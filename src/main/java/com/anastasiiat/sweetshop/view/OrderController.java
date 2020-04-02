package com.anastasiiat.sweetshop.view;

import com.anastasiiat.sweetshop.persistence.entity.User;
import com.anastasiiat.sweetshop.service.OrderService;
import com.anastasiiat.sweetshop.service.SecurityService;
import com.anastasiiat.sweetshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @GetMapping("/orders/open")
    public String openOrders(Model model) {
        User user = userService.findByUsername(securityService.findLoggedInUsername());
        model.addAttribute("userOrders", orderService.findOrdersWithItemsByUser(user));
        return "userOrders";
    }
}
