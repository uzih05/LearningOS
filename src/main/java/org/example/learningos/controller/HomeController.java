package org.example.learningos.controller;

import org.example.learningos.model.Question;
import org.example.learningos.service.ChapterService;
import org.example.learningos.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    private final ChapterService chapterService;
    private final QuestionService questionService;

    @Autowired
    public HomeController(ChapterService chapterService, QuestionService questionService) {
        this.chapterService = chapterService;
        this.questionService = questionService;
    }

    @GetMapping("/")
    public String index(Model model) {
        // Add chapters to the model
        model.addAttribute("chapters", chapterService.getAllChapters());

        // Add recent questions to the model (limit to 5)
        List<Question> recentQuestions = questionService.getRecentQuestions(5);
        model.addAttribute("recentQuestions", recentQuestions);

        return "index"; // templates 폴더 내의 index.html을 렌더링
    }
}