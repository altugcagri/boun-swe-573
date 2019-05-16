package com.altugcagri.smep.persistence.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "wikidata")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WikiData extends DataBaseEntity {

    @Id
    @NotBlank
    private String id;

    @Lob
    @NotBlank
    private String label;

    @Lob
    private String description;

    @Lob
    private String conceptUri;

}
