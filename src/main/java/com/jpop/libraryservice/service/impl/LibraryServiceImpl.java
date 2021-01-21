package com.jpop.libraryservice.service.impl;

import com.jpop.libraryservice.common.model.request.BookRequest;
import com.jpop.libraryservice.common.model.request.UserRequest;
import com.jpop.libraryservice.common.model.response.BookResponse;
import com.jpop.libraryservice.common.model.response.UserResponse;
import com.jpop.libraryservice.constant.LibraryStatusCode;
import com.jpop.libraryservice.entity.Library;
import com.jpop.libraryservice.feignclient.BookServiceClient;
import com.jpop.libraryservice.feignclient.UserServiceClient;
import com.jpop.libraryservice.model.response.BookIssueResponse;
import com.jpop.libraryservice.model.response.LibraryServiceResponse;
import com.jpop.libraryservice.model.response.UserBookDetails;
import com.jpop.libraryservice.repository.LibraryRepository;
import com.jpop.libraryservice.service.LibraryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Validated
@Slf4j
@Transactional
public class LibraryServiceImpl implements LibraryService {

    @Autowired
    private BookServiceClient bookServiceClient;

    @Autowired
    private UserServiceClient userServiceClient;

    @Autowired
    private LibraryRepository libraryRepository;

    @Autowired
    UserInfo userInfo;

    @Autowired
    BookInfo bookInfo;

    @Override
    public LibraryServiceResponse<List<BookResponse>> getAllBooks(){
        log.debug("Request to get all Book details");
        LibraryServiceResponse<List<BookResponse>> response = new LibraryServiceResponse<>(LibraryStatusCode.FAILED);
        List<BookResponse> bookResponse = bookServiceClient.getBooks();
        response.setStatus(LibraryStatusCode.SUCCESS);
        response.setResponseObject(bookResponse);
        return response;
    }

    @Override
    public LibraryServiceResponse<BookResponse> getBookDetails(Integer bookId) {
        log.debug("Request to get Book details for user id: {}", bookId);
        LibraryServiceResponse<BookResponse> response = new LibraryServiceResponse<>(LibraryStatusCode.FAILED);
        BookResponse bookResponse = bookServiceClient.getBookDetails(bookId);
        response.setStatus(LibraryStatusCode.SUCCESS);
        response.setResponseObject(bookResponse);
        return response;
    }

    @Override
    public LibraryServiceResponse<BookResponse> addBookDetails(Integer loggedIn, BookRequest bookRequest) {
        log.debug("Request to save Book details for request: {} by user :{}", bookRequest, loggedIn);
        LibraryServiceResponse<BookResponse> response = new LibraryServiceResponse<>(LibraryStatusCode.FAILED);
        BookResponse bookResponse = bookServiceClient.addBookDetails(bookRequest, loggedIn);
        response.setStatus(LibraryStatusCode.SUCCESS);
        response.setResponseObject(bookResponse);
        return response;
    }

    @Override
    public LibraryServiceResponse<List<UserResponse>> getAllUsers() {
        log.debug("Request to get all User details");
        LibraryServiceResponse<List<UserResponse>> response = new LibraryServiceResponse<>(LibraryStatusCode.FAILED);
        List<UserResponse> userResponse = userServiceClient.getAllUsers();
        response.setStatus(LibraryStatusCode.SUCCESS);
        response.setResponseObject(userResponse);
        return response;
    }

    @Override
    public LibraryServiceResponse<Boolean> deleteBook(Integer bookId) {
        log.debug("Request to delete Book details for user id : {}", bookId);
        LibraryServiceResponse<Boolean> response = new LibraryServiceResponse<>(LibraryStatusCode.FAILED);
        libraryRepository.deleteByBookId(bookId);
        Boolean deleteResponse = bookServiceClient.deleteBook(bookId);
        response.setStatus(LibraryStatusCode.SUCCESS);
        response.setResponseObject(deleteResponse);
        return response;
    }

