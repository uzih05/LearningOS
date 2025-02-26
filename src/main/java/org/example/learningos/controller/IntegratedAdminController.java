package org.example.learningos.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.learningos.model.Chapter;
import org.example.learningos.model.Question;
import org.example.learningos.model.Subchapter;
import org.example.learningos.service.ChapterService;
import org.example.learningos.service.QuestionService;
import org.example.learningos.service.SubchapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class IntegratedAdminController {

    private final ChapterService chapterService;
    private final SubchapterService subchapterService;
    private final QuestionService questionService;

    @Autowired
    public IntegratedAdminController(
            ChapterService chapterService,
            SubchapterService subchapterService,
            QuestionService questionService) {
        this.chapterService = chapterService;
        this.subchapterService = subchapterService;
        this.questionService = questionService;
    }

    // Show the integrated form page
    @GetMapping("/add")
    public String showAddPage(Model model) {
        model.addAttribute("chapters", chapterService.getAllChapters());
        return "admin/add";
    }

    // API endpoint to handle JSON submission
    @PostMapping("/api/save-content")
    @ResponseBody
    public ResponseEntity<?> saveContent(@RequestBody Map<String, Object> data) {
        try {
            // 1. Extract chapter data
            Map<String, Object> chapterData = (Map<String, Object>) data.get("chapter");

            // 2. Create and save Chapter
            Chapter chapter = new Chapter();
            chapter.setChapterNumber(((Number) chapterData.get("chapterNumber")).intValue());
            chapter.setTitle((String) chapterData.get("title"));
            chapter.setDescription((String) chapterData.get("description"));
            chapter = chapterService.saveChapter(chapter);

            // 3. Process subchapters
            List<Map<String, Object>> subchaptersData =
                    (List<Map<String, Object>>) data.get("subchapters");

            for (Map<String, Object> subData : subchaptersData) {
                // 4. Create and save Subchapter
                Subchapter subchapter = new Subchapter();
                subchapter.setTitle((String) subData.get("title"));
                subchapter.setDescription((String) subData.get("description"));
                subchapter.setChapter(chapter);
                subchapter = subchapterService.saveSubchapter(subchapter);

                // 5. Process questions
                List<Map<String, Object>> questionsData =
                        (List<Map<String, Object>>) subData.get("questions");

                for (Map<String, Object> qData : questionsData) {
                    // 6. Create and save Question
                    Question question = new Question();
                    question.setQuestionText((String) qData.get("questionText"));
                    question.setAnswer((String) qData.get("answer"));
                    question.setExplanation((String) qData.get("explanation"));
                    question.setSubchapter(subchapter);
                    questionService.saveQuestion(question);
                }
            }

            // 7. Return success response
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Content successfully created!");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();
            // 8. Return error response
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}