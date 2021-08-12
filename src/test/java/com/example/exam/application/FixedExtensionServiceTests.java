package com.example.exam.application;

import com.example.exam.persistance.FixedExtensionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

class FixedExtensionServiceTests {

    private FixedExtensionService fixedExtensionService;

    @Mock
    private FixedExtensionRepository fixedExtensionRepository;

    @BeforeEach
    public void SetUp() {
        MockitoAnnotations.openMocks(this);
        doNothing().when(fixedExtensionRepository).updateBat(anyBoolean());
        fixedExtensionService = new FixedExtensionService(fixedExtensionRepository);
    }

    @Test
    @DisplayName("Repository.updateBat 실행시킨다.")
    public void update() {
        fixedExtensionService.updateBat(true);
        verify(fixedExtensionRepository).updateBat(anyBoolean());
    }
}