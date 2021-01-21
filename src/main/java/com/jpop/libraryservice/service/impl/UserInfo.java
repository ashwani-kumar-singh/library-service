package com.jpop.libraryservice.service.impl;

import com.jpop.libraryservice.common.model.response.UserResponse;
import com.jpop.libraryservice.feignclient.UserServiceClient;
import com.jpop.libraryservice.model.response.UserBookDetails;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserInfo {

    @Autowired
    UserServiceClient userServiceClient;

    @HystrixCommand(fallbackMethod = "getFallbackUserDetails")
    public UserBookDetails getUserDetails(Integer userId) {
        UserBookDetails userBookDetails = new UserBookDetails();
        userBookDetails.setUserDetails(userServiceClient.getUserDetails(userId));
        return userBookDetails;
    }

    private UserBookDetails getFallbackUserDetails(Integer userId) {
        return new UserBookDetails(new UserResponse(userId, "", "", "", "",""),
                new ArrayList<>());
    }
}
