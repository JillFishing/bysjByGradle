package cn.edu.sdjzu.xg.bysj.controller.basic;
//201902104050 姜瑞临
import cn.edu.sdjzu.xg.bysj.domain.GraduateProjectType;
import cn.edu.sdjzu.xg.bysj.domain.School;
import cn.edu.sdjzu.xg.bysj.service.GraduateProjectTypeService;
import cn.edu.sdjzu.xg.bysj.service.SchoolService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import util.JSONUtil;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/GPT.ctl")
public class GraduateProjectTypeController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        //设置响应字符编码为UTF-8
        response.setContentType("application/json;charset=UTF-8");
        //设置请求字符编码为UTF-8
        request.setCharacterEncoding("UTF-8");
        //根据request对象，获得代表参数的JSON字串
        String gPT_json = JSONUtil.getJSON(request);
        //将JSON字串解析为GPT对象
        GraduateProjectType beAdd = JSON.parseObject(gPT_json, GraduateProjectType.class);
        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        //在数据库表中增加GPT对象
        try {
            GraduateProjectTypeService.getInstance().add(beAdd);
            message.put("message", "增加成功");
        }catch (SQLException e){
            message.put("message", "数据库操作异常");
        }catch(Exception e){
            message.put("message", "网络异常");
        }//响应message到前端
        response.getWriter().println(message);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        //设置响应字符编码为UTF-8
        response.setContentType("application/json;charset=UTF-8");
        //读取参数id
        String id_str = request.getParameter("id");
        try {
            //如果id = null, 表示响应所有GraduateProjectController对象，否则响应id指定的GraduateProjectController对象
            if (id_str == null) {
                GraduateProjectTypesJSON(response);
            } else {
                //根据id查找某个GraduateProjectController
                int id = Integer.parseInt(id_str);
                GraduateProjectTypeJSON(id, response);
            }
        }catch (SQLException e){
            //响应message到前端
            response.getWriter().println("SQLException occurs!"+e.getMessage());
        }catch(Exception e){
            //响应message到前端
            response.getWriter().println("Exception occurs!"+e.getMessage());
        }
    }@Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //读取参数id
        String id_str = request.getParameter("id");
        int id = Integer.parseInt(id_str);
        //设置响应字符编码为UTF-8
        response.setContentType("application/json;charset=UTF-8");
        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        //到数据库表中删除对应的GPT
        try {
            GraduateProjectTypeService.getInstance().delete(id);
            message.put("message", "删除成功");
        }catch (SQLException e){
            message.put("message", "数据库操作异常");
        }catch(Exception e){
            message.put("message", "网络异常");
        }
        //响应message到前端
        response.getWriter().println(message);
    }
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //设置响应字符编码为UTF-8
        response.setContentType("application/json;charset=UTF-8");
        //设置请求字符编码为UTF-8
        request.setCharacterEncoding("UTF-8");
        String gPT_json = JSONUtil.getJSON(request);
        //将JSON字串解析为School对象
        GraduateProjectType schoolToAdd = JSON.parseObject(gPT_json, GraduateProjectType.class);
        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        //到数据库表修改School对象对应的记录
        try {
            GraduateProjectTypeService.getInstance().update(schoolToAdd);
            message.put("message", "修改成功");
        }catch (SQLException e){
            message.put("message", "数据库操作异常");
        }catch(Exception e){
            message.put("message", "网络异常");
        }
        //响应message到前端
        response.getWriter().println(message);
    }
    private void GraduateProjectTypeJSON(int id, HttpServletResponse response)
            throws SQLException, IOException {
        //根据id查找GraduateProjectController
        GraduateProjectType graduateProjectType = GraduateProjectTypeService.getInstance().find(id);
        String school_json = JSON.toJSONString(graduateProjectType);
        //响应school到前端
        response.getWriter().println(school_json);
    }
    private void GraduateProjectTypesJSON(HttpServletResponse response)
            throws IOException, SQLException {
        //响应所有GPT到前端
        response.getWriter().println(GraduateProjectTypeService.getInstance().findAll());
    }
}
