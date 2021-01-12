package com.jpop.libraryservice.service;

import com.jpop.libraryservice.common.model.request.BookRequest;
import com.jpop.libraryservice.common.model.request.UserRequest;
import com.jpop.libraryservice.common.model.response.BookResponse;
import com.jpop.libraryservice.common.model.response.UserResponse;
import com.jpop.libraryservice.model.response.BookIssueResponse;
import com.jpop.libraryservice.model.response.LibraryServiceResponse;
import com.jpop.libraryservice.model.response.UserBookDetails;

import java.util.List;

public interface LibraryService {

    LibraryServiceResponse<List<BookResponse>> getAllBooks();

    LibraryServiceResponse<BookResponse> getBookDetails(Integer bookId);

    LibraryServiceResponse<BookResponse> addBookDetails(Integer loggedIn, BookRequest bookRequest);

    LibraryServiceResponse<List<UserResponse>> getAllUsers();

    LibraryServiceResponse<Boolean> deleteBook(Integer bookId);

    LibraryServiceResponse<Boolean> deleteUser(Integer userId);

    LibraryServiceResponse<UserBookDetails> getAllBooksIssuedByUser(Integer userId);

    LibraryServiceResponse<UserResponse> addUserDetails(Integer loggedIn, UserRequest userRequest);

    LibraryServiceResponse<UserResponse> updateUserDetails(Integer userId, Integer loggedIn,
                                                           UserRequest userRequest);

    LibraryServiceResponse<BookIssueResponse> issueBook(Integer userId, Integer bookId, Integer loggedIn);

    LibraryServiceResponse<Boolean> returnBook(Integer userId, Integer bookId);
}
