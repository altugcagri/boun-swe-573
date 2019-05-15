package com.altugcagri.smep.persistence;

import com.altugcagri.smep.persistence.model.WikiData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WikiDataRepository extends JpaRepository<WikiData, String> {

}
