package com.example.exam.presentation;

import com.example.exam.application.CustomExtensionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Slf4j
public class CustomExtensionController {

    private final CustomExtensionService customExtensionService;
    private static final int NAMING_LENGTH_LIMIT = 20;

    public CustomExtensionController(CustomExtensionService customExtensionService) {
        this.customExtensionService = customExtensionService;
    }

    @PostMapping("/custom")
    public String create(
            @RequestParam(value = "name") String name) {
        limitNameCount(name);
        customExtensionService.create(name);
        return "redirect:";
    }

    @GetMapping("/customDelete")
    public String delete(
            @RequestParam(value = "seq") Long seq) {
        customExtensionService.delete(seq);
        return "redirect:";
    }

    private void limitNameCount(String name) {
        if (name.length() > NAMING_LENGTH_LIMIT) {
            throw new DataIntegrityViolationException("20자 이하로 입력해주세요");
        }
    }
}
