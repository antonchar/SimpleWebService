package com.antonchar.userservice.controllers;

import com.antonchar.userservice.services.UserService;
import com.antonchar.userservice.services.dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/users")
public class UserSearchController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String searchUser(@RequestParam(required = false) String query, Model model) {
        if (query != null && !query.isEmpty()) {
            log.info("GET: Search user by name containing '{}'", query);
            List<UserDto> users = userService.findByName(query);

            model.addAttribute("users", users);
            model.addAttribute("query", query);

        } else {
            log.info("GET: Search user page");
        }

        return "user_search";
    }
}
