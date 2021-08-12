package com.example.exam.application;

import com.example.exam.domain.FixedExtension;
import com.example.exam.persistance.FixedExtensionRepository;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class FixedExtensionService {

    private final FixedExtensionRepository fixedExtensionRepository;

    public FixedExtensionService(FixedExtensionRepository fixedExtensionRepository) {
        this.fixedExtensionRepository = fixedExtensionRepository;
    }

    public FixedExtension findBySeq() throws Exception {
        return fixedExtensionRepository.findBySeq(1L)
                .orElseThrow(() -> new NoSuchElementException("서버 에러 잘못된 번호"));
    }

    public void updateBat(boolean bat) {
        fixedExtensionRepository.updateBat(bat);
    }

    public void updateCmd(boolean cmd) {
        fixedExtensionRepository.updateCmd(cmd);
    }

    public void updateCom(boolean Com) {
        fixedExtensionRepository.updateCom(Com);
    }

    public void updateCpl(boolean Cpl) {
        fixedExtensionRepository.updateCpl(Cpl);
    }

    public void updateExe(boolean exe) {
        fixedExtensionRepository.updateExe(exe);
    }

    public void updateScr(boolean Scr) {
        fixedExtensionRepository.updateScr(Scr);
    }

    public void updateJs(boolean js) {
        fixedExtensionRepository.updateJs(js);
    }

}
