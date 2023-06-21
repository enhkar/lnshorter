package local.ln.controllers;

import jakarta.validation.Valid;
import local.ln.data.UserData;
import local.ln.entities.UserEntity;
import local.ln.services.LinkService;
import local.ln.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    LinkService linkService;

    @GetMapping("/login")
    public String getLoginFrom(Model model) {
        model.addAttribute("user", new UserData());
        return "login";
    }

    @GetMapping("/register")
    public String getResisterForm(Model model) {
        model.addAttribute("user", new UserData());
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid UserData userData, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("user", userData);
            return "register";
        }

        userService.register(userData);
        return "redirect:/";
    }

    @GetMapping("/links")
    public String getLinks(Model model, @AuthenticationPrincipal UserEntity user) {
        if (user == null) {
            return "redirect:/login";
        }

        model.addAttribute("links", linkService.getLinksByUser(user));
        return "link-list";
    }

}
