package com.lms;

<<<<<<< HEAD

import com.lms.global.AppContext.AppContext;
=======
import com.lms.global.AppContext.AppContext;
import com.lms.global.AppContext.AppContext;
import com.lms.global.AppContext.CommentAppContext;
import com.lms.global.AppContext.CommentAppContext;
>>>>>>> 7cf81797c4116cfe5161e66038702007c50642c2
import com.lms.global.config.JDBCTemplate;

import java.sql.Connection;

public class Application {
    public static void main(String[] args) {

        try (Connection con = JDBCTemplate.getConnection()) {
            System.out.println("✅ 데이터베이스 연결 성공!!!");
            JDBCTemplate.printConnectionStatus();

            AppContext appContext = new AppContext(con);
<<<<<<< HEAD
            appContext.villageAppContext.run();

        } catch (Exception e) {
            e.printStackTrace();
=======
            appContext.userAppContext.userInputView.displayInitialMenu();
           // appContext.quizAppContext.quizInputView.displayMainMenu();
//            appContext.commentAppContext.commentInputView.showInitialMenu();



        } catch (SQLException e) {
            System.err.println("🚨 데이터 베이스 연결 실패... 🚨");
>>>>>>> 7cf81797c4116cfe5161e66038702007c50642c2
        } finally {
            System.out.println("🔚 데이터베이스 종료...");
        }
    }
}