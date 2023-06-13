package com.example.todo.userapi.service;

import com.example.todo.auth.TokenProvider;
import com.example.todo.exception.DuplicatedEmailException;
import com.example.todo.exception.NoRegisteredArgumentsException;
import com.example.todo.userapi.dto.request.LoginRequestDTO;
import com.example.todo.userapi.dto.request.UserSignUpRequestDTO;
import com.example.todo.userapi.dto.response.LoginResponseDTO;
import com.example.todo.userapi.dto.response.UserSignUpResponseDTO;
import com.example.todo.userapi.entity.User;
import com.example.todo.userapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final TokenProvider tokenProvider;

    //회원가입 처리
    public UserSignUpResponseDTO create(final UserSignUpRequestDTO dto){
        if(dto == null){
            throw new NoRegisteredArgumentsException("가입 정보가 없습니다");
        }
        String email = dto.getEmail();

        if (isDuplicate(email)) {
            log.warn("이메일이 중복되었습니다. - {}", email);
            throw new DuplicatedEmailException("중복된 이메일입니다");
        }

        //패스워드 인코딩
        String encoded = encoder.encode(dto.getPassword());
        dto.setPassword(encoded);
        //유저 엔터티로 변환
        User user = dto.toEntity();

        User saved = userRepository.save(user);

        log.info("회원가입 정상 수행됨! - saved user - {}", saved);

        return new UserSignUpResponseDTO(saved);
    }

    public boolean isDuplicate(String email) {
        return userRepository.existsByEmail(email);
    }

    // 회원 인증
    public LoginResponseDTO authenticate(final LoginRequestDTO dto) {

        // 이메일을 통해 회원 정보 조회
        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(
                        () -> new RuntimeException("가입된 회원이 아닙니다!")
                );

        // 패스워드 검증
        String rawPassword = dto.getPassword();
        String encodedPassword = user.getPassword();

        if (!encoder.matches(rawPassword, encodedPassword)) {
            throw new RuntimeException("비밀번호가 틀렸습니다!");
        }

        log.info("{}님 로그인 성공!!", user.getUserName());

        // 토근에 로그인 정보 저장
        // JWT를 클라이언트에게 저장해야 함
        String token = tokenProvider.createToken(user);

        log.info("token : {}", token);

        return new LoginResponseDTO(user, token);
    }
}