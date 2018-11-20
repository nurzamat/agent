package com.agent.controllers;

import com.agent.entities.User;
import com.agent.repositories.RoleRepository;
import com.agent.services.UserService;
import com.agent.util.PageWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.validation.Valid;
import java.security.Principal;


@Controller
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    public void setUserService(UserService _userService) {
        this.userService = _userService;
    }

    /**
     * List all users
     */

    @RequestMapping(method = RequestMethod.GET)
    public String list(Model model, Pageable pageable){

        Page<User> userPage = userService.listAllByPage(pageable, true);
        PageWrapper<User> page = new PageWrapper<User>(userPage, "/users");
        model.addAttribute("users", page.getContent());
        model.addAttribute("page", page);
        return "users";
    }

    /**
     * Edit a specific user by its id.
     */

    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {

        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        model.addAttribute("roles", roleRepository.findAll());
        return "userform";
    }

    /**
     * New user.
     */
    @RequestMapping("/new")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleRepository.findAll());
        return "userform";
    }

    /**
     * Save user to database.
     */
    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public String saveUser(@Valid User user, BindingResult bindingResult, Model model) {

        if(validate(user, bindingResult))
        {
            model.addAttribute("user", user);
            model.addAttribute("roles", roleRepository.findAll());
            return "userform";
        }
        userService.saveUser(user);

        return "redirect:/users";
    }

    /**
     * Delete user by its id.
     */
    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, Principal principal) {

       //User user = userService.getUserById(id);
        userService.deleteUser(id);

        return "redirect:/users";
    }

    private boolean validate(@Valid User user, BindingResult bindingResult) {

        boolean hasError = false;
        if (bindingResult.hasErrors()) {
            hasError = true;
        }
        else
        {
            if(userService.checkUserForUsername(user))
            {
                hasError = true;
                bindingResult.rejectValue("userName", "error.userName", "This Username already exists");
            }
        }
        return hasError;
    }

}
