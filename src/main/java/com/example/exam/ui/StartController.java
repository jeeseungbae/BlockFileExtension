package com.example.exam.ui;

import com.example.exam.application.CustomExtensionService;
import com.example.exam.application.FixedExtensionService;
import com.example.exam.domain.CustomExtension;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StartController {

    private final CustomExtensionService customExtensionService;
    private final FixedExtensionService fixedExtensionService;

    public StartController(CustomExtensionService customExtensionService, FixedExtensionService fixedExtensionService) {
        this.customExtensionService = customExtensionService;
        this.fixedExtensionService = fixedExtensionService;
    }

    @GetMapping("")
    private String start(Model model) throws Exception {
        model.addAttribute("custom", new CustomExtension());
        model.addAttribute("Fixed", fixedExtensionService.findBySeq());
        model.addAttribute("customs", customExtensionService.findAll());
        return "index";
    }
}
