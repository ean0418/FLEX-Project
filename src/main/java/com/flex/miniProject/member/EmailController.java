package com.flex.miniProject.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class EmailController {


    @Autowired
    private EmailService eServices;

    @RequestMapping(value = "/email.send", method = RequestMethod.POST)
    public Map<String,Object> sendEmail(HttpServletRequest req, HttpServletResponse res, @RequestBody Map<String, Object> inputMap) throws UnsupportedEncodingException {
        req.setCharacterEncoding("utf-8");
        res.setCharacterEncoding("utf-8");
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("code", eServices.joinEmail((String) inputMap.get("email")));
        return returnMap;
    }
}
