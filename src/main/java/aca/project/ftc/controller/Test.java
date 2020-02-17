package aca.project.ftc.controller;

import aca.project.ftc.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import aca.project.ftc.model.SignupRequest;
import aca.project.ftc.model.User;

import java.util.List;

@CrossOrigin
@RestController
public class Test {


    @Autowired
    private UserRepository userRepository;


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public Test(UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    @RequestMapping("/some")
    public String some() {
        return "hel";
    }

    @RequestMapping("/test")
    public String test() {
        SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return "hel";
    }


//    @RequestMapping("/notification/{id}")
//    public Iterable<Notification> byUsername(@PathVariable Long id) {
//        return notificationRepository.findBySenderId(id);
//    }

//    @RequestMapping("/notifications/{id}")
//    public List<Notification> senderNotification(@PathVariable Long id){
//        return  notificationRepository.findBySenderId(id);
//
//    }

    //    @GetMapping(value = "")
//    @ResponseBody
//    public List<Notification> getUsers(@PathVariable Long id) {
//        return userRepository.findById(id);
//    }
////
//
//    @RequestMapping("/notifications")
//    public Iterable<Notification> notification() {
//        return notificationRepository.findAll();
//    }

//
//    @RequestMapping(value = "/notification", method = RequestMethod.POST)
//    public Notification notification(@RequestBody Notification notificationRequest) {
//        Notification notification = new Notification();
//        notification.setMessage(notificationRequest.getMessage());
////        notification.setUser(notificationRequest.getUser());
////        notification.setSender(notificationRequest.getSender());
//        notificationRepository.save(notification);
//        return notification;
//    }



}
