package com.altugcagri.smep.controller.dto.request;

import com.altugcagri.smep.persistence.model.Content;
import com.altugcagri.smep.persistence.model.User;
import com.altugcagri.smep.persistence.model.WikiData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TopicRequest {


    private Long id;

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotBlank
    private String imageUrl;

    @Nullable
    private Set<WikiData> wikiData;

    @Nullable
    private Set<User> enrolledUsers;

    @Nullable
    private List<Content> contentList;

}
