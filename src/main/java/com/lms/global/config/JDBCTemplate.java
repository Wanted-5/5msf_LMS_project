package com.lms.global.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.HikariPoolMXBean;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class JDBCTemplate {


    private static final HikariDataSource datasource;

    // static 블록은 정적 코드 블럭으로서
    // 클래스가 로드 될 때 한번만 실행된다.
    static {
        Properties prop = new Properties();
        //JDBCTemplate.class.getClassLoader() : 클래스의 메모리에 로드하는 역활을 한다.
        // getResourceAsStream("db-info.properties") : "db-info.properties" 파일을 스트림으로 가져오는 역활
        try {
            prop.load(JDBCTemplate.class.getClassLoader().getResourceAsStream("ofCourse-db.properties"));
            // 관련된 환경설정 시작

            // HikariDataSource 를 구성 시 필요한 환경세팅 객체
            HikariConfig config = new HikariConfig();

            // db 정보 설정
            config.setJdbcUrl(prop.getProperty("db.url"));
            config.setUsername(prop.getProperty("db.username"));
            config.setPassword(prop.getProperty("db.password"));

            // connection 관련 설정
            config.setMaximumPoolSize(10); // 최대 10개의 커넥션 관리
            config.setMinimumIdle(5); // 최소 5개의 커넥션 유지
            // 커넥션을 사용할 수 있는 최대 시간, 30분 후 새롭게 생성한다.
            config.setMaxLifetime(1800000); // 30분
            config.setIdleTimeout(600000); // 10분 MaxLife보다 짧게
            // 커넥션 연결 요청이 2초 이상 지연되면 연결 실패로 인식한다.
            config.setConnectionTimeout(30000);
            // 구성한 환경 설정을 바탕으로 datasource 객체 생성
            datasource = new HikariDataSource(config);


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // 커넥션 풀에서 연결 된 객체를 꺼내오는 메서드
    public static Connection getConnection() throws SQLException {
        return datasource.getConnection();
    }

    // 전체 커넥션 풀으 종료하는 메서드
    public static void cloase() {
        if (datasource != null) {
            datasource.close();
        }
    }

    // 옵션 : 커넥션 풀 상태 확인 메서드
    public static void  printConnectionStatus() {
        HikariPoolMXBean poolMXBean = datasource.getHikariPoolMXBean();
        System.out.println("[🔎🔎🔎HikariCP 커넥션 풀 상태 확인!!!]");
        System.out.println("🏘️ 총 커넥션 수 : " + poolMXBean.getTotalConnections());
        System.out.println("🏋️ 활성 커넥션 수 : " + poolMXBean.getActiveConnections());
        System.out.println("🧘 유휴(idle) 커넥션 수 : " + poolMXBean.getIdleConnections());
        System.out.println("=============================================");
    }


    public static void close(ResultSet rs) {
        try {
            if (rs != null && !rs.isClosed()) {
                rs.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void close(PreparedStatement pstmt) {
        try {
            if (pstmt != null && !pstmt.isClosed()) {
                pstmt.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void close(Statement stmt) {
        try {
            if (stmt != null && !stmt.isClosed()) {
                stmt.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void close(Connection con) {
        try {
            if (con != null && !con.isClosed()) {
                con.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
