package com.example.exam.ui;

import com.example.exam.application.CustomExtensionService;
import com.example.exam.application.FixedExtensionService;
import com.example.exam.domain.CustomExtension;
import com.example.exam.domain.FixedExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(StartController.class)
class StartControllerTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CustomExtensionService customExtensionService;

    @MockBean
    private FixedExtensionService fixedExtensionService;

    @BeforeEach
    public void setUp() throws Exception {
        customDtoInit();
        fixedInit();
    }

    private void fixedInit() throws Exception {
        FixedExtension fixedExtension = FixedExtension.builder().seq(1L).build();
        given(fixedExtensionService.findBySeq()).willReturn(fixedExtension);
    }

    private void customDtoInit() {
        List<CustomExtension> customExtensions = new ArrayList<>();
        customExtensions.add(CustomExtension.builder().name("aa").build());
        given(customExtensionService.findAll()).willReturn(customExtensions);
    }

    @Test
    @DisplayName("DB의 정보를 객체와 페이지를 넘겨준다.")
    public void init() throws Exception {
        mvc.perform(get("/"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("custom", "Fixed", "customs"))
                .andExpect(view().name("index"));
    }
}