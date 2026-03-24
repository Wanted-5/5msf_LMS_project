package com.lms.domain.learning.service;

import com.lms.domain.learning.dao.LearningDAO;
import com.lms.domain.learning.dto.LearningDTO;
import com.lms.domain.learning.dto.LearningStatus;
import com.lms.domain.learning.dto.reseponse.LearningSectionResponse;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class LearningService {

    private final LearningDAO learningDAO;
    private final Connection connection;

    public LearningService(Connection connection) {
        this.learningDAO = new LearningDAO(connection);
        this.connection = connection;
    }

    // 특정 유저의 해당 마을 강의 전체 조회
    public List<LearningDTO> findByUserIdAndVillageId(long userId, long villageId) {
        try {
            return learningDAO.findByUserIdAndVillageId(userId, villageId);
        } catch (SQLException e) {
            throw new RuntimeException("[DB error]  강의기록 목록 조회에 실패했습니다.", e);
        }
    }

    // 수강 상태별 조회
    public List<LearningDTO> findByUserIdAndVillageIdAndStatus(long userId, long villageId, LearningStatus status) {
        try {
            return learningDAO.findByUserIdAndVillageIdAndStatus(userId, villageId, status);
        } catch (SQLException e) {
            throw new RuntimeException("[DB error]  강의기록 목록 조회에 실패했습니다.", e);
        }
    }

    // 수강전으로 전체 등록하기
    public int insertIntoBeforeLearning(long sectionId, long villageId) {
        try {
           return learningDAO.insertIntoBeforeLearning(sectionId, villageId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //  수강중으로 변경
    public void updateStatusInProgress(long userId, long sectionId) {
        try {
            learningDAO.updateStatusInProgress(userId, sectionId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // 수강완료로 변경
    public void updateStatusCompleted(long userId, long sectionId) {
        try {
            learningDAO.updateStatusCompleted(userId, sectionId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<LearningSectionResponse> findLearningDetailsByStatus(long userId, long villageId, LearningStatus status) {
        try {
            return learningDAO.findLearningDetailsByStatus(userId, villageId, status);
        } catch (SQLException e) {
            throw new RuntimeException("[error] 수강 상태별 목록을 조회하는 중 DB 오류가 발생했습니다.", e);
        }
    }


    public void insertExistingSectionsForNewStudent(long userId, long villageId) {
        try {
            int insertedCount = learningDAO.insertExistingSectionsForNewStudent(userId, villageId);
        } catch (SQLException e) {
            throw new RuntimeException("[Service Error] 신규 수강생에게 기존 강의를 할당하는 중 DB 오류가 발생했습니다.", e);
        }
    }
}
