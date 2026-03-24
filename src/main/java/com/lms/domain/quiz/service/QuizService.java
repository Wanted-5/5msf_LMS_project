package com.lms.domain.quiz.service;

import com.lms.domain.mafia.dao.MafiaDAO;
import com.lms.domain.quiz.dao.QuizDAO;
import com.lms.domain.quiz.dto.QuizDTO;

import com.lms.domain.users.dto.UserRole;
import com.lms.global.common.UserSession;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class QuizService {

    private final QuizDAO quizDAO;
    private final MafiaDAO mafiaDAO;
    private final Connection connection;

    public QuizService(Connection connection) {
        this.quizDAO = new QuizDAO(connection);
        this.mafiaDAO = new MafiaDAO(connection);
        this.connection = connection;
    }

    public List<QuizDTO> findAllQuiz(long villageId) {

        try {
            return quizDAO.findAllByVillageId(villageId);
        } catch (SQLException e) {
            throw new RuntimeException("퀴즈 전체 조회 중 Error 발생!!" + e);
        }

    }

    public QuizDTO findByQuizId(long quizId, long villageId) {

        try {
            return quizDAO.findByQuizId(quizId, villageId);
        } catch (SQLException e) {
            throw new RuntimeException("퀴즈 상세 조회 중 오류 발생!!" + e);
        }

    }

    // v 추가 완
    public Long createQuiz(QuizDTO newQuiz) {
        try {
            Long userId = UserSession.getLoggedInUser().getUserId();
            UserRole role = UserSession.getLoggedInUser().getRole();

            if (role == UserRole.INSTRUCTOR || role == UserRole.ADMIN) {
                // 강사나 관리자는 mafia_id 없이 등록
                newQuiz.setMafiaId(null);
            } else {
                // 학생은 오늘 마피아인지 체크
                Long mafiaId = mafiaDAO.selectTodayMafiaId(userId, newQuiz.getVillageId());
                newQuiz.setMafiaId(mafiaId);
            }
            newQuiz.setUserId(userId);

            Long nextId = quizDAO.selectNextQuizId();
            newQuiz.setQuizId(nextId);
            return quizDAO.create(newQuiz);
        } catch (SQLException e) {
            throw new RuntimeException("퀴즈 등록 중 Error 발생 !!!" + e.getMessage());
        }
    }

    // v 추가 완
    public int deleteQuiz(Long quizId, long villageId) {
        try {
            // 로그인 여부 체크
            if (!UserSession.isLoggedIn()) {
                throw new RuntimeException("로그인이 필요합니다.");
            }

            Long userId = UserSession.getLoggedInUser().getUserId();
            UserRole role = UserSession.getLoggedInUser().getRole();

            if (role == UserRole.INSTRUCTOR || role == UserRole.ADMIN) {
                int result = quizDAO.deleteByInstructorAndAdmin(quizId, villageId);
                return result;
            } else {
                int result = quizDAO.deleteByMafia(quizId, userId, villageId);
                return result;
            }
        } catch (SQLException e) {
            throw new RuntimeException("퀴즈 삭제 실패 : " + e.getMessage());
        }
    }

    public int updateQuiz(long quizId, String title, String content, String answer, long villageId) {
        try {
            if (!UserSession.isLoggedIn()) {
                throw new RuntimeException("로그인이 필요합니다.");
            }

            Long userId = UserSession.getLoggedInUser().getUserId();
            UserRole role = UserSession.getLoggedInUser().getRole();

            if (role == UserRole.INSTRUCTOR || role == UserRole.ADMIN) {
                int result = quizDAO.updateQuizByInstructorAndAdmin(quizId, title, content, answer, villageId);
                return result;
            } else {
                int result = quizDAO.updateQuizByMafia(quizId, title, content, answer, userId, villageId);
                return result;
            }
        } catch (SQLException e) {
            throw new RuntimeException("퀴즈 수정 실패 : " + e.getMessage());
        }
    }

    public QuizDTO selectTodayQuiz(long villageId) {
        try {
            QuizDTO quiz = quizDAO.selectTodayQuiz(villageId);
            if (quiz == null) {
                throw new RuntimeException("오늘의 퀴즈가 없습니다.");
            }
            return quiz;
        } catch (SQLException e) {
            throw new RuntimeException("퀴즈 조회 실패 : " + e.getMessage());
        }
    }

}
