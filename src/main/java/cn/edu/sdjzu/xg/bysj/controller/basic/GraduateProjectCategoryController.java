package cn.edu.sdjzu.xg.bysj.controller.basic;
//201902104050 姜瑞临
import cn.edu.sdjzu.xg.bysj.domain.GraduateProjectCategory;
import cn.edu.sdjzu.xg.bysj.service.GraduateProjectCategoryService;
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

@WebServlet("/GPCC.ctl")
public class GraduateProjectCategoryController extends HttpServlet{
    Logger logger = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        //设置响应字符编码为UTF-8
        response.setContentType("application/json;charset=UTF-8");
        try {
            String condition = null;
            Pagination pagination = null;
            //如果id = null, 表示响应所有学院对象，否则响应id指定的学院对象
            String GPCC_json = JSONUtil.getJSON(request);
            System.out.println(GPCC_json);
            if (GPCC_json.equals("")){
                responseGPCsJSON(response,condition,pagination);
            }else {
                if (JSON.parseObject(GPCC_json).getJSONObject("id") != null){
                    int id = JSON.parseObject(GPCC_json).getJSONObject("id").getInteger("id");
                    responseGPCJSON(id, response);
                }else {
                    JSONObject jsonArray = JSON.parseObject(GPCC_json);
                    if (jsonArray.getObject("pagination",Pagination.class) != null){
                        pagination = jsonArray.getObject("pagination",Pagination.class);
                    }
                    if (jsonArray.get("condition") != null){
                        condition = JSON.parseObject(GPCC_json).getJSONArray("condition").toString();
                    }
                    this.responseGPCsJSON(response,condition,pagination);
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
    private void responseGPCJSON(int id, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        //根据id查找学位
        GraduateProjectCategory gPC = GraduateProjectCategoryService.getInstance().find(id);
        String gPC_json = JSON.toJSONString(gPC);
        //响应degree到前端
        response.getWriter().println(gPC);
    }
    //响应所有学位对象
    private void responseGPCsJSON(HttpServletResponse response, String condition, Pagination pagination)
            throws ServletException, IOException, SQLException {
        //获得所有学位
        Collection<GraduateProjectCategory> gPCs = GraduateProjectCategoryService.getInstance().findAll(condition,pagination);

        String gPC_json = JSON.toJSONString(
                gPCs, SerializerFeature.DisableCircularReferenceDetect);
        //响应degrees到前端
        response.getWriter().println(gPC_json);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //设置响应字符编码为UTF-8
        response.setContentType("application/json;charset=UTF-8");
        //设置请求字符编码为UTF-8
        request.setCharacterEncoding("UTF-8");
        //根据request对象，获得代表参数的JSON字串
        String gPC_json = JSONUtil.getJSON(request);
        //将JSON字串解析为GPT对象
        GraduateProjectCategory beAdd = JSON.parseObject(gPC_json, GraduateProjectCategory.class);
        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        //在数据库表中增加GPT对象
        try {
            GraduateProjectCategoryService.getInstance().add(beAdd);
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
        String gPC_json = JSONUtil.getJSON(request);
        GraduateProjectCategory gPCToDel = JSON.parseObject(gPC_json, GraduateProjectCategory.class);
        //设置响应字符编码为UTF-8
        response.setContentType("application/json;charset=UTF-8");
        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        //到数据库表中删除对应的学院
        try {
            GraduateProjectCategoryService.getInstance().delete(gPCToDel.getId());
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
        String gPC_json = JSONUtil.getJSON(request);
        //将JSON字串解析为School对象
        GraduateProjectCategory schoolToAdd = JSON.parseObject(gPC_json, GraduateProjectCategory.class);
        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        //到数据库表修改School对象对应的记录
        try {
            GraduateProjectCategoryService.getInstance().update(schoolToAdd);
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
