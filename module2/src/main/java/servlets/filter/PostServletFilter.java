package servlets.filter;

import javax.servlet.*;
import java.io.IOException;

public class PostServletFilter implements Filter {
    public void init(FilterConfig filterConfig) {

    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        filterChain.doFilter(servletRequest, servletResponse);
//        System.out.println("I'm PostServletFilter");
    }
}
