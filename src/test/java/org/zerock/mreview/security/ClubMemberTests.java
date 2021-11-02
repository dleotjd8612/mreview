package org.zerock.mreview.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.zerock.mreview.entity.ClubMember;
import org.zerock.mreview.entity.ClubMemberRole;
import org.zerock.mreview.repository.ClubMemberRepository;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class ClubMemberTests {
    @Autowired
    private ClubMemberRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void addClubMember() {
        ClubMember clubMember = ClubMember.builder()
                .email("admin@museum.com")
                .name("관리자")
                .fromSocial(false)
                .password(passwordEncoder.encode("1111"))
                .build();

        clubMember.addMemberRole(ClubMemberRole.USER); // 유저
        clubMember.addMemberRole(ClubMemberRole.ADMIN); // 관리자

        repository.save(clubMember);
    }

    @Test
    public void insertDummies() {
        // 1 ~ 80까지는 USER만 지정
        // 81 ~ 90까지는 USER, MANAGER
        // 91 ~ 100까지는 USER, MANAGER, ADMIN

        IntStream.rangeClosed(1, 3).forEach(i -> {
            ClubMember clubMember = ClubMember.builder()
                    .email("user" + i + "@zerock.org")
                    .name("사용자" + i)
                    .fromSocial(false)
                    .password(passwordEncoder.encode("1111"))
                    .build();

            // 기본 권한
            clubMember.addMemberRole(ClubMemberRole.USER);
            if(i > 1) clubMember.addMemberRole(ClubMemberRole.MANAGER);
            if(i > 2) clubMember.addMemberRole(ClubMemberRole.ADMIN);

            repository.save(clubMember);
        });
    }

    @Test
    public void testRead() {
        Optional<ClubMember> result = repository.findByEmail("user95@zerock.org", false);
        ClubMember clubMember = result.get();
        System.out.println(clubMember);
    }
}
