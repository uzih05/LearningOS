package org.example.learningos.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Chapter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int chapterNumber; // 1부터 9까지

    private String title;

    private String description;

    @OneToMany(mappedBy = "chapter", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Subchapter> subchapters;

    // 기본 생성자
    public Chapter() {
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getChapterNumber() {
        return chapterNumber;
    }

    public void setChapterNumber(int chapterNumber) {
        this.chapterNumber = chapterNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Subchapter> getSubchapters() {
        return subchapters;
    }

    public void setSubchapters(List<Subchapter> subchapters) {
        this.subchapters = subchapters;
    }
}