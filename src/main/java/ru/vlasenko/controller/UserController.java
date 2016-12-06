package ru.vlasenko.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.vlasenko.model.User;
import ru.vlasenko.repo.UserRepo;

/**
 * Created by andreyvlasenko on 04/12/16.
 */
@RestController
public class UserController {

    @Autowired
    private UserRepo repo;

    @RequestMapping(value = "/user", method = {RequestMethod.PUT, RequestMethod.POST})
    public User saveUser(@RequestBody User user) {
        return repo.save(user);
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public User getUser(String id) {
        return repo.findOne(id);
    }
}
