package com.altugcagri.smep.persistence.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@IdClass(LearningStep.IdClass.class)
@Table(name = "learning_steps")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LearningStep extends UserCreatedDataBaseEntity {

    @Id
    private Long userId;

    @Id
    private Long contentId;

    @Id
    private Long questionId;

    @Id
    private Long answerId;

    @Data
    static class IdClass implements Serializable {
        private Long userId;
        private Long contentId;
        private Long questionId;
        private Long answerId;
    }

}
