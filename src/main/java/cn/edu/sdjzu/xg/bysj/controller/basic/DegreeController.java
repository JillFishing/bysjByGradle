package cn.edu.sdjzu.xg.bysj.controller.basic;

import cn.edu.sdjzu.xg.bysj.domain.Degree;
import cn.edu.sdjzu.xg.bysj.service.DegreeService;
import cn.edu.sdjzu.xg.bysj.service.GraduateProjectTypeService;
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
/**
 * 说明：
 * 以“Dao”结尾的类，负责向Service提供数据（如数据库中信息的增，删，改，查）。当前阶段，数据库是“模拟”出来的。
 * 以“Service”结尾的类，负责向Controller（servlet）提供业务方案（如增，删，改，查）。请阅读原代码。
 * servlet充当Contoller来响应客户请求。由于它根据客户的不同请求去调度系统的资源（比如数据），故称Controller。
 * 开发Controller层时，一般只需要创建Service类的对象，然后向它发送相应的消息，以完成一定的业务。
 * 比如，DegreeService.getInstance.find(1)会得到id为1的学位对象(Degree类型)。DegreeService.getInstance.findAll()会得到所有学位对象(java.util.Collection<Degree></Degree>)。
 *
 */

/**
 * 将所有方法组织在一个Controller(Servlet)中
 */
@WebServlet("/degree.ctl")
public class DegreeController extends HttpServlet {
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
            String deg_json = JSONUtil.getJSON(request);
            System.out.println(deg_json);
            if (deg_json.equals("")){
                responseDegreesJSON(response,condition,pagination);
            }else {
                if (JSON.parseObject(deg_json).getJSONObject("id") != null){
                    int id = JSON.parseObject(deg_json).getJSONObject("id").getInteger("id");
                    responseDegreeJSON(id, response);
                }else {
                    JSONObject jsonArray = JSON.parseObject(deg_json);
                    if (jsonArray.getObject("pagination",Pagination.class) != null){
                        pagination = jsonArray.getObject("pagination",Pagination.class);
                    }
                    if (jsonArray.get("condition") != null){
                        condition = JSON.parseObject(deg_json).getJSONArray("condition").toString();
                    }
                    this.responseDegreesJSON(response,condition,pagination);
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
    //响应一个学位对象
    private void responseDegreeJSON(int id, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        //根据id查找学位
        Degree degree = DegreeService.getInstance().find(id);
        String dept_json = JSON.toJSONString(degree);
        //响应degree到前端
        response.getWriter().println(degree);
    }
    //响应所有学位对象
    private void responseDegreesJSON(HttpServletResponse response,String condition,Pagination pagination)
            throws ServletException, IOException, SQLException {
        //获得所有学位
        Collection<Degree> degrees = DegreeService.getInstance().findAll(condition,pagination);

        String degree_json = JSON.toJSONString(
                degrees, SerializerFeature.DisableCircularReferenceDetect);
        //响应degrees到前端
        response.getWriter().println(degree_json);
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
        Degree beAdd = JSON.parseObject(deg_json, Degree.class);
        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        //在数据库表中增加GPT对象
        try {
            DegreeService.getInstance().add(beAdd);
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
        Degree degreeToDel = JSON.parseObject(degree_json, Degree.class);
        //设置响应字符编码为UTF-8
        response.setContentType("application/json;charset=UTF-8");
        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        //到数据库表中删除对应的学院
        try {
            DegreeService.getInstance().delete(degreeToDel.getId());
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
        Degree schoolToAdd = JSON.parseObject(deg_json, Degree.class);
        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        //到数据库表修改School对象对应的记录
        try {
            DegreeService.getInstance().update(schoolToAdd);
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
