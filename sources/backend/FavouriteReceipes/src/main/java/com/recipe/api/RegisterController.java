//package com.recipe.api;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//
//@Controller
//public class RegisterController {
//    @Autowired
//    PasswordEncoder passwordEncoder;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @PostMapping("/register")
//    public String doRegister(@ModelAttribute UserDto userDto) {
//        String encodedPassword  = passwordEncoder.encode(userDto.getPassword1());
//
//        User user = new User();
//        user.setEnabled(Boolean.TRUE);
//        user.setPassword(encodedPassword);
//        user.setUsername(userDto.getUsername());
//
//        UserAuthority boardAuthority = new UserAuthority();
//        boardAuthority.setAuthority("BOARD");
//        boardAuthority.setUser(user);
//        user.getAuthorities().add(boardAuthority);
//        userRepository.save(user);
//
//        return "register-success";
//    }
//}