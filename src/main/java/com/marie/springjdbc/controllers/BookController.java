package com.marie.springjdbc.controllers;


import com.marie.springjdbc.dao.bookdao.BookDao;
import com.marie.springjdbc.domain.Author;
import com.marie.springjdbc.domain.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class BookController {

    @Qualifier("bookDaoImpl")
    @Autowired
    private BookDao bookDao;


    @GetMapping("admin/book_list")
    public String viewHomePage(Model model) {

        return findPaginated(1, "title", "asc", model);
    }


    @RequestMapping("/searchBooks")
    public String viewHomePage(Model model, @Param("keyword") String keyword) {
        List<Book> listBooks = bookDao.findBookByTitleOrId(keyword, keyword);
        model.addAttribute("listBooks", listBooks);
        model.addAttribute("keyword", keyword);


        return "book_list";

    }

    @GetMapping("/showNewBookForm")
    public String showNewBookForm(Model model) {
        // create model attribute to bind form data
        Book book = new Book();
        model.addAttribute("book", book);
        return "new_book";
    }

    @PostMapping("/saveBook")
    public String saveBook(@ModelAttribute("book") Book book) {
        // save book to database
        bookDao.saveNewBook(book);
        return "redirect:admin/book_list";
    }


    @GetMapping("/showFormForBookUpdate/{id}")
    public String showFormForBookUpdate(@PathVariable(value = "id") long id, Model model) {

        // get author from the dao
        Book book = bookDao.getById(id);

        // set author as a model attribute to pre-populate the form
        model.addAttribute("book", book);
        return "update_book";
    }

    @GetMapping("/deleteBook/{id}")
    public String deleteAuthor(@PathVariable(value = "id") long id) {

        // call delete author method
        this.bookDao.deleteBookById(id);
        return "redirect:/admin/book_list";
    }



    @GetMapping("/page2/{pageNo2}")
    public String findPaginated(@PathVariable(value = "pageNo2") int pageNo2,
                                @RequestParam("sortField2") String sortField2,
                                @RequestParam("sortDir2") String sortDir2,
                                Model model) {
        int pageSize = 5;

        Page<Book> page2 = bookDao.findPaginated(pageNo2, pageSize, sortField2, sortDir2);
        List<Book> listBooks = page2.getContent();

        model.addAttribute("currentPage2", pageNo2);
        model.addAttribute("totalPages2", page2.getTotalPages());
        model.addAttribute("totalItems2", page2.getTotalElements());

        model.addAttribute("sortField2", sortField2);
        model.addAttribute("sortDir2", sortDir2);
        model.addAttribute("reverseSortDir2", sortDir2.equals("asc") ? "desc" : "asc");

        model.addAttribute("listBooks", listBooks);
        return "book_list";


    }
}
