package com.altugcagri.smep.persistence;

import com.altugcagri.smep.persistence.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public  interface QuestionRepository extends JpaRepository<Question, Long> {

    Optional<Question> findById(Long questionId);

    void deleteQuestionById(Long questionId);
}
