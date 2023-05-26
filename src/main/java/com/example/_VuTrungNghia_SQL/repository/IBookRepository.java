package com.example._VuTrungNghia_SQL.repository;


import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example._VuTrungNghia_SQL.entity.Book;
@Repository
public interface IBookRepository extends JpaRepository<Book, Long>{

}
