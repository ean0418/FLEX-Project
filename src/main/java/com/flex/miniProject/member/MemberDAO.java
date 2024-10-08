package com.flex.miniProject.member;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Random;

@Service
public class MemberDAO {

    @Autowired
    private SqlSession ss;


    public boolean checkIfIdExists(String bm_id) {
        Integer count = ss.selectOne("MemberMapper.checkIfIdExists", bm_id);
        System.out.println("ID Count: " + count);  // 로그 출력
        return count != null && count > 0;
    }



    public void signupMember(HttpServletRequest req, Bizone_member m) {
        try {
            String bm_addr1 = req.getParameter("bm_addr1");
            String bm_addr2 = req.getParameter("bm_addr2");
            String bm_addr3 = req.getParameter("bm_addr3");
            String bm_address = bm_addr1 + " " + bm_addr2 + " " + bm_addr3;
            m.setBm_address(bm_address);

            ss.getMapper(MemberMapper.class).signupMember(m);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public Members memberIdCheck(Bizone_member m) {
        return new Members(ss.getMapper(MemberMapper.class).getMemberById(m));
    }



    public void login(Bizone_member m, HttpServletRequest req) {
        try {
            // 데이터베이스에서 ID로 회원 조회
            List<Bizone_member> members = ss.getMapper(MemberMapper.class).getMemberById(m);
            if (members.size() != 0) {
                Bizone_member dbM = members.get(0);

                // 비밀번호 일치 여부 확인
                if (dbM.getBm_pw().equals(m.getBm_pw())) {
                    req.setAttribute("r", "로그인 성공");
                    req.getSession().setAttribute("loginMember", dbM);
                    req.getSession().setMaxInactiveInterval(600); // 세션 유지 시간 설정
                    // 로그인 성공 시 success.jsp로 이동
                    req.setAttribute("contentPage", "../index.jsp");
                } else {
                    // 비밀번호 오류 시
                    req.setAttribute("r", "로그인 실패(PW 오류)");
                    req.setAttribute("contentPage", "../member/login.jsp");
                }
            } else {
                // ID가 없는 경우
                req.setAttribute("r", "로그인 실패(ID 없음)");
                req.setAttribute("contentPage", "../member/login.jsp");
            }
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("r", "로그인 실패(DB서버)");
            req.setAttribute("contentPage", "../member/login.jsp");
        }
    }

    public static boolean loginCheck(HttpServletRequest req) {
        Bizone_member m = (Bizone_member) req.getSession().getAttribute("loginMember");
        if (m != null) {
            // 로그인 성공 + 상태 유지시
            req.setAttribute("lp", "../main/main.jsp");
            return true;
        }
        // 로그인상태가 아니거나 + 로그인 실패시
        req.setAttribute("lp", "../member/login.jsp");
        return false;
    }

    public void logout(HttpServletRequest req) {
        try {
            req.getSession().setAttribute("loginMember", null);
            req.setAttribute("r", "로그아웃 성공");
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("r", "로그아웃 성공");
        }
    }

    public void delete(HttpServletRequest req) {
        try {
            Bizone_member m = (Bizone_member) req.getSession().getAttribute("loginMember");
            if (ss.getMapper(MemberMapper.class).deleteMember(m) == 1) {
                req.setAttribute("r", "탈퇴 성공");
                req.getSession().setAttribute("loginMember", null);


            } else {
                req.setAttribute("r", "이미 탈퇴처리 됨");
            }
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("r", "탈퇴 실패(DB서버)");
        }
    }

    public void update(HttpServletRequest req) {
        Bizone_member loginMember = (Bizone_member) req.getSession().getAttribute("loginMember");
        Bizone_member m = (Bizone_member) req.getSession().getAttribute("loginMember");
        // 세션에 저장된 loginMember 객체와 bm_id 값 확인
        if (loginMember != null) {
            System.out.println("Session loginMember ID: " + loginMember.getBm_id());
        } else {
            System.out.println("loginMember is null in session");
        }
        try {
            // 파라미터 값을 로그로 확인
            System.out.println("Update ID: " + req.getParameter("bm_id"));

            m.setBm_id(req.getParameter("bm_id"));
            m.setBm_pw(req.getParameter("bm_pw"));
            m.setBm_name(req.getParameter("bm_name"));
            m.setBm_nickname(req.getParameter("bm_nickname"));
            m.setBm_phoneNum(req.getParameter("bm_phoneNum"));
            m.setBm_mail(req.getParameter("bm_mail"));

            String bm_birthday = req.getParameter("bm_birthday");
            if (bm_birthday != null && !bm_birthday.isEmpty()) {
                Date birthdayDate = Date.valueOf(bm_birthday);
                m.setBm_birthday(birthdayDate);
            }

            m.setBm_address(req.getParameter("bm_address"));

            int result = ss.getMapper(MemberMapper.class).updateMember(m);
            System.out.println("Update Result: " + result); // 업데이트 성공 여부 로그 확인
            if (result == 1) {
                req.setAttribute("r", "정보 수정 성공");
                req.getSession().setAttribute("loginMember", m);
            } else {
                req.setAttribute("r", "정보 수정 실패");
            }
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("r", "정보 수정 실패");
        }
    }

}




