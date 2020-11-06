package cn.edu.sdjzu.xg.bysj.controller.basic;

import cn.edu.sdjzu.xg.bysj.domain.ProfTitle;
import cn.edu.sdjzu.xg.bysj.service.ProfTitleService;
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

@WebServlet("/profT.ctl")
public class ProfTitleController extends HttpServlet {
    Logger logger = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        //设置响应字符编码为UTF-8
        response.setContentType("application/json;charset=UTF-8");
        //设置请求字符编码为UTF-8
        request.setCharacterEncoding("UTF-8");
        //根据request对象，获得代表参数的JSON字串
        String profT_json = JSONUtil.getJSON(request);

        //将JSON字串解析为ProfTitle对象
        ProfTitle profTToAdd = JSON.parseObject(profT_json, ProfTitle.class);
        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        //在数据库表中增加profT对象
        try {
            ProfTitleService.getInstance().add(profTToAdd);
            logger.info(message.put("message", "增加成功"));
        }catch (SQLException e){
            logger.error(message.put("message", "数据库操作异常"));
            logger.error(e.getMessage());
        }catch(Exception e){
            logger.error(message.put("message", "网络异常"));
            logger.error(e.getMessage());
        }
        //响应message到前端
        response.getWriter().println(message);
    }
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //读取参数id
        String profT_json = JSONUtil.getJSON(request);
        ProfTitle profTitleToDel = JSON.parseObject(profT_json, ProfTitle.class);
        //设置响应字符编码为UTF-8
        response.setContentType("application/json;charset=UTF-8");
        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        //到数据库表中删除对应的学院
        try {
            ProfTitleService.getInstance().delete(profTitleToDel.getId());
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
        //设置响应字符编码为UTF-8
        response.setContentType("application/json;charset=UTF-8");
        //设置请求字符编码为UTF-8
        request.setCharacterEncoding("UTF-8");
        String profT_json = JSONUtil.getJSON(request);
        //将JSON字串解析为profT对象
        ProfTitle profTToAdd = JSON.parseObject(profT_json, ProfTitle.class);
        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        //到数据库表修改profT对象对应的记录
        try {
            ProfTitleService.getInstance().update(profTToAdd);
            logger.info(message.put("message", "修改成功"));
        }catch (SQLException e){
            logger.error(message.put("message", "数据库操作异常"));
            logger.error(e.getMessage());
        }catch(Exception e){
            logger.error(message.put("message", "网络异常"));
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
            String pFT_json = JSONUtil.getJSON(request);
            System.out.println(pFT_json);
            if (pFT_json.equals("")){
                responseProfTsJSON(response,condition,pagination);
            }else {
                if (JSON.parseObject(pFT_json).getJSONObject("id") != null){
                    int id = JSON.parseObject(pFT_json).getJSONObject("id").getInteger("id");
                    responseProfTJSON(id, response);
                }else {
                    JSONObject jsonArray = JSON.parseObject(pFT_json);
                    if (jsonArray.getObject("pagination",Pagination.class) != null){
                        pagination = jsonArray.getObject("pagination",Pagination.class);
                    }
                    if (jsonArray.get("condition") != null){
                        condition = JSON.parseObject(pFT_json).getJSONArray("condition").toString();
                    }
                    this.responseProfTsJSON(response,condition,pagination);
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
    private void responseProfTJSON(int id, HttpServletResponse response)
            throws SQLException, IOException {
        //根据id查找学院
        ProfTitle profT = ProfTitleService.getInstance().find(id);
        String profT_json = JSON.toJSONString(profT);
        //响应profT到前端
        response.getWriter().println(profT_json);
    }
    private void responseProfTsJSON(HttpServletResponse response, String condition,Pagination pagination)
            throws IOException, SQLException {
        //分页获得所有学院
        Collection<ProfTitle> profTs = ProfTitleService.getInstance().findAll(condition,pagination);
        String profTs_json = JSON.toJSONString(
                profTs, SerializerFeature.DisableCircularReferenceDetect);
        //创建用于向前端响应totalNum和指定页的json对象
        JSONObject message = new JSONObject();
        message.put("totalNum",pagination.getTotalNum());
        message.put("data",profTs_json);
        //向前端响应
        response.getWriter().println(message);
    }
}
