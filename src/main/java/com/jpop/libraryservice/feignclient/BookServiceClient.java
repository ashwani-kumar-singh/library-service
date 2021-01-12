package com.jpop.libraryservice.feignclient;

import com.jpop.libraryservice.common.model.request.BookRequest;
import com.jpop.libraryservice.common.model.response.BookResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@FeignClient(value = "book-service", url = "${book.service.url}")
public interface BookServiceClient {
    @GetMapping(value = "v1/books/")
    List<BookResponse> getBooks();

    @GetMapping(value = "v1/books/{book_id}")
    BookResponse getBookDetails(@PathVariable("book_id") Integer bookId);

    @PostMapping(value = "v1/books", consumes = "application/json")
    BookResponse addBookDetails (@RequestBody BookRequest bookRequest, @RequestParam("logged_in") Integer loggedIn);

    @DeleteMapping(value = "v1/books/{book_id}")
    Boolean deleteBook(@PathVariable("book_id") Integer bookId);
}
