package ru.vlasenko.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.vlasenko.model.User;
import ru.vlasenko.repo.UserRepo;
import ru.vlasenko.service.PushNotificationService;

/**
 * Created by andrew on 08/12/16.
 */
@RestController
public class PushController {

    @Autowired
    private UserRepo repo;

    @Autowired
    private PushNotificationService pushNotificationService;

    @RequestMapping(value = "/user/{id}/push", method = RequestMethod.POST)
    public void push(@RequestBody String message, @PathVariable(name = "id")String userId) {
        User user = repo.findOne(userId);
        if (user != null && user.getDevice() != null) {
            pushNotificationService.push(user, message);
        }
    }
}
