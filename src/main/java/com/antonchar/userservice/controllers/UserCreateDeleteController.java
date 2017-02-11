package com.antonchar.userservice.controllers;

import com.antonchar.userservice.services.UserService;
import com.antonchar.userservice.services.dto.UserDto;
import com.antonchar.userservice.util.UserValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;
import java.time.LocalDateTime;

@Slf4j
@Controller
@RequestMapping("/user")
@SessionAttributes(value = {"userNum", "newUser"})
public class UserCreateDeleteController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String showAddUserForm(Model model) {
        log.info("GET: Add new user page");
        model.addAttribute("newUser", new UserDto());
        return "user_add_form";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addUser(@ModelAttribute("newUser") @Valid UserDto user, BindingResult result,
                          SessionStatus sessionStatus) {
        log.info("POST: Add new user");
        new UserValidator().validate(user, result);

        if (result.hasErrors()) {
            log.error("Invalid new user data: {}", user);
            return "user_add";
        }

        user.setCreationDate(LocalDateTime.now());
        UserDto savedUser = userService.save(user);

        sessionStatus.setComplete();
        log.info("New user saved successfully! {}", user);
        return String.format("redirect:/user/%d?saved=true", savedUser.getId());
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String deleteUser(@RequestParam Long id, @RequestParam(required = false) Integer page,
                             @RequestParam(required = false) String query, SessionStatus sessionStatus) {
        log.info("POST: Delete user with id = {}", id);
        logRequestSender(page, query);

        userService.delete(id);
        sessionStatus.setComplete();
        return "redirect:/users" + (page != null ? "/pages/" + page : "/search?query=" + query);
    }

    private void logRequestSender(Integer page, String query) {
        if (page != null) {
            log.info("Delete request from the user list view");
        } else if (query != null) {
            log.info("Delete request from the search view");
        } else {
            log.warn("Unknown delete request sender");
        }
    }
}