package com.jpop.libraryservice.controller;


import com.jpop.libraryservice.common.model.request.BookRequest;
import com.jpop.libraryservice.common.model.request.UserRequest;
import com.jpop.libraryservice.common.model.response.BookResponse;
import com.jpop.libraryservice.common.model.response.UserResponse;
import com.jpop.libraryservice.model.response.BookIssueResponse;
import com.jpop.libraryservice.model.response.LibraryServiceResponse;
import com.jpop.libraryservice.model.response.UserBookDetails;
import com.jpop.libraryservice.service.LibraryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

@RequestMapping("v1/")
@RestController
@EnableSwagger2
@Slf4j
public class LibraryController {

    @Autowired
    private LibraryService libraryService;

    @GetMapping("library/books")
    ResponseEntity<LibraryServiceResponse<List<BookResponse>>> getAllBooks(){
        LibraryServiceResponse<List<BookResponse>>  allBooksResponse = libraryService.getAllBooks();
    return new ResponseEntity<>(allBooksResponse, HttpStatus.OK);
    }

    @GetMapping("library/books/{book_id}")
    ResponseEntity<LibraryServiceResponse<BookResponse>> getBookDetails(@PathVariable("book_id") Integer bookId){
        LibraryServiceResponse<BookResponse> allBooksResponse = libraryService.getBookDetails(bookId);
        return new ResponseEntity<>(allBooksResponse, HttpStatus.OK);
    }

    @PostMapping("library/books")
    ResponseEntity<LibraryServiceResponse<BookResponse>> addBook(@RequestParam("logged_in") Integer loggedIn,
                                                                 @RequestBody BookRequest bookRequest){
        LibraryServiceResponse<BookResponse> allBooksResponse = libraryService.addBookDetails(loggedIn, bookRequest);
        return new ResponseEntity<>(allBooksResponse, HttpStatus.OK);
    }

    @GetMapping("library/users")
    ResponseEntity<LibraryServiceResponse<List<UserResponse>>> getAllUsers(){
        LibraryServiceResponse<List<UserResponse>> allBooksResponse = libraryService.getAllUsers();
        return new ResponseEntity<>(allBooksResponse, HttpStatus.OK);
    }

    @DeleteMapping("/library/books/{book_id}")
    ResponseEntity<LibraryServiceResponse<Boolean>> deleteBook(@PathVariable("book_id") Integer bookId){
        LibraryServiceResponse<Boolean> allBooksResponse = libraryService.deleteBook(bookId);
        return new ResponseEntity<>(allBooksResponse, HttpStatus.OK);
    }

    @DeleteMapping("/library/users/{user_id}")
    ResponseEntity<LibraryServiceResponse<Boolean>> deleteUser(@PathVariable("user_id") Integer userId){
        LibraryServiceResponse<Boolean> allBooksResponse = libraryService.deleteUser(userId);
        return new ResponseEntity<>(allBooksResponse, HttpStatus.OK);
    }

    @GetMapping("/library/users/{user_id}")
    ResponseEntity<LibraryServiceResponse<UserBookDetails>> getAllBooksIssuedByUser(@PathVariable("user_id") Integer userId){
        LibraryServiceResponse<UserBookDetails> issuedBookResponse = libraryService.getAllBooksIssuedByUser(userId);
        return new ResponseEntity<>(issuedBookResponse, HttpStatus.OK);
    }

    @PostMapping("library/users")
    ResponseEntity<LibraryServiceResponse<UserResponse>> addUser(@RequestParam("logged_in") Integer loggedIn,
                                                                 @RequestBody UserRequest userRequest){
        LibraryServiceResponse<UserResponse> allBooksResponse = libraryService.addUserDetails(loggedIn, userRequest);
        return new ResponseEntity<>(allBooksResponse, HttpStatus.OK);
    }

    @PutMapping("/library/users/{user_id}")
    ResponseEntity<LibraryServiceResponse<UserResponse>> updateUser(@PathVariable("user_id") Integer userId,
                                                                    @RequestParam("logged_in") Integer loggedIn,
                                                                 @RequestBody UserRequest userRequest){
        LibraryServiceResponse<UserResponse> allBooksResponse = libraryService.updateUserDetails(userId, loggedIn,
                userRequest);
        return new ResponseEntity<>(allBooksResponse, HttpStatus.OK);
    }

    @PostMapping("/library/users/{user_id}/books/{book_id}")
    ResponseEntity<LibraryServiceResponse<BookIssueResponse>> issueBook(@PathVariable("user_id") Integer userId,
                                                                        @PathVariable("book_id") Integer bookId,
                                                                        @RequestParam("logged_in") Integer loggedIn){
        LibraryServiceResponse<BookIssueResponse> issueBooksResponse = libraryService.issueBook(userId, bookId,
                loggedIn);
        return new ResponseEntity<>(issueBooksResponse, HttpStatus.OK);
    }

    @DeleteMapping("/library/users/{user_id}/books/{book_id}")
    ResponseEntity<LibraryServiceResponse<Boolean>> returnBook(@PathVariable("user_id") Integer userId,
                                                                        @PathVariable("book_id") Integer bookId){
        LibraryServiceResponse<Boolean> returnBookResponse = libraryService.returnBook(userId, bookId);
        return new ResponseEntity<>(returnBookResponse, HttpStatus.OK);
    }
}
