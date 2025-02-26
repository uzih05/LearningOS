package org.example.learningos.repository;

import org.example.learningos.model.Question;
import org.example.learningos.model.Subchapter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findBySubchapter(Subchapter subchapter);
}