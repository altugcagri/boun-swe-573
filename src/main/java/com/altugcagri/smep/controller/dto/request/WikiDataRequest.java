package com.altugcagri.smep.controller.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WikiDataRequest {

    @NotBlank
    private String id;
    @NotBlank
    private String label;
    private String description;
    private String conceptUri;

}
