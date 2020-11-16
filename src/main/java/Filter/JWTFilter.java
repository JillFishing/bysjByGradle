package Filter;

import cn.edu.sdjzu.xg.bysj.service.UserService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.SQLException;

@WebFilter(filterName = "JWTFilter", urlPatterns={"/basic/*"})
public class JWTFilter implements Filter {
    Logger logger = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws ServletException, IOException {
        System.out.println("JWTFilter starts");
        HttpServletRequest request = (HttpServletRequest) req;
        String token = request.getHeader("token");
        JWTVerifier build = JWT.require(Algorithm.HMAC256("114514")).build();
        DecodedJWT verify = build.verify(token);
        System.out.println(verify.getClaim("username").asString());
        try {
            System.out.println(UserService.getInstance().getUser(verify.getClaim("id").asInt()).getUsername());
            boolean confirm = verify.getClaim("username").asString().equals(
                    UserService.getInstance().getUser(verify.getClaim("id").asInt()).getUsername());
            if (confirm){
                throw new SQLException("Plz check your username or password");
            }
        } catch (SQLException throwable) {
            logger.error("We can not Confirm your ID");
        }
        chain.doFilter(req, resp);
        System.out.println("JWTFilter ends");
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
