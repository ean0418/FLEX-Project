package com.flex.miniProject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
public class HomeController {
    @RequestMapping("/")
    public String home(HttpServletRequest req) {
        req.setAttribute("contentPage", "mainpage.jsp");
        return "index";
    }

        @RequestMapping(value = "/index.go", method = RequestMethod.GET)
        public String home2(HttpServletRequest req) {
            return home(req);
        }
}