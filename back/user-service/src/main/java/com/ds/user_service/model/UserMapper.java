package com.ds.user_service.model;

import com.ds.user_service.model.dto.UserRequest;
import com.ds.user_service.model.dto.UserResponse;
import org.springframework.stereotype.Service;

import java.util.List;

public class UserMapper {
    public static User toUser(UserRequest userRequest){
        return User
                .builder()
                .username(userRequest.username())
                .password(userRequest.password())
                .role(List.of())
                .build();
    }
    public static User toUser(String username,String password){
        return User
                .builder()
                .username(username)
                .password(password)
                .role(List.of())
                .build();
    }
    public static UserResponse toUserResponse(User user){
        return new UserResponse(
                user.getUsername(),
                user.getRole()
        );

    }
}
