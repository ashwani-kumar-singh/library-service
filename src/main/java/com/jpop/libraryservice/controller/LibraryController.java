package com.jpop.libraryservice.controller;


import com.jpop.libraryservice.common.model.request.BookRequest;
import com.jpop.libraryservice.common.model.request.UserRequest;
import com.jpop.libraryservice.common.model.response.BookResponse;
import com.jpop.libraryservice.common.model.response.UserResponse;
import com.jpop.libraryservice.constant.LibraryStatusCode;
import com.jpop.libraryservice.model.response.BookIssueResponse;
import com.jpop.libraryservice.model.response.LibraryServiceResponse;
import com.jpop.libraryservice.model.response.UserBookDetails;
import com.jpop.libraryservice.service.LibraryService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;

/**
 * REST controller for handling Library request
 */
@RequestMapping("v1/")
@RestController
@EnableSwagger2
@Slf4j
public class LibraryController {

    @Autowired
    private LibraryService libraryService;

    /**
     * {@code GET  library/books} : get the all the books details.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the list of BookResponse, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("library/books")
    @HystrixCommand(fallbackMethod = "getFallBackBooks")
    ResponseEntity<LibraryServiceResponse<List<BookResponse>>> getAllBooks(){
        log.debug("REST request to get all book details");
        LibraryServiceResponse<List<BookResponse>>  allBooksResponse = libraryService.getAllBooks();
    return new ResponseEntity<>(allBooksResponse, HttpStatus.OK);
    }

    ResponseEntity<LibraryServiceResponse<List<BookResponse>>> getFallBackBooks(){
            LibraryServiceResponse<List<BookResponse>>  fallBackResponse = new LibraryServiceResponse<>(
                    Collections.singletonList(new BookResponse()), LibraryStatusCode.SUCCESS);
        return new ResponseEntity<>(fallBackResponse, HttpStatus.OK);
    }

    /**
     * {@code GET  library/books/{book_id}} : get the book details for the requested book id.
     *
     * @param bookId i.e. book id.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the BookDTO, or with status {@code 404 (Not Found)}.
     */
    @HystrixCommand(fallbackMethod = "getFallBackBookDetails")
    @GetMapping("library/books/{book_id}")
    ResponseEntity<LibraryServiceResponse<BookResponse>> getBookDetails(@PathVariable("book_id") Integer bookId){
        log.debug("REST request to get book details with id:{}", bookId);
        LibraryServiceResponse<BookResponse> allBooksResponse = libraryService.getBookDetails(bookId);
        return new ResponseEntity<>(allBooksResponse, HttpStatus.OK);
    }

    ResponseEntity<LibraryServiceResponse<BookResponse>> getFallBackBookDetails(@PathVariable("book_id")
                                                                                        Integer bookId){
        LibraryServiceResponse<BookResponse> fallBackResponse = new LibraryServiceResponse<>(new BookResponse(),
                LibraryStatusCode.SUCCESS);
        return new ResponseEntity<>(fallBackResponse, HttpStatus.OK);
    }

    /**
     * {@code POST  library/books} : Create a new Book.
     *
     * @param bookRequest the book request to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
     *         body the new bookRequest, or with status {@code 500 (Internal Server Error)} if the
     *         book has already an ID.
     */
    @PostMapping("library/books")
    ResponseEntity<LibraryServiceResponse<BookResponse>> createBook(@RequestParam("logged_in") Integer loggedIn,
                                                                    @Valid @RequestBody BookRequest bookRequest){
        log.debug("REST request by user :{} to create book for request:{}", loggedIn, bookRequest);
        LibraryServiceResponse<BookResponse> allBooksResponse = libraryService.addBookDetails(loggedIn,
                bookRequest);
        return new ResponseEntity<>(allBooksResponse, HttpStatus.OK);
    }

    /**
     * {@code DELETE  library/books/{book_id}} : delete the "id" book.
     *
     * @param bookId the id of the book to delete.
     * @return the {@link ResponseEntity} with status {@code 200}.
     */
    @DeleteMapping("library/books/{book_id}")
    ResponseEntity<LibraryServiceResponse<Boolean>> deleteBook(@PathVariable("book_id") Integer bookId){
        log.debug("REST request to delete book details with id:{}", bookId);
        LibraryServiceResponse<Boolean> allBooksResponse = libraryService.deleteBook(bookId);
        return new ResponseEntity<>(allBooksResponse, HttpStatus.OK);
    }

    /**
     * {@code GET  library/users} : get the all the users details.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the list of UserDTO, or with status {@code 404 (Not Found)}.
     */
    @HystrixCommand(fallbackMethod = "getFallBackUsers")
    @GetMapping("library/users")
    ResponseEntity<LibraryServiceResponse<List<UserResponse>>> getAllUsers(){
        log.debug("REST request to get all user details");
        LibraryServiceResponse<List<UserResponse>> allUsersResponse = libraryService.getAllUsers();
        return new ResponseEntity<>(allUsersResponse, HttpStatus.OK);
    }

