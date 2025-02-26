package org.example.learningos.repository;

import org.example.learningos.model.Chapter;
import org.example.learningos.model.Subchapter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SubchapterRepository extends JpaRepository<Subchapter, Long> {
    List<Subchapter> findByChapter(Chapter chapter);
}