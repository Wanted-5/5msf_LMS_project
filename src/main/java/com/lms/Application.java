package com.lms;

import com.lms.domain.users.constant.UserRole;
import com.lms.domain.users.dto.response.LoginResponse;
import com.lms.global.AppContext.AppContext;
import com.lms.global.AppContext.AppContext;
import com.lms.global.AppContext.AppContext;
import com.lms.global.AppContext.CommentAppContext;
import com.lms.global.AppContext.CommentAppContext;
import com.lms.global.common.UserSession;
import com.lms.global.config.JDBCTemplate;
import java.sql.SQLException;
import java.sql.Connection;

public class Application {
    public static void main(String[] args) {

        try (Connection con = JDBCTemplate.getConnection()) {

            System.out.println("✅ 데이터베이스 연결 성공!!!");
            AppContext appContext = new AppContext(con);

            AppContext.init(con);

            while (true) {
                LoginResponse loggedInUser = UserSession.getLoggedInUser();

                if (loggedInUser == null) {
                    appContext.userAppContext.userInputView.displayInitialMenu();
                }

                else {
                    UserRole role = loggedInUser.getRole();

                    if (role == UserRole.ADMIN) {
                        System.out.println("  [시스템] " + UserSession.getLoggedInUser().getRole().getDescription() + "(ADMIN) 권한으로 접속했습니다.");
                        appContext.cityAppContext.cityInputView.displayCityAdminMenu();
                        //TODO: 관리자는 마을을 선택할 수 있게 로직 구현

                    } else if (role == UserRole.INSTRUCTOR) {
                        System.out.println("  [시스템] " + UserSession.getLoggedInUser().getRole().getDescription() + "(INSTRUCTOR) 권한으로 접속했습니다.");
                        //TODO: 강사 옵션으로 로직 구현

                    } else if (role == UserRole.STUDENT) {
                        System.out.println("  [시스템] " + UserSession.getLoggedInUser().getRole().getDescription() + "(STUDENT) 권한으로 접속했습니다.");
//                        appContext.villageAppContext.villageInputView.showVillageMainMenu();
                        //TODO: 학생 옵션으로 로직 구현

                    }
                }
            }



        } catch (SQLException e) {
            System.err.println("🚨 데이터 베이스 연결 실패... 🚨");
        } finally {
            JDBCTemplate.cloase();
            System.out.println("🔚 데이테 베이스 종료...");
        }

    }
}
