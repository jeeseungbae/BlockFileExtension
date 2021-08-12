package com.example.exam.presentation;

import com.example.exam.application.FixedExtensionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class FixedExtensionController {

    private final FixedExtensionService fixedExtensionService;
    private static final String REQUEST_PARAM = "resource";

    @Autowired
    public FixedExtensionController(FixedExtensionService fixedExtensionService) {
        this.fixedExtensionService = fixedExtensionService;
    }

    @GetMapping("/fixedBat")
    public String updateBat(
            @RequestParam(value = REQUEST_PARAM) boolean resourceBat) throws Exception {
        fixedExtensionService.updateBat(resourceBat);
        return "redirect:";
    }

    @GetMapping("/fixedCmd")
    public String updateCmd(
            @RequestParam(value = REQUEST_PARAM) boolean resourceCmd) {
        fixedExtensionService.updateCmd(resourceCmd);
        return "redirect:";
    }

    @GetMapping("/fixedCom")
    public String updateCom(
            @RequestParam(value = REQUEST_PARAM) boolean resourceCom) {
        fixedExtensionService.updateCom(resourceCom);
        return "redirect:";
    }

    @GetMapping("/fixedCpl")
    public String updateCpl(
            @RequestParam(value = REQUEST_PARAM) boolean resourceCpl) {
        fixedExtensionService.updateCpl(resourceCpl);
        return "redirect:";
    }

    @GetMapping("/fixedExe")
    public String updateExe(
            @RequestParam(value = REQUEST_PARAM) boolean resourceExe) {
        fixedExtensionService.updateExe(resourceExe);
        return "redirect:";
    }

    @GetMapping("/fixedScr")
    public String updateScr(
            @RequestParam(value = REQUEST_PARAM) boolean resourceScr) {
        fixedExtensionService.updateScr(resourceScr);
        return "redirect:";
    }

    @GetMapping("/fixedJs")
    public String updateJs(
            @RequestParam(value = REQUEST_PARAM) boolean resourceJs) {
        fixedExtensionService.updateJs(resourceJs);
        return "redirect:";
    }
}
