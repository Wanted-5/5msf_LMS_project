package com.lms.domain.learning.controller;

import com.lms.domain.learning.dto.LearningDTO;
import com.lms.domain.learning.dto.LearningStatus;
import com.lms.domain.learning.dto.reseponse.LearningSectionResponse;
import com.lms.domain.learning.service.LearningService;

import java.util.List;

public class LearningController {

    private final LearningService learningService;

    public LearningController(LearningService learningService) {
        this.learningService = learningService;
    }


    // 수강 기록 전체 조회
    public List<LearningDTO> getAllLearningHistory(long userId, long villageId) {
        return learningService.findByUserIdAndVillageId(userId, villageId);
    }

    public List<LearningDTO> findByUserIdAndVillageIdAndStatus(long userId, long villageId, LearningStatus status) {
        return learningService.findByUserIdAndVillageIdAndStatus(userId, villageId, status);
    }

    // 초기 수강 전으로 입력하기 기능
    public void insertIntoBeforeLearning(long sectionId, long villageId) {
        learningService.insertIntoBeforeLearning(sectionId, villageId);
    }

    // 초기 수강 전으로 입력하기 기능
    public void updateStatusInProgress(long userId, long sectionId) {
        learningService.updateStatusInProgress(userId, sectionId);
    }

    // 초기 수강 전으로 입력하기 기능
    public void updateStatusBeforeCompleted(long userId, long sectionId) {
        learningService.updateStatusCompleted(userId, sectionId);
    }

    public List<LearningSectionResponse> findLearningDetailsByStatus(long userId, long villageId, LearningStatus status) throws Exception{
        return learningService.findLearningDetailsByStatus(userId, villageId, status);
    }
}
