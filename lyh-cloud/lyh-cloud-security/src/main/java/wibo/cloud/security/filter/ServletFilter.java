package wibo.cloud.security.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * @Classname ServletFilter
 * @Description TODO
 * @Date 2021/2/21 15:34
 * @Created by lyh
 */
@Component
@WebFilter(urlPatterns = "/*", filterName = "ServletFilter")
public class ServletFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // System.out.println("asdasdasd");
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
