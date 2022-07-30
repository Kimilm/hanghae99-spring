package com.sparta.selectshop.service;

import com.sparta.selectshop.models.user.SignupRequestDto;
import com.sparta.selectshop.models.user.User;
import com.sparta.selectshop.models.user.UserRoleEnum;
import com.sparta.selectshop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private static final String ADMIN_TOKEN = "AAABnv/xRVklrnYxKZ0aHgTBcXukeZygoC";

    public void registerUser(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        Optional<User> found = userRepository.findByUsername(username);

        if (found.isPresent()) {
            throw new IllegalArgumentException("중복된 ID 사용자가 존재합니다");
        }

        String password = requestDto.getPassword();
        String email = requestDto.getEmail();
        UserRoleEnum role = UserRoleEnum.USER;

        if (requestDto.isAdmin()) {
            if (!requestDto.getAdminToken().equals(ADMIN_TOKEN)) {
                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
            }
            role = UserRoleEnum.ADMIN;
        }

        userRepository.save(new User(username, password, email, role));
    }
}
