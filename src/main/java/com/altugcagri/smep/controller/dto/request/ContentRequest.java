package com.altugcagri.smep.controller.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContentRequest {

    @NonNull
    private Long id = 0L;

    @NonNull
    private Long topicId;

    @NotBlank
    private String title;

    @NotBlank
    private String text;
}
