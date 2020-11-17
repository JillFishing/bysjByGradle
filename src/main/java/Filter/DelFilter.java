package Filter;
//201902104050 姜瑞临
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
//对所有controller起效
@WebFilter(filterName = "DelFilter", urlPatterns={"*.ctl"})
public class DelFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        System.out.println("DelFilter starts");
        //转换请求和返回的类型
        HttpServletRequest request = (HttpServletRequest)req;
        HttpServletResponse response = (HttpServletResponse)resp;
        //转换请求和返回的编码
        response.setContentType("application/json;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        //完成过滤
        chain.doFilter(req, resp);
        System.out.println("DelFilter ends");
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
