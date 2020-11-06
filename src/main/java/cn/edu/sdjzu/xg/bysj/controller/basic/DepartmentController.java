package cn.edu.sdjzu.xg.bysj.controller.basic;
//201902104050 姜瑞临
import cn.edu.sdjzu.xg.bysj.domain.Department;
import cn.edu.sdjzu.xg.bysj.domain.School;
import cn.edu.sdjzu.xg.bysj.service.DepartmentService;
import cn.edu.sdjzu.xg.bysj.service.SchoolService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
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
@WebServlet("/Dept.ctl")
public class DepartmentController extends HttpServlet {
    Logger logger = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);
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
                responseDepartmentsJSON(response,condition,pagination);
            }else {
                if (JSON.parseObject(dept_json).getJSONObject("id") != null){
                    int id = JSON.parseObject(dept_json).getJSONObject("id").getInteger("id");
                    responseDepartmentJSON(id, response);
                }else {
                    JSONObject jsonArray = JSON.parseObject(dept_json);
                    if (jsonArray.getObject("pagination",Pagination.class) != null){
                        pagination = jsonArray.getObject("pagination",Pagination.class);
                    }
                    if (jsonArray.get("condition") != null){
                        condition = JSON.parseObject(dept_json).getJSONArray("condition").toString();
                    }
                    this.responseDepartmentsJSON(response,condition,pagination);
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
    private void responseDepartmentJSON(int id, HttpServletResponse response)
            throws SQLException, IOException {
        //根据id查找院系
        Department dept = DepartmentService.getInstance().find(id);
        String dept_json = JSON.toJSONString(dept);
        //响应school到前端
        response.getWriter().println(dept_json);
    }
    private void responseDepartmentsJSON(HttpServletResponse response, String conditionJSON,Pagination pagination)
            throws IOException, SQLException {
        //获得所有院系
        Collection<Department> departments = DepartmentService.getInstance().findAll(conditionJSON,pagination);
        //获得满足条件的院系并防止重复调用
        String department_json = JSON.toJSONString(
                departments, SerializerFeature.DisableCircularReferenceDetect);
        //向前端响应
        response.getWriter().println(department_json);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置响应字符编码为UTF-8
        response.setContentType("application/json;charset=UTF-8");
        //设置请求字符编码为UTF-8
        request.setCharacterEncoding("UTF-8");
        //根据request对象，获得代表参数的JSON字串
        String dept = JSONUtil.getJSON(request);
        JSONObject dept_json = JSONObject.parseObject(dept);
        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        //在数据库表中增加GPT对象
        try {
            School school = SchoolService.getInstance().find(dept_json.getInteger("managementSchool"));
            Department beAdd = JSON.parseObject(dept, Department.class);
            beAdd.setSchool(school);
            DepartmentService.getInstance().add(beAdd);
            message.put("message", "增加成功");
        }catch (SQLException e){
            message.put("message", "数据库操作异常");
            logger.error(e.getMessage());
        }catch(Exception e){
            message.put("message", "网络异常");
            logger.error(e.getMessage());
        }//响应message到前端
        response.getWriter().println(message);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置响应字符编码为UTF-8
        response.setContentType("application/json;charset=UTF-8");
        //设置请求字符编码为UTF-8
        request.setCharacterEncoding("UTF-8");
        JSONObject message = null;
        String dept = JSONUtil.getJSON(request);
        JSONObject dept_json = JSONObject.parseObject(dept);
        try {
            School school = SchoolService.getInstance().find(dept_json.getInteger("managementSchool"));
            //将JSON字串解析为School对象
            Department departmentToUpdate = JSON.parseObject(dept, Department.class);
            departmentToUpdate.setSchool(school);
            //创建JSON对象message，以便往前端响应信息
            message = new JSONObject();
            //到数据库表修改School对象对应的记录
            DepartmentService.getInstance().update(departmentToUpdate);
            message.put("message", "修改成功");
        }catch (SQLException e){
            message.put("message", "数据库操作异常");
            e.printStackTrace();
        }catch(Exception e){
            message.put("message", "网络异常");
            e.printStackTrace();
        }
        //响应message到前端
        response.getWriter().println(message);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //读取参数id
        String dept_json = JSONUtil.getJSON(request);
        Department departmentToDel = JSON.parseObject(dept_json, Department.class);
        //设置响应字符编码为UTF-8
        response.setContentType("application/json;charset=UTF-8");
        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        //到数据库表中删除对应的学院
        try {
            DepartmentService.getInstance().delete(departmentToDel.getId());
            logger.info(message.put("message", "删除成功"));
        }catch (SQLException e){
            logger.error(message.put("message", "数据库操作异常"));
        }
        //响应message到前端
        response.getWriter().println(message);
    }
}
