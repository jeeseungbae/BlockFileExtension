package com.example.exam.presentation;

import com.example.exam.application.FixedExtensionService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(FixedExtensionController.class)
class FixedExtensionControllerTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private FixedExtensionService fixedExtensionService;

    public void setUp() {
        MockitoAnnotations.openMocks(this);
        doNothing().when(fixedExtensionService).updateBat(anyBoolean());
    }

    @Test
    @DisplayName("redirection 반환하고 service 실행한다.")
    public void modifyBat() throws Exception {
        mvc.perform(get("/fixedBat")
                        .param("resource", "true"))
                .andExpect(status().is3xxRedirection());

        verify(fixedExtensionService, times(1)).updateBat(anyBoolean());
    }
}