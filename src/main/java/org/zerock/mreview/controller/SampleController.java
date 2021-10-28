package org.zerock.mreview.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.zerock.mreview.security.dto.ClubAuthMemberDTO;

@Controller
@Log4j2
@RequestMapping("/sample/")
public class SampleController {

    // @PreAuthorize("permitAll()"): 웹 페이지 접근 시 로그인 요구 및 모든 사용자(회원) 로그인 가능
    @PreAuthorize("permitAll()")
    @GetMapping("/all")
    public void exAll() { // 로그인을 하지 않은 사용자도 접근
        log.info("exAll..........");
    }

//    @GetMapping("/member")
//    public void exMember() { // 로그인한 사용자만 접근
//        log.info("exMember..........");
//    }

    // @PreAuthorize("hasRole()"): 웹 페이지 접근 시 로그인 요구 및 ADMIN 권한을 가진 사용자만 로그인 허용, 현재 사용자의 권한이 파라미터의 권한과 동일한 경우
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public void exAdmin() { // 관리자(admin) 권한이 있는 사용자만 접근
        log.info("exAdmin..........");
    }

    // @AuthenticationPrincipal: 로그인한 사용자의 정보를 파라미터로 받고 싶을때, UserDetailsService에서 Return한 객체를 파라미터로 직접 받아 사용
    @GetMapping("/member")
    public void exMember(@AuthenticationPrincipal ClubAuthMemberDTO clubAuthMember) {
        log.info("exMember...........");

        log.info("-------------------------");
        log.info(clubAuthMember);
    }

    // 로그인한 사용자 중 email이 user3@zerock.org 인 사용자만 접근 가능
    // @AuthenticationPrincipal을 이용해서 로그인한 사용자의 정보를 얻고 로그인한 사용자가 user3@zerock.org와 같을 때만 접근 가능, eq는 equals를 뜻함, 즉 문자열이 같은지 비교
    @PreAuthorize("#clubAuthMember != null && #clubAuthMember.username eq \"user3@zerock.org\"")
    @GetMapping("/exOnly")
    public String exMemberOnly(@AuthenticationPrincipal ClubAuthMemberDTO clubAuthMember) {
        log.info("exMemberOnly............");
        log.info(clubAuthMember);

        return "/sample/admin";
    }
}
