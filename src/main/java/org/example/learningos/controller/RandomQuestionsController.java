package org.example.learningos.controller;

import org.example.learningos.model.Question;
import org.example.learningos.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class RandomQuestionsController {

    private final QuestionService questionService;

    @Autowired
    public RandomQuestionsController(QuestionService questionService) {
        this.questionService = questionService;
    }

    /**
     * Shows random questions
     * @param count Number of random questions to show (default: 5)
     * @param model Spring model
     * @return The random_questions template
     */
    @GetMapping("/random-questions")
    public String showRandomQuestions(@RequestParam(defaultValue = "5") int count, Model model) {
        List<Question> randomQuestions = questionService.getRandomQuestions(count);
        model.addAttribute("questions", randomQuestions);
        model.addAttribute("count", count);
        return "random_questions";
    }
}