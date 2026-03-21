package com.lms.domain.attendance.dao;

import com.lms.domain.attendance.dto.AttendDTO;
import com.lms.domain.attendance.dto.AttendStatus;
import com.lms.global.util.QueryUtil;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AttendDAO {
    private final Connection connection;

    public AttendDAO(Connection connection) {this.connection = connection;}

    //출결 등록
    public int attendInsert(AttendDTO dto) throws SQLException {

        String query = QueryUtil.getQuery("insert_Attend");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setLong(1, dto.getAttendanceId());
            pstmt.setLong(2, dto.getVillageId());
            pstmt.setLong(3, dto.getUserId());
            pstmt.setString(4, dto.getStatus().toString());

            return pstmt.executeUpdate();
        }

    }
    //유저 개인 조회
    public List<AttendDTO> findAttendAll(long userId) throws SQLException {

        String query = QueryUtil.getQuery("select_Attend");
        List<AttendDTO> attendList = new ArrayList<>();

        try (PreparedStatement pstmt = connection.prepareStatement(query)){

            pstmt.setLong(1, userId);
            ResultSet rset = pstmt.executeQuery();

            while(rset.next()){
                attendList.add(new AttendDTO(
                        rset.getLong("attendance_id"),
                        rset.getLong("village_id"),
                        rset.getLong("user_id"),
                        AttendStatus.valueOf(rset.getString("status")),
                        rset.getTimestamp("created_at").toLocalDateTime(),
                        rset.getTimestamp("attendance_date").toLocalDateTime()
                ));
            }
        }
            return attendList;
    }
    //관리자 전체 조회
    public List<AttendDTO> findAttendAllForAdmin() throws SQLException {
        String query = QueryUtil.getQuery("select_AttendAll");
        List<AttendDTO> attendList = new ArrayList<>();

        try (PreparedStatement pstmt = connection.prepareStatement(query);
             ResultSet rset = pstmt.executeQuery()) {

            while(rset.next()){
                attendList.add(new AttendDTO(
                        rset.getLong("attendance_id"),
                        rset.getLong("village_id"),
                        rset.getLong("user_id"),
                        AttendStatus.valueOf(rset.getString("status")),
                        rset.getTimestamp("created_at").toLocalDateTime(),
                        rset.getTimestamp("attendance_date").toLocalDateTime()
                ));
            }
        }
        return attendList;
    }

    //조회 날짜 입력받았을때 조회
    public List<AttendDTO> findAttendByDate(LocalDate targetDate) throws SQLException {
        String query = QueryUtil.getQuery("select_dateAttend");
        List<AttendDTO> attendList = new ArrayList<>();

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            //datetime은 DB애서 DATE 타입이기 때문에 Date.valueof 사용
            pstmt.setDate(1, Date.valueOf(targetDate));
            //try 두번 쓰는 이유는 두개의 로직이라 그럼 1. ?안의 date설정, 2.?에 해당하는 각 컬럼 조회
            try (ResultSet rset = pstmt.executeQuery()) {
                while(rset.next()){
                    attendList.add(new AttendDTO(
                            rset.getLong("attendance_id"),
                            rset.getLong("village_id"),
                            rset.getLong("user_id"),
                            AttendStatus.valueOf(rset.getString("status")),
                            rset.getTimestamp("created_at").toLocalDateTime(),
                            rset.getTimestamp("attendance_date").toLocalDateTime()
                    ));
                }
            }
        }
        return attendList;
    }

    //출결 수정
    public int updateAttendStatus(long attendanceId, AttendStatus status) throws SQLException {
        String query = QueryUtil.getQuery("update_Attendance");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setString(1, status.name());
            pstmt.setLong(2, attendanceId);

            return pstmt.executeUpdate();
        }
    }
    //출결 삭제
    public int deleteAttendance(long attendanceId) throws SQLException {
        String query = QueryUtil.getQuery("delete_Attendance");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setLong(1, attendanceId);

            return pstmt.executeUpdate();
        }
    }


}

