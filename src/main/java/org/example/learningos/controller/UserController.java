package org.example.learningos.controller;

import org.example.learningos.model.User;
import org.example.learningos.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    // 회원가입 폼을 보여줍니다.
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register"; // src/main/resources/templates/register.html
    }

    // 회원가입 요청을 처리합니다.
    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") User user,
                               BindingResult bindingResult,
                               Model model) {

        if (bindingResult.hasErrors()) {
            return "register";
        }

        String message = userService.registerUser(user);

        // 회원가입 성공 시 메시지(예: 플래시 속성)나 redirect 후 로그인 페이지로 이동
        if ("회원가입이 완료되었습니다.".equals(message)) {
            return "redirect:/login";
        } else {
            model.addAttribute("message", message);
            return "register";
        }
    }

}
