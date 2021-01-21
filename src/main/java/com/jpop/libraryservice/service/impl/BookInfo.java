package com.jpop.libraryservice.service.impl;

import com.jpop.libraryservice.common.model.response.BookResponse;
import com.jpop.libraryservice.feignclient.BookServiceClient;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BookInfo {

    @Autowired
    BookServiceClient bookServiceClient;

    @HystrixCommand(fallbackMethod = "getFallbackBooksDetails")
    public List<BookResponse> getBooksDetails(List<Integer> bookIds) {
        List<BookResponse> issuedBook = new ArrayList<>();
        bookIds.forEach( id -> issuedBook.add(bookServiceClient.getBookDetails(id)));
        return issuedBook;
    }

    private List<BookResponse> getFallbackBooksDetails(List<Integer> bookIds) {
        List<BookResponse> bookResponses = new ArrayList<>();
        bookResponses.add(new BookResponse(0, "", "", "", "", 0,
                0, "",0 , new Date()));
        return bookResponses;
    }
}
