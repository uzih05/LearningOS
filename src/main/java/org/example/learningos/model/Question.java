package org.example.learningos.model;

import jakarta.persistence.*;

@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 1000)
    private String questionText;  // 문제 내용

    // Multiple choice options
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;

    // Correct option (A, B, C, or D)
    private String correctOption;

    @Column(length = 1000)
    private String explanation;   // 해설

    @ManyToOne
    @JoinColumn(name = "subchapter_id")
    private Subchapter subchapter;

    // 기본 생성자
    public Question() {}

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getOptionA() {
        return optionA;
    }

    public void setOptionA(String optionA) {
        this.optionA = optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public void setOptionB(String optionB) {
        this.optionB = optionB;
    }

    public String getOptionC() {
        return optionC;
    }

    public void setOptionC(String optionC) {
        this.optionC = optionC;
    }

    public String getOptionD() {
        return optionD;
    }

    public void setOptionD(String optionD) {
        this.optionD = optionD;
    }

    public String getCorrectOption() {
        return correctOption;
    }

    public void setCorrectOption(String correctOption) {
        this.correctOption = correctOption;
    }

    // For backward compatibility
    public String getAnswer() {
        // Return the text of the correct option
        switch (correctOption) {
            case "A": return optionA;
            case "B": return optionB;
            case "C": return optionC;
            case "D": return optionD;
            default: return "";
        }
    }

    public void setAnswer(String answer) {
        // This is kept for backward compatibility
        // It won't do anything but is needed to prevent errors
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public Subchapter getSubchapter() {
        return subchapter;
    }

    public void setSubchapter(Subchapter subchapter) {
        this.subchapter = subchapter;
    }
}