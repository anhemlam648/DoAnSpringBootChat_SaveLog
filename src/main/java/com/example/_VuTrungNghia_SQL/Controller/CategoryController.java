package com.example._VuTrungNghia_SQL.Controller;


import com.example._VuTrungNghia_SQL.entity.Book;
import com.example._VuTrungNghia_SQL.entity.Category;
import com.example._VuTrungNghia_SQL.repository.ICategoryRepository;
import com.example._VuTrungNghia_SQL.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/categorys")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;


    @Autowired
    private ICategoryRepository iCategoryRepository;

    @GetMapping
    public String showCategory(Model model) {
        List<Category> categoryList = categoryService.getAllCategories();
        model.addAttribute("categories", categoryList);
        return "category/listcategory";
    }

     @GetMapping("/addcategory")
        public String Addcategory(Model model){
         model.addAttribute("category",new Category());
         return "category/addcategory";
     }
        @PostMapping("/addcategory")
        public String addBook(@Valid @ModelAttribute("category") Category categoryes,Model model ) {
            categoryService.addcategory(categoryes);
            return "redirect:/categorys";
     }
    @GetMapping("/edit/{id}")
    public String editBook(@PathVariable("id") Long id, Model model) {
        Optional<Category> categoryedit = iCategoryRepository.findById(id);
        if (!categoryedit.isPresent()) {
            return "/category/listcategory";
        }
        Category category = categoryedit.get();
        List<Category> categories = iCategoryRepository.findAll();
        model.addAttribute("category", category);
        model.addAttribute("categories", categories);
        return "category/editcategory";
    }

    @PostMapping("/edit/{id}")
    public String updateBook(@PathVariable("id") Long id, @ModelAttribute("category") Category category) {
        Optional<Category> categoryedit = iCategoryRepository.findById(id);
        if (!categoryedit.isPresent()) {
            return "redirect:/category/listcategory";
        }
        Category editcategory = categoryedit.get();
        editcategory.setName(category.getName());
        iCategoryRepository.save(editcategory);
        return "redirect:/categorys";
    }
    @GetMapping("/delete/{id}")
    public String deleteCate(@PathVariable("id") Long id){
        Category category = categoryService.getCategoryById(id);
        categoryService.deleteCategory(id);
        return "redirect:/categorys";
    }

}
