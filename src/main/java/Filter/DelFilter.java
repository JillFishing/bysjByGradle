package Filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "DelFilter", urlPatterns={"/basic/*"} )
public class DelFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        System.out.println("DelFilter starts");
        HttpServletRequest request = (HttpServletRequest)req;
        HttpServletResponse response = (HttpServletResponse)resp;
        response.setContentType("application/json;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        chain.doFilter(req, resp);
        System.out.println("DelFilter ends");
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
