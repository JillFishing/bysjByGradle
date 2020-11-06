package cn.edu.sdjzu.xg.bysj.controller.basic;

import cn.edu.sdjzu.xg.bysj.domain.Teacher;
import cn.edu.sdjzu.xg.bysj.service.TeacherService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import exception.UsernameDuplicateException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.JSONUtil;
import util.Pagination;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.Collection;

@WebServlet("/teacher.ctl")
public class TeacherController extends HttpServlet {
    Logger logger = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        //设置响应字符编码为UTF-8
        response.setContentType("application/json;charset=UTF-8");
        //设置请求字符编码为UTF-8
        request.setCharacterEncoding("UTF-8");
        //根据request对象，获得代表参数的JSON字串
        String teacher_json = JSONUtil.getJSON(request);

        //将JSON字串解析为teacher对象
        Teacher teacherToAdd = JSON.parseObject(teacher_json, Teacher.class);
        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        //在数据库表中增加teacher对象
        try {
            TeacherService.getInstance().add(teacherToAdd);
            message.put("message", "增加成功");
        }catch (SQLException e){
            message.put("message", "数据库操作异常");
            logger.error(e.getMessage());
        }catch (UsernameDuplicateException e){
            message.put("message",e.getMessage());
            logger.error(e.getMessage());
        }catch(Exception e){
            message.put("message", "网络异常");
            logger.error(e.getMessage());
        }
        //响应message到前端
        response.getWriter().println(message);
    }
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //读取参数id
        String teacher_json = JSONUtil.getJSON(request);
        Teacher teacherToDel = JSON.parseObject(teacher_json, Teacher.class);
        //设置响应字符编码为UTF-8
        response.setContentType("application/json;charset=UTF-8");
        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        //到数据库表中删除对应的学院
        try {
            System.out.println(TeacherService.getInstance().delete(teacherToDel));
            logger.info(message.put("message", "删除成功"));
        }catch (SQLException e){
            logger.error(message.put("message", "数据库操作异常"));
            logger.error(e.getMessage());
        }
        //响应message到前端
        response.getWriter().println(message);
    }
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //设置请求字符编码为UTF-8
        request.setCharacterEncoding("UTF-8");
        String teacher_json = JSONUtil.getJSON(request);
        //将JSON字串解析为teacher对象
        Teacher teacherToAdd = JSON.parseObject(teacher_json, Teacher.class);
        //设置响应字符编码为UTF-8
        response.setContentType("application/json;charset=UTF-8");
        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        //到数据库表修改teacher对象对应的记录
        try {
            TeacherService.getInstance().update(teacherToAdd);
            message.put("message", "修改成功");
        }catch (SQLException e){
            message.put("message", "数据库操作异常");
            logger.error(e.getMessage());
        }catch(Exception e){
            message.put("message", "网络异常");
            logger.error(e.getMessage());
        }
        //响应message到前端
        response.getWriter().println(message);
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        //设置响应字符编码为UTF-8
        response.setContentType("application/json;charset=UTF-8");
        try {
            String condition = null;
            Pagination pagination = null;
            //如果id = null, 表示响应所有学院对象，否则响应id指定的学院对象
            String teacher_json = JSONUtil.getJSON(request);
            System.out.println(teacher_json);
            if (teacher_json.equals("")){
                responseteachersJSON(response, pagination, condition);
            }else {
                if (JSON.parseObject(teacher_json).getJSONObject("id") != null){
                    int id = JSON.parseObject(teacher_json).getJSONObject("id").getInteger("id");
                    responseteacherJSON(id, response);
                }else {
                    JSONObject jsonArray = JSON.parseObject(teacher_json);
                    if (jsonArray.getObject("pagination",Pagination.class) != null){
                        pagination = jsonArray.getObject("pagination",Pagination.class);
                    }
                    if (jsonArray.get("condition") != null){
                        condition = JSON.parseObject(teacher_json).getJSONArray("condition").toString();
                    }
                    this.responseteachersJSON(response, pagination, condition);
                }
            }
        }catch (SQLException e){
            //响应message到前端
            response.getWriter().println("SQLException occurs!"+e.getMessage());
            logger.error(e.getMessage());
        }catch(Exception e){
            //响应message到前端
            response.getWriter().println("Exception occurs!"+e.getMessage());
            logger.error(e.getMessage());
        }
    }
    private void responseteacherJSON(int id, HttpServletResponse response)
            throws SQLException, IOException {
        //根据id查找学院
        Teacher teacher = TeacherService.getInstance().find(id);
        String teacher_json = JSON.toJSONString(teacher);
        //响应teacher到前端
        response.getWriter().println(teacher_json);
    }
    private void responseteachersJSON(HttpServletResponse response,Pagination pagination,String condition)
            throws IOException, SQLException {
        //分页获得所有学院
        Collection<Teacher> teachers = TeacherService.getInstance().findAll(pagination,condition);
        String teachers_json = JSON.toJSONString(
                teachers, SerializerFeature.DisableCircularReferenceDetect);
        //创建用于向前端响应totalnum和指定页的json对象
        JSONObject message = new JSONObject();
        message.put("totalNum",pagination.getTotalNum());
        message.put("data",teachers_json);
        //向前端响应
        response.getWriter().println(message);
    }
}
