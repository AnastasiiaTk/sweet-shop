package com.anastasiiat.sweetshop.view;

import com.anastasiiat.sweetshop.error.InvalidProductOperationException;
import com.anastasiiat.sweetshop.service.ProductService;
import com.anastasiiat.sweetshop.validator.ProductValidator;
import com.anastasiiat.sweetshop.persistence.entity.Category;
import com.anastasiiat.sweetshop.persistence.entity.Product;
import com.anastasiiat.sweetshop.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;

@Controller
public class ManageProductsController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductValidator productValidator;

    @Autowired
    private Environment environment;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @GetMapping("/manageProducts")
    public String getAllProducts(Model model) {
        model.addAttribute("selectedCategory", new Category());
        model.addAttribute("products", productService.getAllProducts());
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("selectedProduct", new Product());
        return "manageProducts";
    }

    @RequestMapping(value = "/getAllProducts", method = RequestMethod.GET)
    public @ResponseBody
    Iterable<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @RequestMapping(value = "/filterByCategory/{categoryId}", method = RequestMethod.GET)
    public @ResponseBody
    Iterable<Product> filterByCategory(@PathVariable("categoryId") Integer categoryId) {
        return productService.findByСategoryId(categoryId);
    }

    @RequestMapping(value = "/saveProduct", method = RequestMethod.POST)
    public String saveProduct(@ModelAttribute("editableProduct") Product editableProduct, @RequestParam("file") MultipartFile file) {
        saveImage(file, editableProduct);
        productService.saveProduct(editableProduct);
        return "redirect:/manageProducts";
    }

    @GetMapping("/addProductForm")
    public String addProductFormOpen(Model model) {
        model.addAttribute("editableProduct", new Product());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "addOrEditProduct";
    }

    @GetMapping("/editProductForm/{productId}")
    public String editProductFormOpen(Model model, @PathVariable("productId") Integer productId) {
        model.addAttribute("editableProduct", productService.findProductById(productId));
        model.addAttribute("categories", categoryService.getAllCategories());
        return "addOrEditProduct";
    }

    @RequestMapping(value = "/deleteProduct", method = RequestMethod.POST)
    public ModelAndView deleteCategory(@ModelAttribute("selectedProduct") Product selectedProduct) {
        try {
            productValidator.validateToDelete(selectedProduct.getProductId());
            productService.deleteProduct(selectedProduct.getProductId());
        } catch (InvalidProductOperationException e) {
            logger.error("Validation error during deleting product", e);
            return buildCategoryModelAndViewIfError(e.getMessage());
        } catch (Exception e) {
            logger.error("Error during deleting product", e);
            return buildCategoryModelAndViewIfError(environment.getProperty("deleting.product.error"));
        }
        return new ModelAndView("redirect:/manageProducts");
    }

    private ModelAndView buildCategoryModelAndViewIfError(String errorMessage) {
        ModelAndView modelAndView = new ModelAndView("manageProducts");
        modelAndView.addObject("errorMsg", errorMessage);
        modelAndView.addObject("selectedCategory", new Category());
        modelAndView.addObject("products", productService.getAllProducts());
        modelAndView.addObject("categories", categoryService.getAllCategories());
        modelAndView.addObject("selectedProduct", new Product());
        return modelAndView;
    }

    private void saveImage(MultipartFile file, Product editableProduct) {
        if (!file.isEmpty()) {
            String photoPath = productService.buildUniquePhotoPath(file.getOriginalFilename());
            editableProduct.setPhotoPath(photoPath);
            try {
                String fullPath = productService.getFullPathToStorage() + photoPath;
                file.transferTo(new File(fullPath));
            } catch (Exception e) {
                logger.error("ERROR during uploading image: ", e);
            }
        }
    }

}
