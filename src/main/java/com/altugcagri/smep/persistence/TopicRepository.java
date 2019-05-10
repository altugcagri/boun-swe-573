package com.altugcagri.smep.persistence;

import com.altugcagri.smep.persistence.model.Topic;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {

    Optional<Topic> findById(Long topicId);

    List<Topic> findByCreatedBy(Long userId);

    long countByCreatedBy(Long userId);

    void deleteById(Long topicId);

    List<Topic> findByIdIn(List<Long> topicIds);

    List<Topic> findByIdIn(List<Long> topicIds, Sort sort);
}
