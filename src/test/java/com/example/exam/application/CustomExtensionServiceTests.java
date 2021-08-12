package com.example.exam.application;

import com.example.exam.domain.CustomExtension;
import com.example.exam.exception.DuplicateNameException;
import com.example.exam.exception.FixedSameDataException;
import com.example.exam.persistance.CustomExtensionRepository;
import org.apache.ibatis.exceptions.TooManyResultsException;
import org.hibernate.procedure.NamedParametersNotSupportedException;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class CustomExtensionServiceTests {

    private CustomExtensionService customExtensionService;

    @Mock
    private CustomExtensionRepository customExtensionRepository;

    @Mock
    private List<String> fixedNames;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        customExtensionService = new CustomExtensionService(customExtensionRepository);
        this.customExtensionService.init();
    }

    @Nested
    @DisplayName("에러를 체크하고 데이터베이스에 저장한다.")
    public class save {
        @Test
        @DisplayName("성공")
        public void create() {
            CustomExtension resource = CustomExtension.builder()
                    .seq(1L)
                    .name("aza")
                    .build();

            given(customExtensionRepository.save(any())).willReturn(resource);

            CustomExtension customExtension = customExtensionService.create("aza");

            Assertions.assertEquals(customExtension.getSeq(), resource.getSeq());
            Assertions.assertEquals(customExtension.getName(), resource.getName());

            verify(customExtensionRepository, times(1)).save(any());
        }

        @Test
        @DisplayName("error : 특수문자 입력")
        public void nameSpecialText() {
            Assertions.assertThrows(NamedParametersNotSupportedException.class, () -> {
                customExtensionService.create("bat.");
            });
        }

        @Test
        @DisplayName("error : 한글 Naming 입력")
        public void nameKorean() {
            Assertions.assertThrows(NamedParametersNotSupportedException.class, () -> {
                customExtensionService.create("이것을입력하면안되오");
            });
        }

        @Test
        @DisplayName("error : 숫자 Naming 입력")
        public void nameNumber() {
            Assertions.assertThrows(NamedParametersNotSupportedException.class, () -> {
                customExtensionService.create("444444");
            });
        }

        @Test
        @DisplayName("error : 숫자+한글 Naming 입력")
        public void nameNumberAndKorean() {
            Assertions.assertThrows(NamedParametersNotSupportedException.class, () -> {
                customExtensionService.create("이1렇2게도");
            });
        }

        @Test
        @DisplayName("error : 영어 + 한글 or 숫자 Naming 입력")
        public void nameEnglishAndNumberOrKorean() {
            Assertions.assertThrows(NamedParametersNotSupportedException.class, () -> {
                customExtensionService.create("1234good");
            });
            Assertions.assertThrows(NamedParametersNotSupportedException.class, () -> {
                customExtensionService.create("밍good");
            });
        }

        @Test
        @DisplayName("error : 200개 초과 저장")
        public void excessDataError() {
            List<CustomExtension> customExtensions = new ArrayList<>();
            for (Long i = 0L; i < 200L; i++) {
                customExtensions.add(CustomExtension.builder().name("aa" + i).build());
            }
            given(customExtensionService.findAll()).willReturn(customExtensions);

            Assertions.assertThrows(TooManyResultsException.class, () -> {
                customExtensionService.create("azs");
            });
        }

        @Test
        @DisplayName("error : 중복된 이름을 저장")
        public void duplicateSaveError() {
            CustomExtension customExtension = CustomExtension.builder()
                    .seq(1L)
                    .name("aza")
                    .build();

            given(customExtensionRepository.findByName("aza"))
                    .willReturn(java.util.Optional.ofNullable(customExtension));

            Assertions.assertThrows(DuplicateNameException.class, () -> {
                customExtensionService.create("aza");
            });
        }

        @Nested
        @DisplayName("고정확장자명과 동일하면 에러를 반환한다.")
        public class fixedSameDataError {

            @Test
            @DisplayName("bat")
            public void bat() {
                Assertions.assertThrows(FixedSameDataException.class, () -> {
                    customExtensionService.create("bat");
                });
            }

            @Test
            @DisplayName("com")
            public void com() {
                Assertions.assertThrows(FixedSameDataException.class, () -> {
                    customExtensionService.create("com");
                });
            }

            @Test
            @DisplayName("cmd")
            public void cmd() {
                Assertions.assertThrows(FixedSameDataException.class, () -> {
                    customExtensionService.create("cmd");
                });
            }

            @Test
            @DisplayName("cpl")
            public void cpl() {
                Assertions.assertThrows(FixedSameDataException.class, () -> {
                    customExtensionService.create("cpl");
                });
            }

            @Test
            @DisplayName("exe")
            public void exe() {
                Assertions.assertThrows(FixedSameDataException.class, () -> {
                    customExtensionService.create("exe");
                });
            }

            @Test
            @DisplayName("scr")
            public void scr() {
                Assertions.assertThrows(FixedSameDataException.class, () -> {
                    customExtensionService.create("scr");
                });
            }

            @Test
            @DisplayName("js")
            public void js() {
                Assertions.assertThrows(FixedSameDataException.class, () -> {
                    customExtensionService.create("js");
                });
            }

        }

    }


    @Test
    @DisplayName("Repository.findAll 실행시켜 모든 정보를 반환한다.")
    public void findAll() {

        List<CustomExtension> resources = new ArrayList<>();
        resources.add(CustomExtension.builder().seq(1L).name("aa").build());
        resources.add(CustomExtension.builder().seq(2L).name("bb").build());
        resources.add(CustomExtension.builder().seq(3L).name("cc").build());
        resources.add(CustomExtension.builder().seq(4L).name("dd").build());

        given(customExtensionRepository.findAll()).willReturn(resources);

        List<CustomExtension> customExtensions = customExtensionService.findAll();
        Assertions.assertFalse(customExtensions.isEmpty());

        int index = 2;
        CustomExtension customExtension = customExtensions.get(index);

        Assertions.assertEquals(customExtension.getSeq(), index + 1);
        Assertions.assertEquals(customExtension.getName(), resources.get(index).getName());
    }


    @Test
    @DisplayName("Request.delete를 실행시켜 데이터를 지운다.")
    public void delete() {
        doNothing().when(customExtensionRepository).deleteBySeq(1L);
        customExtensionService.delete(1L);
        verify(customExtensionRepository).deleteBySeq(1L);
    }

}