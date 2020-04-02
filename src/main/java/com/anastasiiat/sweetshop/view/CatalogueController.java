package com.anastasiiat.sweetshop.view;

import com.anastasiiat.sweetshop.service.CategoryService;
import com.anastasiiat.sweetshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class CatalogueController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/catalogue")
    public String catalogueOpen(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "catalogue";
    }

    @GetMapping("/getProductDetails/{productId}")
    public String getProductDetails(Model model, @PathVariable("productId") Integer productId) {
        model.addAttribute("product", productService.findProductById(productId));
        return "productDetails";
    }
}
