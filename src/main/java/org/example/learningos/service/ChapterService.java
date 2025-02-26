package org.example.learningos.service;

import org.example.learningos.model.Chapter;
import org.example.learningos.repository.ChapterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ChapterService {

    private final ChapterRepository chapterRepository;

    @Autowired
    public ChapterService(ChapterRepository chapterRepository) {
        this.chapterRepository = chapterRepository;
    }

    public List<Chapter> getAllChapters() {
        return chapterRepository.findAllByOrderByChapterNumberAsc();
    }

    public Optional<Chapter> getChapterById(Long id) {
        return chapterRepository.findById(id);
    }

    public Chapter saveChapter(Chapter chapter) {
        return chapterRepository.save(chapter);
    }

    public void deleteChapter(Long id) {
        chapterRepository.deleteById(id);
    }
}