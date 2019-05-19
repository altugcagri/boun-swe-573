package com.altugcagri.smep.controller.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContentResponse {

    private Long id;
    private Long nextContentId;
    private Long questionCount;
    private String title;
    private String topicTitle;
    private String text;
    private Long topicId;

}
