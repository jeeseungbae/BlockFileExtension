package com.example.exam.application;

import com.example.exam.domain.CustomExtension;
import com.example.exam.exception.DuplicateNameException;
import com.example.exam.exception.FixedSameDataException;
import com.example.exam.persistance.CustomExtensionRepository;
import org.apache.ibatis.exceptions.TooManyResultsException;
import org.hibernate.procedure.NamedParametersNotSupportedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class CustomExtensionService {

    private final CustomExtensionRepository customExtensionRepository;
    private static final int DATA_LIMIT_VOLUME = 200;
    private static final String EXPRESSION = "^[a-zA-Z]*$";

    private List<String> fixedNames;

    @Autowired
    public CustomExtensionService(CustomExtensionRepository customExtensionRepository) {
        this.customExtensionRepository = customExtensionRepository;
    }

    @PostConstruct
    public void init() {
        fixedNames = new ArrayList<>();
        fixedNames.add("bat");
        fixedNames.add("cmd");
        fixedNames.add("com");
        fixedNames.add("cpl");
        fixedNames.add("exe");
        fixedNames.add("scr");
        fixedNames.add("js");
    }

    public CustomExtension create(String name) {
        namePatternCheck(name);
        CustomExtension customExtension = CustomExtension.builder()
                .name(name)
                .build();
        return customExtensionRepository.save(customExtension);
    }

    private void namePatternCheck(String name) {
        Pattern namingCheck = Pattern.compile(EXPRESSION);
        Matcher namingMatching = namingCheck.matcher(name);

        if (namingMatching.find()) {
            namingDuplicateCheck(name);
            return;
        }
        throw new NamedParametersNotSupportedException("????????? ???????????? ?????????.");
    }

    private void namingDuplicateCheck(String name) {
        Optional<CustomExtension> customExtension = customExtensionRepository.findByName(name);
        customExtension.ifPresent(namePresent -> {
            throw new DuplicateNameException("????????? ???????????? ???????????????.");
        });
        selectFixedExtension(name);
    }

    private void selectFixedExtension(String name) {
        if (fixedNames.contains(name)) {
            throw new FixedSameDataException("?????? ??????????????? ??????????????????");
        }
        limitDataCount();
    }

    private void limitDataCount() {
        List<CustomExtension> customExtensions = findAll();
        if (customExtensions.size() >= DATA_LIMIT_VOLUME) {
            throw new TooManyResultsException("Data 200?????? ?????????????????????.");
        }
    }

    public List<CustomExtension> findAll() {
        return customExtensionRepository.findAll();
    }

    public void delete(Long seq) {
        customExtensionRepository.deleteBySeq(seq);
    }
}
