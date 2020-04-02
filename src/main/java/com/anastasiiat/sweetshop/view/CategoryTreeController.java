package com.anastasiiat.sweetshop.view;

import com.anastasiiat.sweetshop.util.IterableUtils;
import com.anastasiiat.sweetshop.error.InvalidCategoryOperationException;
import com.anastasiiat.sweetshop.persistence.entity.Category;
import com.anastasiiat.sweetshop.service.CategoryService;
import com.anastasiiat.sweetshop.validator.CategoryValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CategoryTreeController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryValidator categoryValidator;

    @Autowired
    private Environment environment;

    @GetMapping("/categoryTree")
    public String categoryTree(Model model) {
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("selectedCategory", new Category());
        return "categoryTreePage";
    }

    @GetMapping("/addCategoryForm")
    public String addCategoryFormOpen(Model model) {
        model.addAttribute("editableCategory", new Category());
        return "addOrEditCategory";
    }

    @GetMapping("/editCategoryForm/{categoryId}")
    public String editCategoryFormOpen(Model model, @PathVariable("categoryId") Integer categoryId) {
        model.addAttribute("parentNonEditable", IterableUtils.isNotEmpty(categoryService.findSubcategories(categoryId)));
        model.addAttribute("editableCategory", categoryService.findCategoryById(categoryId));
        return "addOrEditCategory";
    }

    @RequestMapping(value = "/saveCategory", method = RequestMethod.POST)
    public String saveCategory(@ModelAttribute("editableCategory") Category editableCategory) {
        categoryService.saveCategory(editableCategory);
        return "redirect:/categoryTree";
    }

    @RequestMapping(value = "/deleteCategory", method = RequestMethod.POST)
    public ModelAndView deleteCategory(@ModelAttribute("selectedCategory") Category selectedCategory) {
        try {
            categoryValidator.validateToDelete(selectedCategory.getCategoryId());
            categoryService.deleteCategory(selectedCategory.getCategoryId());
        } catch (InvalidCategoryOperationException e) {
            logger.error("Validation error during deleting category", e);
            return buildCategoryModelAndViewIfError(e.getMessage());

        } catch (Exception e) {
            logger.error("Error during deleting category", e);
            return buildCategoryModelAndViewIfError(environment.getProperty("deleting.category.error"));
        }
        return new ModelAndView("redirect:/categoryTree");
    }

    @ModelAttribute("rootCategories")
    public Iterable<Category> getRootCategories() {
        return categoryService.getParentCategories();
    }

    private ModelAndView buildCategoryModelAndViewIfError(String errorMessage) {
        ModelAndView modelAndView = new ModelAndView("categoryTreePage");
        modelAndView.addObject("errorMsg", errorMessage);
        modelAndView.addObject("categories", categoryService.getAllCategories());
        modelAndView.addObject("selectedCategory", new Category());
        return modelAndView;
    }

}
