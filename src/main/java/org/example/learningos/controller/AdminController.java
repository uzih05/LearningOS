package org.example.learningos.controller;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final ChapterService chapterService;
    private final SubchapterService subchapterService;
    private final QuestionService questionService;

    @Autowired
    public AdminController(ChapterService chapterService,
                           SubchapterService subchapterService,
                           QuestionService questionService) {
        this.chapterService = chapterService;
        this.subchapterService = subchapterService;
        this.questionService = questionService;
    }

    // The /add mapping is now handled by IntegratedAdminController
    // Original /add method removed to resolve the conflict

    // 관리자 대시보드
    @GetMapping("/list")
    public String listContent(Model model) {
        model.addAttribute("chapters", chapterService.getAllChapters());
        return "admin/list"; // templates 폴더 내의 admin/list.html을 렌더링
    }

    // 챕터 편집 페이지
    @GetMapping("/edit/chapter/{id}")
    public String editChapter(@PathVariable Long id, Model model) {
        chapterService.getChapterById(id).ifPresent(chapter -> model.addAttribute("chapter", chapter));
        return "admin/edit-chapter";
    }

    // 서브챕터 편집 페이지
    @GetMapping("/edit/subchapter/{id}")
    public String editSubchapter(@PathVariable Long id, Model model) {
        subchapterService.getSubchapterById(id).ifPresent(subchapter -> {
            model.addAttribute("subchapter", subchapter);
            model.addAttribute("chapters", chapterService.getAllChapters());
        });
        return "admin/edit-subchapter";
    }

    // 질문 편집 페이지
    @GetMapping("/edit/question/{id}")
    public String editQuestion(@PathVariable Long id, Model model) {
        questionService.getQuestionById(id).ifPresent(question -> {
            model.addAttribute("question", question);
            model.addAttribute("subchapters", subchapterService.getAllSubchapters());
        });
        return "admin/edit-question";
    }

    /**
     * 챕터 수정 처리
     */
    @PostMapping("/edit/chapter/{id}")
    public String updateChapter(@PathVariable Long id, @ModelAttribute Chapter chapter, RedirectAttributes redirectAttributes) {
        chapter.setId(id);
        chapterService.saveChapter(chapter);
        redirectAttributes.addFlashAttribute("message", "챕터가 성공적으로 수정되었습니다.");
        return "redirect:/admin/list";
    }

    /**
     * 서브챕터 수정 처리
     */
    @PostMapping("/edit/subchapter/{id}")
    public String updateSubchapter(@PathVariable Long id, @ModelAttribute Subchapter subchapter, RedirectAttributes redirectAttributes) {
        // Ensure the chapter object is properly loaded before saving
        chapterService.getChapterById(subchapter.getChapter().getId()).ifPresent(subchapter::setChapter);

        subchapter.setId(id);
        subchapterService.saveSubchapter(subchapter);
        redirectAttributes.addFlashAttribute("message", "서브챕터가 성공적으로 수정되었습니다.");
        return "redirect:/admin/list";
    }

    /**
     * 질문 수정 처리
     */
    @PostMapping("/edit/question/{id}")
    public String updateQuestion(@PathVariable Long id, @ModelAttribute Question question, RedirectAttributes redirectAttributes) {
        // Ensure the subchapter object is properly loaded before saving
        subchapterService.getSubchapterById(question.getSubchapter().getId()).ifPresent(question::setSubchapter);

        question.setId(id);
        questionService.saveQuestion(question);
        redirectAttributes.addFlashAttribute("message", "질문이 성공적으로 수정되었습니다.");
        return "redirect:/admin/list";
    }

    /**
     * 챕터 삭제 처리
     */
    @DeleteMapping("/delete/chapter/{id}")
    @ResponseBody
    public ResponseEntity<?> deleteChapter(@PathVariable Long id) {
        try {
            chapterService.deleteChapter(id);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "챕터가 성공적으로 삭제되었습니다.");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    /**
     * 서브챕터 삭제 처리
     */
    @DeleteMapping("/delete/subchapter/{id}")
    @ResponseBody
    public ResponseEntity<?> deleteSubchapter(@PathVariable Long id) {
        try {
            subchapterService.deleteSubchapter(id);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "서브챕터가 성공적으로 삭제되었습니다.");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    /**
     * 질문 삭제 처리
     */
    @DeleteMapping("/delete/question/{id}")
    @ResponseBody
    public ResponseEntity<?> deleteQuestion(@PathVariable Long id) {
        try {
            questionService.deleteQuestion(id);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "질문이 성공적으로 삭제되었습니다.");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}