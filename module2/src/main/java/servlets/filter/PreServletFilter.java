package servlets.filter;

import javax.servlet.*;
import java.io.IOException;

public class PreServletFilter implements Filter {

    public void init(FilterConfig filterConfig) {

    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        System.out.println("I'm PreServletFilter");
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
