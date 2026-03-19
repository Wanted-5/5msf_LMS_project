package com.lms;

import com.lms.global.AppContext.AppContext;
import com.lms.global.config.JDBCTemplate;

import java.sql.Connection;
import java.sql.SQLException;

public class Application {
    public static void main(String[] args) {

        try (Connection con = JDBCTemplate.getConnection()) {

            System.out.println("✅ 데이터베이스 연결 성공!!!");
            JDBCTemplate.printConnectionStatus();

            AppContext appContext = new AppContext(con);
            appContext.quizAppContext.quizInputView.displayMainMenu();
//            appContext.userAppContext(con);
           // appContext.quizAppContext(con);






        } catch (SQLException e) {
            System.err.println("🚨 데이터 베이스 연결 실패... 🚨");
        } finally {
            JDBCTemplate.cloase();
            System.out.println("🔚 데이테 베이스 종료...");
        }

    }
}
