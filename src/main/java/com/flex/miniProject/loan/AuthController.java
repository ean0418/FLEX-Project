package com.flex.miniProject.loan;

import com.flex.miniProject.member.Bizone_member;
import com.flex.miniProject.member.MemberDAO;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")  // React 주소와 세션 쿠키 사용 설정
public class AuthController {

    // 로그인 요청 처리
    @PostMapping("/login")
    public String login(@RequestBody Bizone_member loginRequest, HttpServletRequest request) {
        HttpSession session = request.getSession(true); // 세션이 없으면 생성
        session.setAttribute("user", loginRequest.getBm_id());
        return "로그인 성공";
    }

    // 로그인 상태 확인
    @GetMapping("/session")
    public String sessionInfo(HttpServletRequest request) {
        HttpSession session = request.getSession(false); // 세션이 없으면 null 반환
        if (session != null && session.getAttribute("user") != null) {
            return "로그인 상태: " + session.getAttribute("user");
        }
        return "로그인하지 않은 상태입니다.";
    }

    // 로그아웃 요청 처리
    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate(); // 세션 무효화
        }
        return "로그아웃 성공";
    }
}
