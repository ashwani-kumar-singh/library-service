package com.jpop.libraryservice.feignclient;

import com.jpop.libraryservice.common.model.request.UserRequest;
import com.jpop.libraryservice.common.model.response.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "user-service", url = "${user.service.url}")
public interface UserServiceClient {

    @GetMapping(value = "v1/users/")
    List<UserResponse> getAllUsers();

    @GetMapping(value = "v1/users/{user_id}")
    UserResponse getUserDetails(@PathVariable("user_id") Integer userId);

    @PostMapping(value = "v1/users", consumes = "application/json")
    UserResponse addUserDetails (@RequestBody UserRequest userRequest, @RequestParam("logged_in") Integer loggedIn);

    @PutMapping(value = "v1/users/{user_id}", consumes = "application/json")
    UserResponse updateUserDetails (@PathVariable("user_id") Integer userId, @RequestBody UserRequest userRequest,
                                 @RequestParam("logged_in") Integer loggedIn);

    @DeleteMapping(value = "v1/users/{user_id}")
    Boolean deleteUser(@PathVariable("user_id") Integer userId);
}
