package wibo.cloud.uaa.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/r")
public class SecurityController {

    @RequestMapping("/name")
    public String test() {
       return "name";
    }

    @RequestMapping("/age")
    @PreAuthorize("hasAuthority('aaa')")
    public String age() {
        return "age";
    }

    @RequestMapping("/security")
    public String security() {
        SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return "age";
    }

    /**
     *
     * @return
     * @throws
     * @description 获取用户姓名
     * @author liyuanhao
     * @date 2020/7/16 16:50
     */
    @RequestMapping("/getName")
    public String getName() {
        // TODO 需要专注以下ThreadLocal中对于强，弱，虚应用的认识需要看一下
        // ThreadLocal<String> threadLocal = new ThreadLocal<>();
        // 获取当前上下文中的用户权限
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.isAuthenticated()) {
            // 如果当前用户未进行认证则返回空
            return null;
        }
        // 获取当前用户的身份认证信息
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails)principal).getUsername();
        } else {
            return principal.toString();
        }
    }
}
