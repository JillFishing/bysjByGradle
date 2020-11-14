package cn.edu.sdjzu.xg.bysj.controller.basic;

import cn.edu.sdjzu.xg.bysj.domain.User;
import cn.edu.sdjzu.xg.bysj.domain.authority.Actor;
import cn.edu.sdjzu.xg.bysj.domain.authority.ActorAssRole;
import cn.edu.sdjzu.xg.bysj.domain.authority.Role;
import cn.edu.sdjzu.xg.bysj.service.ActorAssRoleService;
import cn.edu.sdjzu.xg.bysj.service.UserService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.impl.JWTParser;
import com.auth0.jwt.interfaces.DecodedJWT;
import util.JSONUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;
import java.util.TreeSet;

/**
 * 将所有方法组织在一个Controller(Servlet)中
 * <p>
 * {
 * "username":"020204",
 * "password":"020204"
 * }
 */
@WebServlet("/login.ctl")
public class LoginController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //创建JSON对象respMessage_jsonObj，以便往前端响应信息
        JSONObject respMessage_jsonObj = new JSONObject();
        //设置响应字符编码为UTF-8
        response.setContentType("application/json;charset=UTF-8");
        //将request body转换为JSON字串
        String user_json = JSONUtil.getJSON(request);
        //将JSON字串解析为User对象
        User userToLogin = JSON.parseObject(user_json, User.class);
        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();

        try {
            Actor currentActor = UserService.getInstance().login(userToLogin);
            if (currentActor == null) {
                message.put("message", "用户名或密码不匹配");
                return;
            } else {
                Collection<ActorAssRole> actorAssRoleCollection = ActorAssRoleService.getInstance().findAll(currentActor);
                Collection<Role> roles = new TreeSet<>();
                for (ActorAssRole actorAssRole : actorAssRoleCollection) {
                    Role role = actorAssRole.getRole();
                    roles.add(role);
                }

                message.put("actor", currentActor);
                message.put("roles", roles);
                //输出JSON字串时，屏蔽某些字段
                SimplePropertyPreFilter filter = new SimplePropertyPreFilter();
                //不输出"department"、"title"和"degree"
                filter.getExcludes().add("department");
                filter.getExcludes().add("title");
                filter.getExcludes().add("degree");
                response.getWriter().println(JSON.toJSONString(message, filter));
            }
        } catch (SQLException e) {
            message.put("message", "数据库操作异常");
            //响应message到前端
            response.getWriter().println(message);
            e.printStackTrace();
        } catch (Exception e) {
            message.put("message", "网络异常");
            //响应message到前端
            response.getWriter().println(message);
        }
    }

}
