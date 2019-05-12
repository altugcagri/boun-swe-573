package com.altugcagri.smep.persistence.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.util.Set;

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
    @NotBlank
    private String label;
    private String description;
    private String conceptUri;

}
