package org.example.learningos.controller;

import java.util.HashMap;
import java.util.Map;
import org.example.learningos.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DuplicateCheckController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/check-username")
    public Map<String, Boolean> checkUsername(@RequestParam("value") String value) {
        boolean duplicate = userRepository.findByUsername(value).isPresent();
        Map<String, Boolean> response = new HashMap<>();
        response.put("duplicate", duplicate);
        return response;
    }

    @GetMapping("/check-nickname")
    public Map<String, Boolean> checkNickname(@RequestParam("value") String value) {
        boolean duplicate = userRepository.findByNickname(value).isPresent();
        Map<String, Boolean> response = new HashMap<>();
        response.put("duplicate", duplicate);
        return response;
    }
}
