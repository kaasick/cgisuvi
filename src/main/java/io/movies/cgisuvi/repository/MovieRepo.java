package io.movies.cgisuvi.repository;

import io.movies.cgisuvi.models.CatalogItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepo extends JpaRepository<CatalogItem, Long> {
}