    ResponseEntity<LibraryServiceResponse<List<UserResponse>>> getFallBackUsers(){
        LibraryServiceResponse<List<UserResponse>> fallBackResponse = new LibraryServiceResponse<>(
                Collections.singletonList(new UserResponse()), LibraryStatusCode.SUCCESS);
        return new ResponseEntity<>(fallBackResponse, HttpStatus.OK);
    }

    /**
     * {@code GET  library/users/{user_id}} : get the all the books details issued by user.
     * @param userId i.e. user id.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the UserBookDetails, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("library/users/{user_id}")
    ResponseEntity<LibraryServiceResponse<UserBookDetails>> getAllBooksIssuedByUser(@PathVariable("user_id")
                                                                                            Integer userId){
        log.debug("Get all the books details issued by the user: {}", userId);
        LibraryServiceResponse<UserBookDetails> issuedBookResponse = libraryService.getAllBooksIssuedByUser(
                userId);
        return new ResponseEntity<>(issuedBookResponse, HttpStatus.OK);
    }

    /**
     * {@code POST  library/users} : Create a new User.
     *
     * @param userRequest the user request to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
     *         body the new UserResponse, or with status {@code 500 (Internal Server Error)} if the
     *         user has already an ID.
     */
    @PostMapping("library/users")
    ResponseEntity<LibraryServiceResponse<UserResponse>> createUser(@RequestParam("logged_in") Integer loggedIn,
                                                                    @Valid @RequestBody UserRequest userRequest){
        log.debug("REST request by user :{} to create user for request:{}", loggedIn, userRequest);
        LibraryServiceResponse<UserResponse> allBooksResponse = libraryService.addUserDetails(loggedIn,
                userRequest);
        return new ResponseEntity<>(allBooksResponse, HttpStatus.OK);
    }

    /**
     * {@code PUT  library/users/{user_id}} : Update existing User Details.
     *
     * @param userRequest the user request to update.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
     *         body the new UserDTO, or with status {@code 500 (Internal Server Error)} if the
     *         user does not exist with given id.
     */
    @PutMapping("library/users/{user_id}")
    ResponseEntity<LibraryServiceResponse<UserResponse>> updateUser(@PathVariable("user_id") Integer userId,
                                                                    @RequestParam("logged_in") Integer loggedIn,
                                                                 @Valid @RequestBody UserRequest userRequest){
        log.debug("REST request by user: {} to update user: {} for request: {}", loggedIn, userId,
                userRequest);
        LibraryServiceResponse<UserResponse> allBooksResponse = libraryService.updateUserDetails(userId, loggedIn,
                userRequest);
        return new ResponseEntity<>(allBooksResponse, HttpStatus.OK);
    }

    /**
     * {@code DELETE  users/{user_id}} : delete the "id" user.
     *
     * @param userId the id of the user to delete.
     * @return the {@link ResponseEntity} with status {@code 200}.
     */
    @DeleteMapping("library/users/{user_id}")
    ResponseEntity<LibraryServiceResponse<Boolean>> deleteUser(@PathVariable("user_id") Integer userId){
        log.debug("REST request to delete user details with id:{}", userId);
        LibraryServiceResponse<Boolean> allBooksResponse = libraryService.deleteUser(userId);
        return new ResponseEntity<>(allBooksResponse, HttpStatus.OK);
    }

    /**
     * {@code POST  library/users} : Issue a book to a user.
     * @param userId i.e. user id
     * @param bookId i.e. book id
     * @param loggedIn i.e. logged in
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
     *         body the new BookIssueResponse, or with status {@code 500 (Internal Server Error)} if the
     *         book/user does not exist.
     */
    @PostMapping("library/users/{user_id}/books/{book_id}")
    ResponseEntity<LibraryServiceResponse<BookIssueResponse>> issueBook(@PathVariable("user_id") Integer userId,
                                                                        @PathVariable("book_id") Integer bookId,
                                                                        @RequestParam("logged_in") Integer loggedIn){
        log.debug("issue a book:{} for a user: {} by user :{}", bookId, userId, loggedIn);
        LibraryServiceResponse<BookIssueResponse> issueBooksResponse = libraryService.issueBook(userId, bookId,
                loggedIn);
        return new ResponseEntity<>(issueBooksResponse, HttpStatus.OK);
    }

    /**
     * {@code DELETE  library/users/{user_id}/books/{book_id}} : return the book issued to a user.
     * @param bookId i.e. book id
     * @param userId i.e. user id
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
     *         body True/False, or with status {@code 500 (Internal Server Error)} if the
     *         book/user does not exist.
     */
    @DeleteMapping("library/users/{user_id}/books/{book_id}")
    ResponseEntity<LibraryServiceResponse<Boolean>> returnBook(@PathVariable("user_id") Integer userId,
                                                               @PathVariable("book_id") Integer bookId){
        log.debug("return the book: {} issued to a user: {}", bookId, userId);
        LibraryServiceResponse<Boolean> returnBookResponse = libraryService.returnBook(userId, bookId);
        return new ResponseEntity<>(returnBookResponse, HttpStatus.OK);
    }
}
