package com.altugcagri.smep.persistence;

import com.altugcagri.smep.persistence.model.Choice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChoiceRepository extends JpaRepository<Choice, Long> {

}
