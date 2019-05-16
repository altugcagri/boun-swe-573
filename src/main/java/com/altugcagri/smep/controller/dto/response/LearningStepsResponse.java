package com.altugcagri.smep.controller.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LearningStepsResponse {

    private List<QuestionResponse> questions;

    private String contentTitle;

    private Long nextContentId;

    private String topicTitle;

}
