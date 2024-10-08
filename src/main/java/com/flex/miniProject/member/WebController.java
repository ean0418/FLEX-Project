package com.flex.miniProject.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@RequestMapping("/member")  // "/member" 경로에 대한 API 요청 처리
public class WebController {

    @Autowired
    private MemberDAO memberDAO;

    // 회원 ID 중복 체크 API
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/checkId/{bm_id}")
    public boolean checkId(@PathVariable("bm_id") String bm_id) {
        return memberDAO.checkIfIdExists(bm_id);
    }


    // 회원 가입 API
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/signup")
    public String signupMember(HttpServletRequest req, @RequestBody Bizone_member m) {
        memberDAO.signupMember(req, m);
        return "회원 가입 성공";
    }

    // 회원 로그인 API
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/login")
    public String login(@RequestBody Bizone_member m, HttpServletRequest req) {
        memberDAO.login(m, req);
        return (String) req.getAttribute("r");  // 로그인 결과 메시지 반환
    }

    // 회원 로그아웃 API
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/logout")
    public String logout(HttpServletRequest req) {
        memberDAO.logout(req);
        return "로그아웃 성공";
    }

    // 회원 탈퇴 API
    @CrossOrigin(origins = "http://localhost:3000")
    @DeleteMapping("/delete")
    public String deleteMember(HttpServletRequest req) {
        memberDAO.delete(req);
        return (String) req.getAttribute("r");  // 탈퇴 결과 메시지 반환
    }

    // 회원 정보 업데이트 API
    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping("/update")
    public String updateMember(HttpServletRequest req, @RequestBody Bizone_member m) {
        memberDAO.update(req);
        return (String) req.getAttribute("r");  // 업데이트 결과 메시지 반환
    }

    // 로그인 체크 API
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/loginCheck")
    public boolean loginCheck(HttpServletRequest req) {
        return memberDAO.loginCheck(req);
    }
}