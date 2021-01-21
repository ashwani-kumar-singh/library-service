package com.jpop.libraryservice.service;

import com.jpop.libraryservice.common.model.request.BookRequest;
import com.jpop.libraryservice.common.model.request.UserRequest;
import com.jpop.libraryservice.common.model.response.BookResponse;
import com.jpop.libraryservice.common.model.response.UserResponse;
import com.jpop.libraryservice.model.response.BookIssueResponse;
import com.jpop.libraryservice.model.response.LibraryServiceResponse;
import com.jpop.libraryservice.model.response.UserBookDetails;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Service to handle Library request
 */
public interface LibraryService {

    /**
     * Get all Book Details.
     * @return LibraryServiceResponse<List<BookResponse>> i.e. list of books with details.
     */
    LibraryServiceResponse<List<BookResponse>> getAllBooks();

    /**
     * Get a Book details
     * @param bookId i.e. book id
     * @return LibraryServiceResponse<BookResponse> i.e. book details
     */
    LibraryServiceResponse<BookResponse> getBookDetails(@NotNull(message = "book id can not be null")
                                                                Integer bookId);

    /**
     * Create a book for given below request.
     * @param loggedIn i.e. logged in
     * @param bookRequest i.e. user request
     * @return LibraryServiceResponse<BookResponse> i.e. returns newly created book.
     */
    LibraryServiceResponse<BookResponse> addBookDetails(@NotNull(message = "logged in can not be null") Integer loggedIn,
                                                        @NotNull(message = "book request can not be null") BookRequest
                                                                bookRequest);

    /**
     * Get all User Details.
     * @return LibraryServiceResponse<List<UserResponse> i.e. list of users with details.
     */
    LibraryServiceResponse<List<UserResponse>> getAllUsers();

    /**
     * Delete a book with given user id
     * @param bookId i.e. book id.
     * @return LibraryServiceResponse<Boolean>
     */
    LibraryServiceResponse<Boolean> deleteBook(@NotNull(message = "book id can not be null") Integer bookId);

    /**
     * Delete a user with given user id
     * @param userId i.e. user id.
     * @return LibraryServiceResponse<Boolean>
     */
    LibraryServiceResponse<Boolean> deleteUser(@NotNull(message = "user id can not be null") Integer userId);

    /**
     * Get all Books issued by a user.
     * @param userId i.e. user id
     * @return LibraryServiceResponse<UserBookDetails>
     */
    LibraryServiceResponse<UserBookDetails> getAllBooksIssuedByUser(@NotNull(message = "user id can not be null")
                                                                            Integer userId);

    /**
     * Create a user for given below request.
     * @param loggedIn i.e. logged in
     * @param userRequest i.e. user request
     * @return LibraryServiceResponse<UserResponse> i.e. returns newly created user.
     */
    LibraryServiceResponse<UserResponse> addUserDetails(@NotNull(message = "logged in can not be null") Integer loggedIn,
                                                        @NotNull(message = "user request can not be null") UserRequest
                                                                userRequest);

    /**
     * Update a user for below request.
     * @param loggedIn i.e. logged in user.
     * @param userId i.e. user id of user details to be updated.
     * @param userRequest i.e. user request.
     * @return LibraryServiceResponse<UserResponse> i.e. returns updated user details.
     */
    LibraryServiceResponse<UserResponse> updateUserDetails(@NotNull(message = "user id can not be null") Integer userId,
                                                           @NotNull(message = "book id can not be null") Integer loggedIn,
                                                           @NotNull(message = "user request can not be null") UserRequest
                                                                   userRequest);

    /**
     * Issue a book to a user.
     * @param userId i.e. user id.
     * @param bookId i.e. book id
     * @param loggedIn i.e. logged in
     * @return LibraryServiceResponse<BookIssueResponse>
     */
    LibraryServiceResponse<BookIssueResponse> issueBook(@NotNull(message = "user id can not be null") Integer userId,
                                                        @NotNull(message = "book id can not be null") Integer bookId,
                                                        @NotNull(message = "logged in can not be null") Integer loggedIn);

    /**
     * Return a book issued to a user.
     * @param userId i.e. user id
     * @param bookId i.e. book id
     * @return LibraryServiceResponse<Boolean>
     */
    LibraryServiceResponse<Boolean> returnBook(@NotNull(message = "user id can not be null") Integer userId,
                                               @NotNull(message = "book id can not be null") Integer bookId);
}
