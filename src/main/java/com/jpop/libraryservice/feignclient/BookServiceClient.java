package com.jpop.libraryservice.feignclient;

//import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient(value = "book-service", url = "${book.service.url}")
public interface BookServiceClient {
    @PostMapping(value = "/posts")
    List<Object> getPosts();

    /*@RequestMapping(method = RequestMethod.GET, value = "/posts/{postId}", produces = "application/json")
    Post getPostById(@PathVariable("postId") Long postId);*/
}
