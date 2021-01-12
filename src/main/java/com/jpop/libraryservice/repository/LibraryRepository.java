package com.jpop.libraryservice.repository;

import com.jpop.libraryservice.entity.Library;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LibraryRepository extends JpaRepository<Library, Integer> {

    long deleteByBookId(Integer bookId);

    long deleteByUserId(Integer userId);

    long deleteByUserIdAndBookId(Integer userId, Integer bookId);

    List<Library> findByUserId(Integer userId);

}
