package com.example.books_management;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/books")
public class BookController {
    @Autowired
    BookRepositary bookRepositary;
    @GetMapping
    public List<Book> getAllBooks(){
        return bookRepositary.findAll();
    }
    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book){
        Book savedBook=bookRepositary.save(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBook);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id,@RequestBody Book book){
        Optional<Book> optionalBook= bookRepositary.findById(id);
        if(optionalBook.isPresent()){
            Book existingBook=optionalBook.get();
            existingBook.setAuthor(book.getAuthor());
            existingBook.setTitle(book.getTitle());
            existingBook.setPublishedYear(book.getPublishedYear());
            Book updatedBook=bookRepositary.save(existingBook);
            return ResponseEntity.status(HttpStatus.OK).body(updatedBook);

        }
        else return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeBook(@PathVariable Long id){
        if(bookRepositary.existsById(id)){
            bookRepositary.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        else{
            return ResponseEntity.notFound().build();
        }


    }

}
