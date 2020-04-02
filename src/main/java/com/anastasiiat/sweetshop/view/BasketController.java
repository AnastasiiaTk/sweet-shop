package com.anastasiiat.sweetshop.view;

import com.anastasiiat.sweetshop.service.BasketService;
import com.anastasiiat.sweetshop.service.OrderService;
import com.anastasiiat.sweetshop.service.SecurityService;
import com.anastasiiat.sweetshop.service.UserService;
import com.anastasiiat.sweetshop.persistence.entity.Product;
import com.anastasiiat.sweetshop.persistence.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.Map;

@Controller
public class BasketController {

    @Autowired
    private BasketService basketService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserService userService;

    @GetMapping("/basket/open")
    public String openBasket(Model model) {
        Map<Product, Integer> basketItems = basketService.readBasketItems(getSessionId());
        model.addAttribute("selectedProductsMap", basketItems);
        model.addAttribute("totalPrice", basketService.countTotalPrice(basketItems));
        return "basket";
    }

    @RequestMapping(value = "/basket/addToBasket", method = RequestMethod.POST)
    public String addToBasket(@ModelAttribute("product") Product product) {
        basketService.addToBasket(getSessionId(), product);
        return "redirect:/catalogue";
    }

    @RequestMapping(value = "/basket/changeProductAmount", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public void changeProductAmount(@RequestParam("productId") Integer productId, @RequestParam("productAmount") Integer productAmount) {
        Product product = basketService.findProductByIdFromCache(getSessionId(), productId);
        basketService.changeProductAmount(getSessionId(), product, productAmount);
    }

    @RequestMapping(value = "/basket/deleteFromBasket", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteFromBasket(@RequestParam("productId") Integer productId) {
        Product product = basketService.findProductByIdFromCache(getSessionId(), productId);
        basketService.deleteProduct(getSessionId(), product);
    }


    @GetMapping("/basket/basketAmount")
    public @ResponseBody Integer getBasketItemsAmount() {
        return basketService.readBasketItems(getSessionId()).size();
    }

    @GetMapping("/basket/totalPrice")
    public @ResponseBody Double getTotalPrice() {
        return basketService.countTotalPrice(basketService.readBasketItems(getSessionId()));
    }

    @RequestMapping(value = "saveOrder", method = RequestMethod.GET)
    public String saveOrder() {
        User user = userService.findByUsername(securityService.findLoggedInUsername());
        orderService.saveOrderFromBasket(basketService.getBasketData(getSessionId()), user);
        basketService.invalidateCache();
        return "redirect:/orders/open";
    }

    private String getSessionId() {
        return RequestContextHolder.currentRequestAttributes().getSessionId();
    }
}
