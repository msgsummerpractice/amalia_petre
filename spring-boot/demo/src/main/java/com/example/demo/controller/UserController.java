package com.example.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import com.example.demo.service.UserService;
import java.util.List;
import com.example.demo.model.User;
import org.springframework.web.bind.annotation.PathVariable;
import com.example.demo.model.ConfiguratieVehicul;

@RestController
public class UserController {

    // Autowire the ConfiguratieVehicul bean to access its properties
    @Autowired
    private ConfiguratieVehicul configuratieVehicul;

    Logger logger = LoggerFactory.getLogger(UserController.class);

    public UserController() {
        System.out.println("UserController initialized");
    }

    @Autowired
    private UserService userService;

    // Inject the user ID from application.properties
    @Value("${user.id}")
    private Long id;

    @GetMapping("/users")
    public List<User> getUsers() {
        return userService.getAllUsers();
    }

    @RequestMapping("/")
    public String index() {
        logger.trace("A TRACE Message");
        logger.debug("A DEBUG Message");
        logger.info("An INFO Message");
        logger.warn("A WARN Message");
        logger.error("An ERROR Message");

        return "Howdy! Check out the Logs to see the output...";
    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }
    
    @RequestMapping("/userSpecial")
    public User getUserSpecial() {
        return userService.getUserById(id);
    }

    @RequestMapping("/vehicul")
    public String getVehicul() {
        return configuratieVehicul.getMarca() + " " + configuratieVehicul.getTip();
    }
}
