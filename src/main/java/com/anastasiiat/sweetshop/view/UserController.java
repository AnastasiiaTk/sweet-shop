package com.anastasiiat.sweetshop.view;

import com.anastasiiat.sweetshop.persistence.entity.User;
import com.anastasiiat.sweetshop.service.SecurityService;
import com.anastasiiat.sweetshop.service.UserService;
import com.anastasiiat.sweetshop.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;

    @GetMapping("/login")
    public String login() {
        return "/login";
    }

    @GetMapping("/registration")
    public String registrationPageOpen(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(@ModelAttribute("user") User user, BindingResult bindingResult, Model model) {
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "registration";
        }
        userService.save(user);
        return "redirect:/login";
    }

    @RequestMapping(value = "/getCurrentUser", method = RequestMethod.GET)
    @ResponseBody
    public String getCurrentUser() {
        return securityService.findLoggedInUsername();
    }

}
