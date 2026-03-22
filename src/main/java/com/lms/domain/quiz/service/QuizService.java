package com.lms.domain.quiz.service;

import com.lms.domain.mafia.dao.MafiaDAO;
import com.lms.domain.quiz.dao.QuizDAO;
import com.lms.domain.quiz.dto.QuizDTO;
import com.lms.domain.users.constant.UserRole;
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

    public List<QuizDTO> findAllQuiz() {

        try {
            return quizDAO.findAll();
        } catch (SQLException e) {
            throw new RuntimeException("퀴즈 전체 조회 중 Error 발생!!" + e);
        }

    }

    public QuizDTO findByQuizId(long id) {

        try {
            return quizDAO.findByQuizId(id);
        } catch (SQLException e) {
            throw new RuntimeException("강좌 상세 조회 중 오류 발생!!" + e);
        }

    }

    public Long createQuiz(QuizDTO newQuiz) {

        try {
            // 현재 로그인 한 유저의 아이디 가져오기
            int userId = UserSession.getLoggedInUser().getUserId().intValue();
            // 오늘 마피아인지 확인하고 가져오기
            Long mafiaId = mafiaDAO.selectTodayMafiaId(userId);
            newQuiz.setMafiaId(mafiaId);

            Long nextId = quizDAO.selectNextQuizId();  // 추가
            newQuiz.setQuizId(nextId);
            return quizDAO.create(newQuiz);
        } catch (SQLException e) {
            throw new RuntimeException("퀴즈 등록 중 Error 발생 !!!" + e.getMessage());
        }

    }

    public Long deleteQuiz(long quizId) {
        try {
            // 로그인 여부 체크
            if (!UserSession.isLoggedIn()) {
                throw new RuntimeException("로그인이 필요합니다.");
            }

            Long userId = UserSession.getLoggedInUser().getUserId();
            UserRole role = UserSession.getLoggedInUser().getRole();

            if (role == UserRole.INSTRUCTOR) {
                quizDAO.deleteByInstructor(quizId);
            } else if(role == UserRole.ADMIN){
                quizDAO.deleteByAdmin(quizId);
            }else {
                Long result = quizDAO.deleteByMafia(quizId, userId.intValue());

                if (result == 0) {
                    throw new RuntimeException("본인이 작성한 퀴즈만 삭제할 수 있습니다.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("퀴즈 삭제 실패 : " + e.getMessage());
        }
        return 0L;
    }

    public Long updateQuiz(long quizId, String title, String content, String answer) {
        try {
            if (!UserSession.isLoggedIn()) {
                throw new RuntimeException("로그인이 필요합니다.");
            }

            Long userId = UserSession.getLoggedInUser().getUserId();
            UserRole role = UserSession.getLoggedInUser().getRole();

            if (role == UserRole.INSTRUCTOR) {
                quizDAO.updateQuizByInstructor(quizId, title, content, answer);
            } else if(role == UserRole.ADMIN) {
                quizDAO.updateQuizByAdmin(quizId, title, content, answer);
            } else {
                Long result = quizDAO.updateQuizByMafia(quizId, title, content, answer, userId);

                if (result == 0) {
                    throw new RuntimeException("본인이 작성한 퀴즈만 수정할 수 있습니다.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("퀴즈 수정 실패 : " + e.getMessage());
        }
        return 0L;
    }
}
