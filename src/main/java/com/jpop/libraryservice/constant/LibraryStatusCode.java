package com.jpop.libraryservice.constant;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(doNotUseGetters = true)
public enum LibraryStatusCode {

  SUCCESS(1000, "SUCCESS"),
  FAILED(1002, "FAILED"),
  DATA_VALIDATION_FAILED(1003, "DATA VALIDATION FAILED"),
  DATABASE_ERROR(1005, "FAILED IN DATABASE CALL");

  private final int code;
  private final String desc;

  LibraryStatusCode(int code, String desc) {
    this.code = code;
    this.desc = desc;
  }
}