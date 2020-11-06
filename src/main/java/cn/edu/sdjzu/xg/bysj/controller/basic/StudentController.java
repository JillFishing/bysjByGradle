package cn.edu.sdjzu.xg.bysj.controller.basic;
//201902104050 姜瑞临
import cn.edu.sdjzu.xg.bysj.domain.Student;
import cn.edu.sdjzu.xg.bysj.service.StudentService;
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

@WebServlet("/student.ctl")
public class StudentController extends HttpServlet {
    Logger logger = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);
    /**
     * GET, http://localhost:8080/degree.ctl?id=1, 查询id=1的学位
     * GET, http://localhost:8080/degree.ctl, 查询所有的学位
     * 把一个或所有学位对象响应到前端
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        //设置响应字符编码为UTF-8
        response.setContentType("application/json;charset=UTF-8");
        try {
            String condition = null;
            Pagination pagination = null;
            //如果id = null, 表示响应所有学院对象，否则响应id指定的学院对象
            String student_json = JSONUtil.getJSON(request);
            System.out.println(student_json);
            JSONObject jsonObject = JSON.parseObject(student_json);
            if (!student_json.equals("")){
                Object teacherId_obj = jsonObject.get("teacherId");
                if(teacherId_obj != null){
                    Integer teacherId = Integer.parseInt(teacherId_obj.toString());
                    if (jsonObject.getObject("pagination",Pagination.class) != null){
                        pagination = jsonObject.getObject("pagination",Pagination.class);
                    }
                    System.out.println(teacherId);
                    responseStudentsByTeacher(response,teacherId,pagination);
                }else {
                    if (JSON.parseObject(student_json).getJSONObject("id") != null){
                        int id = JSON.parseObject(student_json).getJSONObject("id").getInteger("id");
                        responseStudent(id, response);
                    }else {
                        if (jsonObject.getObject("pagination",Pagination.class) != null){
                            pagination = jsonObject.getObject("pagination",Pagination.class);
                        }
                        if (jsonObject.get("condition") != null){
                            condition = JSON.parseObject(student_json).getJSONArray("condition").toString();
                        }
                        this.responseStudents(response,condition,pagination);
                    }
                }
            }
        }catch (SQLException e){
            //响应message到前端
            response.getWriter().println("SQLException occurs!"+e.getMessage());
            //logger.error(e.getMessage());
            e.printStackTrace();
        }catch(Exception e){
            //响应message到前端
            response.getWriter().println("Exception occurs!"+e.getMessage());
            //logger.error(e.getMessage());
            e.printStackTrace();
        }
    }
    //响应一个学位对象
    private void responseStudent(int id, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        //根据id查找学位
        Student student = StudentService.getInstance().find(id);
        String student_json = JSON.toJSONString(student);
        //响应degree到前端
        response.getWriter().println(student);
    }
    //响应所有学位对象
    private void responseStudents(HttpServletResponse response, String condition, Pagination pagination)
            throws ServletException, IOException, SQLException {
        //获得所有学位
        Collection<Student> degrees = StudentService.getInstance().findAll(condition,pagination);
        String students_json = JSON.toJSONString(
                degrees, SerializerFeature.DisableCircularReferenceDetect);
        //响应degrees到前端
        response.getWriter().println(students_json);
    }
    private void responseStudentsByTeacher(HttpServletResponse response,Integer teacher, Pagination pagination)
            throws ServletException, IOException, SQLException {
        //获得所有学位
        Collection<Student> students = StudentService.getInstance().findAll(teacher,pagination);
        String students_json = JSON.toJSONString(
                students, SerializerFeature.DisableCircularReferenceDetect);
        System.out.println(students_json);
        //响应degrees到前端
        response.getWriter().println(students_json);
    }
    /**
     * POST, http://localhost:8080/degree.ctl, 增加学位
     * 增加一个学位对象：将来自前端请求的JSON对象，增加到数据库表中
     * @param request 请求对象
     * @param response 响应对象
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //设置响应字符编码为UTF-8
        response.setContentType("application/json;charset=UTF-8");
        //设置请求字符编码为UTF-8
        request.setCharacterEncoding("UTF-8");
        //根据request对象，获得代表参数的JSON字串
        String deg_json = JSONUtil.getJSON(request);
        //将JSON字串解析为GPT对象
        Student beAdd = JSON.parseObject(deg_json, Student.class);
        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        //在数据库表中增加GPT对象
        try {
            StudentService.getInstance().add(beAdd);
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

    /**
     * DELETE, http://localhost:8080/degree.ctl?id=1, 删除id=1的学位
     * 删除一个学位对象：根据来自前端请求的id，删除数据库表中id的对应记录
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //读取参数id
        String degree_json = JSONUtil.getJSON(request);
        Student degreeToDel = JSON.parseObject(degree_json, Student.class);
        //设置响应字符编码为UTF-8
        response.setContentType("application/json;charset=UTF-8");
        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        //到数据库表中删除对应的学院
        try {
            StudentService.getInstance().delete(degreeToDel.getId());
            logger.info(message.put("message", "删除成功"));
        }catch (SQLException e){
            logger.error(message.put("message", "数据库操作异常"));
        }
        //响应message到前端
        response.getWriter().println(message);
    }


    /**
     * PUT, http://localhost:8080/degree.ctl, 修改学位
     *
     * 修改一个学位对象：将来自前端请求的JSON对象，更新数据库表中相同id的记录
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //设置响应字符编码为UTF-8
        response.setContentType("application/json;charset=UTF-8");
        //设置请求字符编码为UTF-8
        request.setCharacterEncoding("UTF-8");
        String deg_json = JSONUtil.getJSON(request);
        //将JSON字串解析为School对象
        Student schoolToAdd = JSON.parseObject(deg_json, Student.class);
        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        //到数据库表修改School对象对应的记录
        try {
            StudentService.getInstance().update(schoolToAdd);
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
}