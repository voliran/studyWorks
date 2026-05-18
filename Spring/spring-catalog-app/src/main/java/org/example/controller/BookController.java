package org.example.controller;

import org.example.model.Book;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class BookController {
    private static final List<Book> books= new ArrayList<>();

    @GetMapping("/")
    public String books(Model model) {
        books.add(new Book("На западном фронте без перемен", "Эрих Мария Ремарк", 1928));
        books.add(new Book("Превращение", "Франц Кафка", 1912));
        model.addAttribute("books", books);
        return "books-list";
    }
}
