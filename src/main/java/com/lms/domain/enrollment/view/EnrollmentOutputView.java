package com.lms.domain.enrollment.view;

import java.util.List;
import java.util.Map;

public class EnrollmentOutputView {

    public void printEnrollmentList(List<Map<String, Object>> list) {
        if (list == null || list.isEmpty()) {
            System.out.println("조회된 대상이 없습니다.");
            return;
        }

        for (Map<String, Object> row : list) {
            System.out.println("--------------------------------");
            System.out.println("신청번호(enrollment_id): " + row.get("enrollmentId"));
            System.out.println("학생번호(user_id): " + row.get("userId"));
            System.out.println("학생이름: " + row.get("userName"));
            System.out.println("상태: " + row.get("status"));
            System.out.println("신청일시: " + row.get("appliedAt"));
        }
        System.out.println("--------------------------------");
    }
}