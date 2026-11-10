package com.example.demo;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Delete;

@Mapper
public interface BookMapper {
    @Select("SELECT id, title, author FROM springboot_books WHERE author = #{author}")
    List<Book> findByAuthor(String author);

    @Select("SELECT id, title, author FROM springboot_books")
    List<Book> findAll();

    @Insert("INSERT INTO springboot_books(id, title, author)VALUES (#{id}, #{title}, #{author})")
    int insert(Book book);

    @Update("UPDATE springboot_books SET title = #{title}, author = #{author} WHERE id = #{id}")
    int update(Book book);

    @Delete("DELETE FROM springboot_books WHERE id = #{id}")
    int deleteById(int id);
}
