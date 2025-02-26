package org.example.learningos.service;

import org.example.learningos.model.User;
import org.example.learningos.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String registerUser(User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return "이미 존재하는 아이디입니다.";
        }
        if (userRepository.findByNickname(user.getNickname()).isPresent()) {
            return "이미 존재하는 닉네임입니다.";
        }
        // 비밀번호 암호화 처리
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // 기본 역할 설정 - "USER"로 설정 (ROLE_ 접두사는 CustomUserDetails에서 처리)
        user.setRole("USER");
        userRepository.save(user);
        return "회원가입이 완료되었습니다.";
    }

    // 관리자 계정 생성 메서드 (필요 시 사용)
    public void createAdminUser(String username, String password, String nickname) {
        if (userRepository.findByUsername(username).isEmpty()) {
            User adminUser = new User();
            adminUser.setUsername(username);
            adminUser.setPassword(passwordEncoder.encode(password));
            adminUser.setNickname(nickname);
            adminUser.setRole("ADMIN");
            userRepository.save(adminUser);
        }
    }
}