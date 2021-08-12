package com.example.exam.persistance;

import com.example.exam.domain.CustomExtension;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import javax.validation.ConstraintViolationException;

@DataJpaTest
class CustomExtensionRepositoryTests {

    @Autowired
    private CustomExtensionRepository customExtensionRepository;

    @Nested
    @DisplayName("주어진 정보를 저장한다.")
    public class create{

        @Test
        @DisplayName("성공")
        public void save() {
            CustomExtension resource = CustomExtension.builder()
                    .name("aa")
                    .build();

            CustomExtension customExtension = customExtensionRepository.save(resource);
            Assertions.assertEquals(customExtension.getName(), resource.getName());
        }

        @Test
        @DisplayName("error : 글자수 초과")
        public void namingLengthLimit() {
            CustomExtension customExtension = CustomExtension.builder()
                    .name("asdfjklghasdfjklsdffdsfdsfsghsdfdsfa")
                    .build();
            Assertions.assertThrows(DataIntegrityViolationException.class, () -> {
                customExtensionRepository.save(customExtension);
            });
        }

        @Test
        @DisplayName("error : 한글 입력")
        public void textNotSupported() {
            CustomExtension customExtension = CustomExtension.builder()
                    .name("ㅁㅁ")
                    .build();
            Assertions.assertThrows(ConstraintViolationException.class, () -> {
                customExtensionRepository.save(customExtension);
            });
        }

        @Test
        @DisplayName("error : 숫자 입력")
        public void numberNotSupported() {
            CustomExtension customExtension = CustomExtension.builder()
                    .name("234324")
                    .build();
            Assertions.assertThrows(ConstraintViolationException.class, () -> {
                customExtensionRepository.save(customExtension);
            });
        }

        @Test
        @DisplayName("error : 특수문자 입력")
        public void SpecialTextNotSupported() {
            CustomExtension customExtension = CustomExtension.builder()
                    .name("._sdf")
                    .build();
            Assertions.assertThrows(ConstraintViolationException.class, () -> {
                customExtensionRepository.save(customExtension);
            });
        }
    }

    @Test
    @DisplayName("성공적으로 주어진 아이디값을 제거한다.")
    public void delete() {
        CustomExtension resource = CustomExtension.builder()
                .seq(1L)
                .name("aa")
                .build();

        customExtensionRepository.save(resource);
        customExtensionRepository.deleteBySeq(1L);

        Assertions.assertFalse(customExtensionRepository.findById(1L).isPresent());
    }
}