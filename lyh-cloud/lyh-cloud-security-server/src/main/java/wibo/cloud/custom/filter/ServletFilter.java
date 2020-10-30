package wibo.cloud.custom.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@Component
@WebFilter(urlPatterns = "/*",filterName = "ServletFilter")
public class ServletFilter implements Filter {

    /**
     * 初始化调用的方法
     * @param filterConfig
     * @throws ServletException
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    /**
     FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
     filterRegistrationBean.setOrder(6);       // 这个是用来设置执行顺序
     filterRegistrationBean.setFilter(new AFilter());
     filterRegistrationBean.setName("filter1");
     filterRegistrationBean.addUrlPatterns("/*");
     return filterRegistrationBean;
     */ 
    /**
     * 指定拦截的方法
     * @param servletRequest
     * @param servletResponse
     * @param filterChain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //System.out.println("----------已经进入方法了-----------");
        filterChain.doFilter(servletRequest, servletResponse);
    }

    /**
     * 销毁的方法
     */
    @Override
    public void destroy() {

    }
}
