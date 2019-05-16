package com.altugcagri.smep.persistence.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "topics")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Topic extends UserCreatedDataBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @NotBlank
    private String title;

    @Lob
    @NotBlank
    private String description;

    @Lob
    @NotBlank
    private String imageUrl;

    @Nullable
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "topic")
    private List<Content> contentList;

    @Nullable
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(
            name = "topic_wikidata",
            joinColumns = @JoinColumn(name = "topic_id"),
            inverseJoinColumns = @JoinColumn(name = "wikidata_id"))
    private Set<WikiData> wikiDataSet;

    @Nullable
    @ManyToMany
    @JoinTable(name = "enrolled_users",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "topic_id"))
    private Set<User> enrolledUsers;

}


