package com.jpop.libraryservice.model.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jpop.libraryservice.constant.LibraryStatusCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class LibraryServiceResponse<T> {

    private LibraryStatusCode status;
    private T responseObject;

    @JsonIgnore
    private HttpStatus statusCode;

    public LibraryServiceResponse() {
        this.statusCode = HttpStatus.OK;
    }

    public LibraryServiceResponse(T responseObject) {
        this.responseObject = responseObject;
        this.statusCode = HttpStatus.OK;
    }

    public LibraryServiceResponse(T responseObject, LibraryStatusCode status) {
        this.responseObject = responseObject;
        this.status = status;
        this.statusCode = HttpStatus.OK;
    }

    public LibraryServiceResponse(LibraryStatusCode status) {
        this.status = status;
        this.statusCode = HttpStatus.OK;
    }
}
