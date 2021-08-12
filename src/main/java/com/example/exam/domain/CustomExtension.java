package com.example.exam.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Setter
public class CustomExtension {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    @Column(length = 20)
    @NotBlank(message = "커스텀 확장자명을 입력해주세요!")
    @Pattern(regexp = "^[a-zA-Z]*$")
    private String name;

}
