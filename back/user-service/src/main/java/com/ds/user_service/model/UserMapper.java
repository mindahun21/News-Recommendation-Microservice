package com.ds.user_service.model;

import com.ds.user_service.model.dto.UserRequest;
import com.ds.user_service.model.dto.UserResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserMapper {
    public User toUser(UserRequest userRequest){
        return User
                .builder()
                .username(userRequest.username())
                .password(userRequest.password())
                .role(List.of())
                .build();
    }
    public User toUser(String username,String password){
        return User
                .builder()
                .username(username)
                .password(password)
                .role(List.of())
                .build();
    }
    public UserResponse toUserResponse(User user){
        return new UserResponse(
                user.getUsername(),
                user.getRole()
        );

    }
}
