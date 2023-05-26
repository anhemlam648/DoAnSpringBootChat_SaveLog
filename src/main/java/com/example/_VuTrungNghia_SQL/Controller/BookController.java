package com.example._VuTrungNghia_SQL.Controller;

import com.example._VuTrungNghia_SQL.entity.Book;
import com.example._VuTrungNghia_SQL.entity.Category;
import com.example._VuTrungNghia_SQL.repository.IBookRepository;
import com.example._VuTrungNghia_SQL.repository.ICategoryRepository;
import com.example._VuTrungNghia_SQL.services.BookService;
import com.example._VuTrungNghia_SQL.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BookController {
    @Autowired
    private BookService bookService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private IBookRepository bookRepository;
    @Autowired
    private ICategoryRepository categoryRepository;
    @GetMapping
    public String showAllBooks(Model model){
        List<Book> books = bookService.getAllBooks();
        model.addAttribute("book",books);
        return "book/list";
    }
    @GetMapping("/add")
    public String addBookForm(Model model){
        model.addAttribute("book",new Book());
        model.addAttribute("categories",categoryService.getAllCategories());
        return "book/add";
    }
    @PostMapping("/add")
    public String addBook(@Valid @ModelAttribute("categories") Book book, BindingResult result,Model model ) {
        if(result.hasErrors())
        {
            model.addAttribute("categories", categoryService.getAllCategories());
            return "book/add";
        }
        bookService.addBook(book);
        return "redirect:/books";
    }
    @GetMapping("/edit/{id}")
    public String editBook(@PathVariable("id") Long id, Model model) {
        Optional<Book> editbook = bookRepository.findById(id);
        if (!editbook.isPresent()) {
            return "/book/list";
        }
        Book book = editbook.get();
        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("book", book);
        model.addAttribute("categories", categories);
        return "book/edit";
    }

    @PostMapping("/edit/{id}")
    public String updateBook(@PathVariable("id") Long id, @ModelAttribute("book") Book book) {
        Optional<Book> editbook = bookRepository.findById(id);
        if (!editbook.isPresent()) {
            return "redirect:/book/list";
        }
        Book edit = editbook.get();
        edit.setTitle(book.getTitle());
        edit.setAuthor(book.getAuthor());
        edit.setCategory(book.getCategory());
        edit.setPrice(book.getPrice());
        bookRepository.save(edit);
        return "redirect:/books";
    }
    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable("id") Long id){
        Book book = bookService.getBookById(id);
        bookService.deleteBook(id);
        return "redirect:/books";
    }


}