package Filter;
//201902104050 姜瑞临
import cn.edu.sdjzu.xg.bysj.service.UserService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import exception.BysjException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.SQLException;
//对所有controller起效
@WebFilter(filterName = "JWTFilter", urlPatterns={"*.ctl"})
public class JWTFilter implements Filter {
    Logger logger = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws ServletException, IOException {
        System.out.println("JWTFilter starts");
        //转换请求类型
        HttpServletRequest request = (HttpServletRequest) req;
        //获取token
        String token = request.getHeader("token");
        JWTVerifier build = JWT.require(Algorithm.HMAC256("114514")).build();
        DecodedJWT verify = build.verify(token);
        try {
            //检验token是否合规
            boolean confirm = verify.getClaim("username").asString().equals(
                    UserService.getInstance().getUser(verify.getClaim("id").asInt()).getUsername());
            //完成过滤
            chain.doFilter(req, resp);
            System.out.println("JWTFilter ends");
            if (!confirm){
                throw new BysjException("");
            }
        } catch (SQLException throwable) {
            logger.error("We can not Confirm your ID");
            System.out.println("JWTFilter ends");
        }catch (BysjException bysjException){
            logger.error("Plz check your username or password");
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
