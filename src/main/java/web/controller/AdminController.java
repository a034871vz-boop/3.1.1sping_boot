package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import web.model.User;
import web.service.UserService;

@Controller
public class AdminController {
    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin")
    public String getAdminPage(Model model) {
        model.addAttribute("listUsers", userService.getAllUsers());
        return "admin";
    }

    @GetMapping("/createUser")
    public String createUser(ModelMap model) {
        model.addAttribute("newUser", new User());
        model.addAttribute("allRoles", userService.getAllRoles());
        return "newUser";
    }
    @PostMapping("/create")
    public String createUser(@ModelAttribute User user) {
        userService.createUser(user);
        return "redirect:/admin";
    }

    @PostMapping("/editUser/{id}")
    public String editUser(Model model, @PathVariable("id") Long id) {
        User user = userService.getUserById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("editUser", user);
        model.addAttribute("allRoles", userService.getAllRoles());
        return "editUser";
    }

    @PostMapping("/edit")
    public String edit(@ModelAttribute User user) {
        userService.updateUser(user);
        return "redirect:/admin";
    }

    @PostMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable("id") long id) {
     userService.deleteUser(id);
        return "redirect:/admin";
    }
}
