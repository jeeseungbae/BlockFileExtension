package com.example.exam.presentation;

import com.example.exam.application.CustomExtensionService;
import com.example.exam.domain.CustomExtension;
import com.example.exam.exception.DuplicateNameException;
import com.example.exam.exception.FixedSameDataException;
import org.apache.ibatis.exceptions.TooManyResultsException;
import org.hibernate.procedure.NamedParametersNotSupportedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import javax.validation.ConstraintViolationException;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CustomExtensionController.class)
class CustomExtensionControllerTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CustomExtensionService customExtensionService;

    @BeforeEach
    public void setUp() {
        createData();
    }

    private void createData() {
        CustomExtension customExtension = CustomExtension.builder()
                .seq(1L)
                .name("aa")
                .build();
        given(customExtensionService.create("aa")).willReturn(customExtension);
    }

    @Test
    @DisplayName("redirection 반환하고 save 실행한다.")
    public void create() throws Exception {
        mvc.perform(post("/custom")
                        .param("name", "a./a"))
                .andExpect(status().is3xxRedirection());

        verify(customExtensionService).create(anyString());
    }

    @Test
    @DisplayName("redirection 반환하고 delete 실행한다.")
    public void delete() throws Exception {
        doNothing().when(customExtensionService).delete(anyLong());

        mvc.perform(get("/customDelete")
                        .param("seq", "1"))
                .andExpect(status().is3xxRedirection());

        verify(customExtensionService).delete(anyLong());
    }

    @Nested
    @DisplayName("ErrorHandling Error 페이지 반환")
    public class ExceptionHandling {

        @Test
        @DisplayName("Naming 길이 제한")
        public void namingLimit() throws Exception {
            mvc.perform(post("/custom")
                            .param("name", "asdfgjasdfgjasdfgjasdfgj"))
                    .andExpect(status().isOk())
                    .andExpect(view().name("error"))
                    .andExpect(model().attributeExists("Type", "Message"));
        }

        @Test
        @DisplayName("중복된 데이터")
        public void namingDuplicate() throws Exception {

            given(customExtensionService.create(anyString())).willThrow(new DuplicateNameException(""));
            mvc.perform(post("/custom")
                            .param("name", "asdf"))
                    .andExpect(status().isOk())
                    .andExpect(view().name("error"))
                    .andExpect(model().attributeExists("Type", "Message"));
        }

        @Test
        @DisplayName("영어 외의 데이터")
        public void namingNotSupport() throws Exception {

            given(customExtensionService.create(anyString())).willThrow(new NamedParametersNotSupportedException(""));
            mvc.perform(post("/custom")
                            .param("name", "aㅁㄴㅇㄹsdf"))
                    .andExpect(status().isOk())
                    .andExpect(view().name("error"))
                    .andExpect(model().attributeExists("Type", "Message"));
        }

        @Test
        @DisplayName("고정확장자명에 있는 naming")
        public void FixedSameData() throws Exception {

            given(customExtensionService.create(anyString())).willThrow(new FixedSameDataException(""));
            mvc.perform(post("/custom")
                            .param("name", "bat"))
                    .andExpect(status().isOk())
                    .andExpect(view().name("error"))
                    .andExpect(model().attributeExists("Type", "Message"));
        }

        @Test
        @DisplayName("빈 문자열의 Naming")
        public void namingCheck() throws Exception {
            given(customExtensionService.create(anyString())).willThrow(new ConstraintViolationException("aa", any()));

            mvc.perform(post("/custom")
                            .param("name", ""))
                    .andExpect(status().isOk())
                    .andExpect(view().name("error"))
                    .andExpect(model().attributeExists("Type", "Message"));
        }

        @Test
        @DisplayName("data 개수 초과로 error 페이지 반환")
        public void dataExceed() throws Exception {
            given(customExtensionService.create(anyString())).willThrow(new TooManyResultsException(""));
            mvc.perform(post("/custom")
                            .param("name", "sdf"))
                    .andExpect(status().isOk())
                    .andExpect(view().name("error"))
                    .andExpect(model().attributeExists("Type", "Message"));
        }
    }
}