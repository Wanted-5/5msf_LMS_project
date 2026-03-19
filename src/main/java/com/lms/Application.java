package com.lms;


import com.lms.global.AppContext.AppContext;
import com.lms.global.config.JDBCTemplate;

import java.sql.Connection;

public class Application {
    public static void main(String[] args) {

        try (Connection con = JDBCTemplate.getConnection()) {
            System.out.println("✅ 데이터베이스 연결 성공!!!");
            JDBCTemplate.printConnectionStatus();

            AppContext appContext = new AppContext(con);
            appContext.villageAppContext.run();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("🔚 데이터베이스 종료...");
        }
    }
}