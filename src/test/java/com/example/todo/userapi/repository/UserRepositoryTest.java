package com.example.todo.userapi.repository;

import com.example.todo.userapi.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("회원가입 테스트")
    void saveTest() {
        //given
        User newUser = User.builder()
                .email("abc1234@abc.com")
                .password("1234")
                .userName("망아지")
                .build();
        //when
        User saved = userRepository.save(newUser);

        //then
        assertNotNull(saved);
    }

    @Test
    @DisplayName("이메일 조회 테스트")
    void findByEmailTest() {
        //given
        String email = "abc1234@abc.com";
        //when
        Optional<User> found= userRepository.findByEmail(email);
        //then
        assertTrue(found.isPresent());
        User user = found.get();
        assertEquals("망아지", user.getUserName());
    }

    @Test
    @DisplayName("이메일 중복체크에서 중복이 떳을 경우 테스트")
    void email() {
        //given
        String email = "abc1234@abc.com";
        //when
        boolean flag = userRepository.existsByEmail(email);
        //then
        assertTrue(flag);
    }


    @Test
    @DisplayName("이메일 중복체크에서 중복이 안 떳을 경우 테스트")
    void emailFalse() {
        //given
        String email = "abc1234@abc.co";
        //when
        boolean flag = userRepository.existsByEmail(email);
        //then
        assertFalse(flag);
    }
}