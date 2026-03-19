package com.lms.domain.quiz.dto;

import java.time.LocalDateTime;

public class QuizDTO {

    private int quizId;
    private String title;
    private int mafiaId;
    private String content;
    private String answer;
    private LocalDateTime createdAt;


    public QuizDTO(int quizId, String title, int mafiaId, String content, String answer, LocalDateTime createdAt) {
        this.quizId = quizId;
        this.title = title;
        this.mafiaId = mafiaId;
        this.content = content;
        this.answer = answer;
        this.createdAt = createdAt;
    }

    public QuizDTO(String content, String title, int quizId) {
        this.content = content;
        this.mafiaId = mafiaId;
        this.title = title;
        this.quizId = quizId;
    }

    public int getQuizId() {
        return quizId;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getMafiaId() {
        return mafiaId;
    }

    public void setMafiaId(int mafiaId) {
        this.mafiaId = mafiaId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "QuizDTO{" +
                "quizId=" + quizId +
                ", title='" + title + '\'' +
                ", mafiaId=" + mafiaId +
                ", content='" + content + '\'' +
                ", answer='" + answer + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
