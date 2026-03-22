package com.lms.domain.quizSubmission.dto;

import java.time.LocalDate;

public class QuizSubDTO {

    private Long submissionId;
    private Long quizId;
    private Long userId;
    private String submitAnswer;
    private Boolean isCorrect;
    private LocalDate createdAt;

    public QuizSubDTO(Long submissionId, Long quizId, Long userId, String submitAnswer, Boolean isCorrect, LocalDate createdAt) {
        this.submissionId = submissionId;
        this.quizId = quizId;
        this.userId = userId;
        this.submitAnswer = submitAnswer;
        this.isCorrect = isCorrect;
        this.createdAt = createdAt;
    }

    public Long getSubmissionId() {
        return submissionId;
    }

    public void setSubmissionId(Long submissionId) {
        this.submissionId = submissionId;
    }

    public Long getQuizId() {
        return quizId;
    }

    public void setQuizId(Long quizId) {
        this.quizId = quizId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getSubmitAnswer() {
        return submitAnswer;
    }

    public void setSubmitAnswer(String submitAnswer) {
        this.submitAnswer = submitAnswer;
    }

    public Boolean getCorrect() {
        return isCorrect;
    }

    public void setCorrect(Boolean correct) {
        isCorrect = correct;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "QuizSubDTO{" +
                "submissionId=" + submissionId +
                ", quizId=" + quizId +
                ", userId=" + userId +
                ", submitAnswer='" + submitAnswer + '\'' +
                ", isCorrect=" + isCorrect +
                ", createdAt=" + createdAt +
                '}';
    }
}
