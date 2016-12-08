package ru.vlasenko.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.vlasenko.model.User;
import ru.vlasenko.repo.UserRepo;

import java.util.Date;

/**
 * Created by andreyvlasenko on 04/12/16.
 */
@RestController
public class UserController {

    @Autowired
    private UserRepo repo;

    @RequestMapping(value = "/user", method = {RequestMethod.PUT, RequestMethod.POST})
    public User saveUser(@RequestBody User user) {
        if (user.getDateRegistered() == null) {
            user.setDateRegistered(new Date());
        }
        return repo.save(user);
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public User getUser(@PathVariable(value="id")String id) {
        return repo.findOne(id);
    }
}
