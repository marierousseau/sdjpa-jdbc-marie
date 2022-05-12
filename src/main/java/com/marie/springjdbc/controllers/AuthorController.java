package com.marie.springjdbc.controllers;


import com.marie.springjdbc.dao.authordao.AuthorDao;
import com.marie.springjdbc.domain.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AuthorController {

    @Qualifier("authorDaoImpl")
    @Autowired
    private AuthorDao authorDao;

    // display list of author
    @GetMapping("admin/newpage")
    public String viewHomePage(Model model) {
        return findPaginated(1, "firstName", "asc", model);
    }

    @GetMapping("/showNewAuthorForm")
    public String showNewAuthorForm(Model model) {
        // create model attribute to bind form data
        Author author = new Author();
        model.addAttribute("author", author);
        return "new_author";
    }

    @PostMapping("/saveAuthor")
    public String saveAuthor(@ModelAttribute("author") Author author) {
        // save author to database
        authorDao.saveNewAuthor(author);
        return "redirect:admin/newpage";
    }

    @GetMapping("/showFormForUpdate/{id}")
    public String showFormForUpdate(@PathVariable(value = "id") long id, Model model) {

        // get author from the dao
        Author author = authorDao.getById(id);

        // set author as a model attribute to pre-populate the form
        model.addAttribute("author", author);
        return "update_author";
    }

    @GetMapping("/deleteAuthor/{id}")
    public String deleteAuthor(@PathVariable(value = "id") long id) {

        // call delete author method
        this.authorDao.deleteAuthorById(id);
        return "redirect:/admin/newpage";
    }

    @RequestMapping("/searchAuthors")
    public String viewHomePage(Model model, @Param("keyword") String keyword) {
        List<Author> listAuthors = authorDao.findAuthorsByFirstNameOrLastName(keyword,keyword);
        model.addAttribute("listAuthors", listAuthors);
        model.addAttribute("keyword", keyword);

        return "newpage";
    }


    @GetMapping("/page/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo,
                                @RequestParam("sortField") String sortField,
                                @RequestParam("sortDir") String sortDir,
                                Model model) {
        int pageSize = 5;

        Page<Author> page = authorDao.findPaginated(pageNo, pageSize, sortField, sortDir);
        List<Author> listAuthors = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("listAuthors", listAuthors);
        return "newpage";
    }
}

















//    @Autowired
//    private AuthorDaoHibernate authorDaoHibernate;
//
//    //display list of authors
//    @RequestMapping(value="admin/newpage", method=GET)
//    public String viewHomePage(Model model) {
//        model.addAttribute("listAuthors",authorDaoHibernate.
//        return "newpage";
//
//    }
