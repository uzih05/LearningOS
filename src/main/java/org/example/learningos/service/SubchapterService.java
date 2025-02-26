package org.example.learningos.service;

import org.example.learningos.model.Chapter;
import org.example.learningos.model.Subchapter;
import org.example.learningos.repository.SubchapterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class SubchapterService {

    private final SubchapterRepository subchapterRepository;

    @Autowired
    public SubchapterService(SubchapterRepository subchapterRepository) {
        this.subchapterRepository = subchapterRepository;
    }

    public List<Subchapter> getAllSubchapters() {
        return subchapterRepository.findAll();
    }

    public List<Subchapter> getSubchaptersByChapter(Chapter chapter) {
        return subchapterRepository.findByChapter(chapter);
    }

    public Optional<Subchapter> getSubchapterById(Long id) {
        return subchapterRepository.findById(id);
    }

    public Subchapter saveSubchapter(Subchapter subchapter) {
        return subchapterRepository.save(subchapter);
    }

    public void deleteSubchapter(Long id) {
        subchapterRepository.deleteById(id);
    }
}