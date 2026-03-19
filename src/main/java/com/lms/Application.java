package com.lms;

import com.lms.global.common.AppContext;
import com.lms.global.config.JDBCTemplate;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Application {
    public static void main(String[] args) {

        try (Connection con = JDBCTemplate.getConnection()) {

            System.out.println("✅ 데이터베이스 연결 성공!!!");
            JDBCTemplate.printConnectionStatus();

            Scanner sc = new Scanner(System.in);
            System.out.print("이동할 게시글 번호를 입력하세요: ");
            long boardId = Long.parseLong(sc.nextLine());

            AppContext appContext = new AppContext();
            appContext.commentAppContext(con, boardId);



        } catch (SQLException e) {
            System.err.println("🚨 데이터 베이스 연결 실패... 🚨");
        } finally {
            JDBCTemplate.cloase();
            System.out.println("🔚 데이테 베이스 종료...");
        }



    }
}
