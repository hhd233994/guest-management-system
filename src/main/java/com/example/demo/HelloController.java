package com.example.demo;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import java.util.Map;
import java.util.List;

@RestController 
public class HelloController {
    private final BookMapper bookMapper;
    public HelloController(BookMapper bookMapper){
        this.bookMapper = bookMapper;
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello Spring Boot";
    }
    @GetMapping ("/status")
    public String status(){
        return "服务正常";
    
    }
    @GetMapping ("/greet")
    public String greet(@RequestParam String name){
        return "你好, " + name;
    }
    @PostMapping ("/greet")
    public String greetFromBody(@RequestBody Map<String, String> body){
        return "你好, " + body.get("name");
    }
    @PostMapping("/books")
     public String addBook(@RequestBody Book book){
        bookMapper.insert(book);
        return "收到图书：" + book.getTitle();
    }

    @GetMapping("/books")
    public List<Book> getBooks() {
        return bookMapper.findAll();
    }

    @GetMapping("/books/search")
    public List<Book> searchBooks(@RequestParam  String author){
        return bookMapper.findByAuthor(author);
}

    @PutMapping("/books/{id}")
    public String updateBook(@PathVariable int id, @RequestBody Book updateBook){
    updateBook.setId(id);
    int rows = bookMapper.update(updateBook);

    if(rows >  0){
        return "更新成功";
    }
        return "没有找到图书";
    }

    @DeleteMapping ("/books/{id}")
    public String deleteBook(@PathVariable int id){
       int rows = bookMapper.deleteById(id);

       if(rows > 0 ){
        return "删除成功";
       }
        return  "没有找到图书";
    }

    @GetMapping  ("/double")
    public  String doubleNumber(@RequestParam int number){
        return String.valueOf(number*2);
    }
}
