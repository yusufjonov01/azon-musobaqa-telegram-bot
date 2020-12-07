package uz.azon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.azon.service.UserTest;

@RestController
@RequestMapping("/api/test")
public class TestContoller {
    @Autowired
    UserTest userTest;

    @GetMapping
    public boolean testUser() {
        userTest.saveUser();
        return true;
    }
}
