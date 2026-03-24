package com.lms.domain.quiz.dto.Requset;

public class CreateQuizRequest {
    private Long villageId;
    private String title;
    private String content;
    private String answer;

    public CreateQuizRequest(Long villageId, String title, String content, String answer) {
        this.villageId = villageId;
        this.title = title;
        this.content = content;
        this.answer = answer;
    }

    public Long getVillageId() {
        return villageId;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getAnswer() {
        return answer;
    }
}

