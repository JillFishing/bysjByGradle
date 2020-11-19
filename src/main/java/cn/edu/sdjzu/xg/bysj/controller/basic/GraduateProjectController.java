package cn.edu.sdjzu.xg.bysj.controller.basic;

import cn.edu.sdjzu.xg.bysj.domain.GraduateProject;
import cn.edu.sdjzu.xg.bysj.service.GraduateProjectService;
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

@WebServlet("/GraduateProjectServlet.ctl")
public class GraduateProjectController extends HttpServlet {
    Logger logger = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        try {
            String condition = null;
            Pagination pagination = null;
            //如果id = null, 表示响应所有学院对象，否则响应id指定的学院对象
            String deg_json = JSONUtil.getJSON(request);
            System.out.println(deg_json);
            if (deg_json.equals("")){
                responseGPsJSON(response,condition,pagination);
            }else {
                if (JSON.parseObject(deg_json).getJSONObject("id") != null){
                    int id = JSON.parseObject(deg_json).getJSONObject("id").getInteger("id");
                    responseGPJSON(id, response);
                }else {
                    JSONObject jsonArray = JSON.parseObject(deg_json);
                    if (jsonArray.getObject("pagination",Pagination.class) != null){
                        pagination = jsonArray.getObject("pagination",Pagination.class);
                    }
                    if (jsonArray.get("condition") != null){
                        condition = JSON.parseObject(deg_json).getJSONArray("condition").toString();
                    }
                    this.responseGPsJSON(response,condition,pagination);
                }
            }
        }catch (SQLException e){
            //响应message到前端
            response.getWriter().println("SQLException occurs!"+e.getMessage());
            e.printStackTrace();
            e.getMessage();
            logger.error(e.getMessage());
        }catch(Exception e){
            //响应message到前端
            response.getWriter().println("Exception occurs!"+e.getMessage());
            e.getMessage();
            logger.error(e.getMessage());
        }
    }
    private void responseGPJSON(int id, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        GraduateProject gp = GraduateProjectService.getInstance().find(id);
        response.getWriter().println(gp);
    }
    private void responseGPsJSON(HttpServletResponse response,String condition,Pagination pagination)
            throws ServletException, IOException, SQLException {
        Collection<GraduateProject> gps = GraduateProjectService.getInstance().findAll(condition,pagination);
        String gps_json = JSON.toJSONString(gps, SerializerFeature.DisableCircularReferenceDetect);
        response.getWriter().println(gps_json);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //根据request对象，获得代表参数的JSON字串
        String gp_json = JSONUtil.getJSON(request);
        //将JSON字串解析为GPT对象
        GraduateProject beAdd = JSON.parseObject(gp_json, GraduateProject.class);
        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        //在数据库表中增加GPT对象
        try {
            GraduateProjectService.getInstance().add(beAdd);
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
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //读取参数id
        String gp_json = JSONUtil.getJSON(request);
        GraduateProject degreeToDel = JSON.parseObject(gp_json, GraduateProject.class);
        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        //到数据库表中删除对应的学院
        try {
            GraduateProjectService.getInstance().delete(degreeToDel.getId());
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
        String deg_json = JSONUtil.getJSON(request);
        //将JSON字串解析为School对象
        GraduateProject graduateProjectAdded = JSON.parseObject(deg_json, GraduateProject.class);
        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        //到数据库表修改School对象对应的记录
        try {
            GraduateProjectService.getInstance().update(graduateProjectAdded);
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
