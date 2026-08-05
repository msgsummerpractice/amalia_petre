package com.example.spring_data_jpa.model;

public class UserMapper {

    // Convert User entity to UserResponse DTO
    public static UserResponse toUserResponse(User user) {
        if (user == null) {
            return null;
        }
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setUsername(user.getUsername());
        userResponse.setEmail(user.getEmail());
        userResponse.setFirstname(user.getFirstname());
        userResponse.setLastname(user.getLastname());

        return userResponse;
    }

    // Convert UserRequest DTO to User entity
    public static User toUserEntity(UserRequest userRequest) {
        if (userRequest == null) {
            return null;
        }
        User user = new User();
        user.setUsername(userRequest.getUsername());
        user.setEmail(userRequest.getEmail());
        user.setPassword(userRequest.getPassword());
        user.setFirstname(userRequest.getFirstname());
        user.setLastname(userRequest.getLastname());

        return user;
    }
}
