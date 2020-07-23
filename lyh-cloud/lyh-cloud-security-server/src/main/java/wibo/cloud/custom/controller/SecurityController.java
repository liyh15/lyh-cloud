package wibo.cloud.custom.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

@RestController
public class SecurityController {

    @RequestMapping("/admin")
    @PreAuthorize("hasAuthority('admin')")
    public String admin() {
        System.out.println(((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
        return "admin";
    }

    @RequestMapping("/user")
    @PreAuthorize("hasAuthority('user')")
    public String user() {
        return "user";
    }

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 9000);
        OutputStream outputStream = socket.getOutputStream();
        InputStream inputStream = socket.getInputStream();
        outputStream.write(1);
        outputStream.close();
        System.out.println(inputStream.read());
        inputStream.close();
    }
}