    @Override
    public LibraryServiceResponse<Boolean> deleteUser(Integer userId) {
        log.debug("Request to delete user details for user id : {}", userId);
        LibraryServiceResponse<Boolean> response = new LibraryServiceResponse<>(LibraryStatusCode.FAILED);
        libraryRepository.deleteByUserId(userId);
        Boolean deleteResponse = userServiceClient.deleteUser(userId);
        response.setStatus(LibraryStatusCode.SUCCESS);
        response.setResponseObject(deleteResponse);
        return response;
    }

    @Override
    public LibraryServiceResponse<UserBookDetails> getAllBooksIssuedByUser(Integer userId) {
        log.debug("Get all the book issued by the user id: {}", userId);
        LibraryServiceResponse<UserBookDetails> response = new LibraryServiceResponse<>(LibraryStatusCode.FAILED);
        List<Library> libraryList = libraryRepository.findByUserId(userId);
        List<Integer> bookIds = libraryList.stream().map(Library::getBookId).collect(Collectors.toList());
        List<BookResponse> issuedBook = bookInfo.getBooksDetails(bookIds);
        UserBookDetails userBookDetails = userInfo.getUserDetails(userId);
        userBookDetails.setBooksIssued(issuedBook);
        response.setStatus(LibraryStatusCode.SUCCESS);
        response.setResponseObject(userBookDetails);
        return response;
    }

    @Override
    public LibraryServiceResponse<UserResponse> addUserDetails(Integer loggedIn, UserRequest userRequest) {
        log.debug("Request to save user details for request: {} by user :{}", userRequest, loggedIn);
        LibraryServiceResponse<UserResponse> response = new LibraryServiceResponse<>(LibraryStatusCode.FAILED);
        UserResponse userResponse = userServiceClient.addUserDetails(userRequest, loggedIn);
        response.setStatus(LibraryStatusCode.SUCCESS);
        response.setResponseObject(userResponse);
        return response;
    }

    @Override
    public LibraryServiceResponse<UserResponse> updateUserDetails(Integer userId, Integer loggedIn,
                                                                  UserRequest userRequest) {
        log.debug("Request to update User details for user id:{} with update request: {} by user :{}",
                userId, userRequest, loggedIn);
        LibraryServiceResponse<UserResponse> response = new LibraryServiceResponse<>(LibraryStatusCode.FAILED);
        UserResponse userResponse = userServiceClient.updateUserDetails(userId, userRequest, loggedIn);
        response.setStatus(LibraryStatusCode.SUCCESS);
        response.setResponseObject(userResponse);
        return response;
    }

    @Override
    public LibraryServiceResponse<BookIssueResponse> issueBook(Integer userId, Integer bookId, Integer loggedIn) {
        log.debug("issue a book: {} for a user: {} by user:{}", bookId, userId, loggedIn);
        LibraryServiceResponse<BookIssueResponse> response = new LibraryServiceResponse<>(LibraryStatusCode.FAILED);
        Library library = libraryRepository.save(Library.builder().bookId(bookId).userId(userId)
                .createdBy(loggedIn).build());
        BookIssueResponse issueResponse = new BookIssueResponse();
        BeanUtils.copyProperties(library, issueResponse);
        response.setStatus(LibraryStatusCode.SUCCESS);
        response.setResponseObject(issueResponse);
        return response;
    }

    @Override
    public LibraryServiceResponse<Boolean> returnBook(Integer userId, Integer bookId) {
        log.debug("return the book: {} issued to a user: {}", bookId, userId);
        LibraryServiceResponse<Boolean> response = new LibraryServiceResponse<>(Boolean.FALSE, LibraryStatusCode.FAILED);
        libraryRepository.deleteByUserIdAndBookId(userId, bookId);
        response.setStatus(LibraryStatusCode.SUCCESS);
        response.setResponseObject(Boolean.TRUE);
        return response;
    }

}
