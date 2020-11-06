package cn.edu.sdjzu.xg.bysj.controller.basic;
//201902104050 姜瑞临
import cn.edu.sdjzu.xg.bysj.domain.School;
import cn.edu.sdjzu.xg.bysj.service.SchoolService;
import com.alibaba.fastjson.*;
import com.alibaba.fastjson.serializer.SerializerFeature;
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
import java.sql.SQLException;
import java.util.Collection;

@WebServlet("/school.ctl")
public class SchoolController extends HttpServlet {
    Logger logger = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        //设置响应字符编码为UTF-8
        response.setContentType("application/json;charset=UTF-8");
        //设置请求字符编码为UTF-8
        request.setCharacterEncoding("UTF-8");
        //根据request对象，获得代表参数的JSON字串
        String school_json = JSONUtil.getJSON(request);

        //将JSON字串解析为School对象
        School schoolToAdd = JSON.parseObject(school_json, School.class);
        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        //在数据库表中增加School对象
        try {
            SchoolService.getInstance().add(schoolToAdd);
            logger.info(message.put("message", "增加成功"));
        }catch (SQLException e){
            logger.error(message.put("message", "数据库操作异常"));
        }catch(Exception e){
            logger.error(message.put("message", "网络异常"));
        }
        //响应message到前端
        response.getWriter().println(message);
    }
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //读取参数id
        String school_json = JSONUtil.getJSON(request);
        School schoolToDel = JSON.parseObject(school_json, School.class);
        //设置响应字符编码为UTF-8
        response.setContentType("application/json;charset=UTF-8");
        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        //到数据库表中删除对应的学院
        try {
            SchoolService.getInstance().delete(schoolToDel.getId());
            logger.info(message.put("message", "删除成功"));
        }catch (SQLException e){
            logger.error(message.put("message", "数据库操作异常"));
        }
        //响应message到前端
        response.getWriter().println(message);
    }
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //设置请求字符编码为UTF-8
        request.setCharacterEncoding("UTF-8");
        String school_json = JSONUtil.getJSON(request);
        //将JSON字串解析为School对象
        School schoolToAdd = JSON.parseObject(school_json, School.class);
        //设置响应字符编码为UTF-8
        response.setContentType("application/json;charset=UTF-8");
        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        //到数据库表修改School对象对应的记录
        try {
            SchoolService.getInstance().update(schoolToAdd);
            logger.info(message.put("message", "修改成功"));
        }catch (SQLException e){
            logger.error(message.put("message", "数据库操作异常"));
        }catch(Exception e){
            logger.error(message.put("message", "网络异常"));
        }
        //响应message到前端
        response.getWriter().println(message);
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        //设置响应字符编码为UTF-8
        response.setContentType("application/json;charset=UTF-8");
        try {
            String condition = null;
            Pagination pagination = null;
            //如果id = null, 表示响应所有学院对象，否则响应id指定的学院对象
            String dept_json = JSONUtil.getJSON(request);
            System.out.println(dept_json);
            if (dept_json.equals("")){
                responseSchoolsJSON(response,condition,pagination);
            }else {
                if (JSON.parseObject(dept_json).getJSONObject("id") != null){
                    int id = JSON.parseObject(dept_json).getJSONObject("id").getInteger("id");
                    responseSchoolJSON(id, response);
                }else {
                    JSONObject jsonArray = JSON.parseObject(dept_json);
                    if (jsonArray.getObject("pagination",Pagination.class) != null){
                        pagination = jsonArray.getObject("pagination",Pagination.class);
                    }
                    if (jsonArray.get("condition") != null){
                        condition = JSON.parseObject(dept_json).getJSONArray("condition").toString();
                    }
                    this.responseSchoolsJSON(response,condition,pagination);
                }
            }
        }catch (SQLException e){
            //响应message到前端
            logger.error("SQLException occurs!"+e.getMessage());
        }catch(Exception e){
            //响应message到前端
            logger.error("Exception occurs!"+ e.getMessage());
        }
    }
    private void responseSchoolJSON(int id, HttpServletResponse response)
            throws SQLException, IOException {
        //根据id查找学院
        School school = SchoolService.getInstance().find(id);
        String school_json = JSON.toJSONString(school);
        //响应school到前端
        response.getWriter().println(school_json);
    }
    private void responseSchoolsJSON(HttpServletResponse response, String conditionJSON,Pagination pagination)
            throws IOException, SQLException {
        //分页获得所有学院
        Collection<School> schools = SchoolService.getInstance().findAll(conditionJSON,pagination);
        String schools_json = JSON.toJSONString(
                schools, SerializerFeature.DisableCircularReferenceDetect);
        //向前端响应
        response.getWriter().println(schools_json);
    }


}
