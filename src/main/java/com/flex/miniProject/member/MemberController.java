package com.flex.miniProject.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class MemberController {

    private final MemberDAO mDAO;

    public MemberController(MemberDAO mDAO) {
        this.mDAO = mDAO;
    }


    @RequestMapping(value = "/member.login.go", method = RequestMethod.GET)
    public String goMemberLogin(HttpServletRequest req) {
        req.setAttribute("cp", "member/login.jsp");
        return "index";
    }

    @RequestMapping(value = "/member.signup.go", method = RequestMethod.GET)
    public String goSignup(HttpServletRequest req) {
        req.setAttribute("cp", "member/signup.jsp");
        return "index";
    }

    @RequestMapping(value = "/member.signup", method = RequestMethod.POST)
    public String signupMember(bizone_member m, HttpServletRequest req) {
        mDAO.signup(m, req);
        req.setAttribute("cp", "home.jsp");
        return "index";
    }

    @RequestMapping(value = "/member.id.check", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
    public @ResponseBody Members memberIdCheck(bizone_member m) {
        return mDAO.memberIdCheck(m);
    }

    @RequestMapping(value = "/member.login", method = RequestMethod.POST)
    public String memberLogin(bizone_member m, HttpServletRequest req) {
        mDAO.login(m, req);
        req.setAttribute("cp", "home.jsp");
        return "index";
    }

    @RequestMapping(value = "/member.info.go", method = RequestMethod.GET)
    public String goMemberInfo(HttpServletRequest req) {
        req.setAttribute("cp", "member/info.jsp");
        return "index";
    }

    @RequestMapping(value = "/member.logout", method = RequestMethod.GET)
    public String memberLogout(HttpServletRequest req) {
        mDAO.logout(req);
        req.setAttribute("cp", "home.jsp");
        return "index";
    }

    @RequestMapping(value = "/member.resign", method = RequestMethod.GET)
    public String memberResign(HttpServletRequest req) {
        mDAO.delete(req);
        req.setAttribute("cp", "home.jsp");
        return "index";
    }

    @RequestMapping(value = "/member.update", method = RequestMethod.POST)
    public String memberUpdate(HttpServletRequest req) {
        mDAO.update(req);
        req.setAttribute("cp", "member/info.jsp");
        return "index";
    }
}