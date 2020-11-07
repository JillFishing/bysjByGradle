package cn.edu.sdjzu.xg.bysj.controller.basic;
//201902104050 姜瑞临
import cn.edu.sdjzu.xg.bysj.domain.ApplicationForSupervisorEntry;
import cn.edu.sdjzu.xg.bysj.domain.Student;
import cn.edu.sdjzu.xg.bysj.service.ApplicationForSupervisorService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.JSONUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/app.ctl")
public class ApplicationForSupervisorController extends HttpServlet {
    Logger logger = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //设置响应字符编码为UTF-8
        response.setContentType("application/json;charset=UTF-8");
        //设置请求字符编码为UTF-8
        request.setCharacterEncoding("UTF-8");
        //根据request对象，获得代表参数的JSON字串
        String json = JSONUtil.getJSON(request);
        List<ApplicationForSupervisorEntry> applicationForSupervisors;
        //将JSON字串解析为GPT对象
        applicationForSupervisors = JSON.parseObject(json).
                getJSONArray("applicationForSupervisors").toJavaList(ApplicationForSupervisorEntry.class);
        String selfIntro = JSON.parseObject(json).getString("selfIntro");
        System.out.println(JSON.parseObject(json).getObject("student",String.class));
        Student student = JSON.parseObject(json).getObject("student",Student.class);
        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        //在数据库表中增加GPT对象
        try {
            ApplicationForSupervisorService.getInstance().add(applicationForSupervisors,selfIntro,student);
            message.put("message", "增加成功");
        }catch (SQLException e){
            message.put("message", "数据库操作异常");
            e.printStackTrace();
            logger.error(e.getMessage());
        }catch(Exception e){
            message.put("message", "网络异常");
            logger.error(e.getMessage());
        }//响应message到前端
        response.getWriter().println(message);
    }
}
