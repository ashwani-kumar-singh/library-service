package com.jpop.libraryservice.service.impl;

import com.jpop.libraryservice.feignclient.BookServiceClient;
import com.jpop.libraryservice.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;

public class LibraryServiceImpl implements LibraryService {

    @Autowired
    private BookServiceClient bookServiceClient;
}
