package com.altugcagri.smep.controller.dto.response;

import com.altugcagri.smep.persistence.model.Content;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TopicResponse {

    private Long id;
    private String title;
    private String description;
    private UserSummary createdBy;
    private Instant creationDateTime;
    private ArrayList<String> wikiData;
    private List<Content> contentList;

}
