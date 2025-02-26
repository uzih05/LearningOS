package org.example.learningos.service;

import org.example.learningos.model.Question;
import org.example.learningos.model.Subchapter;
import org.example.learningos.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    @Autowired
    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public List<Question> getQuestionsBySubchapter(Subchapter subchapter) {
        return questionRepository.findBySubchapter(subchapter);
    }

    public Optional<Question> getQuestionById(Long id) {
        return questionRepository.findById(id);
    }

    public Question saveQuestion(Question question) {
        return questionRepository.save(question);
    }

    public void deleteQuestion(Long id) {
        questionRepository.deleteById(id);
    }

    /**
     * Get most recently added questions
     * @param count Number of questions to retrieve
     * @return List of recent questions
     */
    public List<Question> getRecentQuestions(int count) {
        // Using PageRequest to limit results and sort by ID in descending order
        // assuming higher IDs are more recent additions
        return questionRepository.findAll(
                        PageRequest.of(0, count, Sort.by(Sort.Direction.DESC, "id")))
                .getContent();
    }

    /**
     * Get a specified number of random questions from all available questions
     * @param count Number of questions to retrieve
     * @return List of random questions
     */
    public List<Question> getRandomQuestions(int count) {
        // Get all questions
        List<Question> allQuestions = questionRepository.findAll();

        // If we have fewer questions than requested, return all we have
        if (allQuestions.size() <= count) {
            return allQuestions;
        }

        // Shuffle the list to randomize
        List<Question> shuffled = new ArrayList<>(allQuestions);
        Collections.shuffle(shuffled);

        // Return the requested number of questions
        return shuffled.subList(0, count);
    }
}