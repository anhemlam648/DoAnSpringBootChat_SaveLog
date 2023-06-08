package com.example._VuTrungNghia_SQL.repository;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example._VuTrungNghia_SQL.entity.Book;

import java.util.List;

@Repository
public interface IBookRepository extends JpaRepository<Book, Long>{
    @Query("SELECT b FROM Book b WHERE LOWER(b.title) LIKE %:keyword% OR LOWER(b.category.name) LIKE %:keyword%")
    List<Book> searchBooks(@Param("keyword") String keyword);
}
