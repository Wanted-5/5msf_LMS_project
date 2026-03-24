package com.lms.domain.mafia.dao;

import com.lms.domain.mafia.dto.MafiaDTO;
import com.lms.global.util.QueryUtil;


import javax.management.Query;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MafiaDAO {

    private final Connection connection;

    public MafiaDAO(Connection connection) {
        this.connection = connection;
    }

    // 마을 별
    public List<Integer> selectAllVillageId() throws SQLException {
        String query = QueryUtil.getQuery("mafia.selectAllVillageId");

        List<Integer> villageList = new ArrayList<>();

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            ResultSet rset = pstmt.executeQuery();

            while(rset.next()) {
                villageList.add(rset.getInt("village_id"));
            }
        }
        return villageList;
    }

    // 마피아 뽑기
    public List<MafiaDTO> selectMafia(long villageId) throws SQLException {

        String query = QueryUtil.getQuery("mafia.selectMafia2");

        List<MafiaDTO> mafiaList = new ArrayList<>();

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setLong(1, villageId);

            ResultSet rset = pstmt.executeQuery();
            while(rset.next()) {
                MafiaDTO mafia = new MafiaDTO();
                mafia.setUserId(rset.getLong("user_id"));
                mafia.setVillageId(rset.getLong("village_id"));
                mafia.setName(rset.getString("name"));
                mafia.setNickname(rset.getString("nickname"));

                mafiaList.add(mafia);
            }
        }
        return mafiaList;
    }

    // 뽑은 마피아 mafiaHistory 테이블에 저장
    public int insertMafiaHistory(MafiaDTO mafia) throws SQLException {
        String query = QueryUtil.getQuery("mafia.insertmafiaHistory");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setLong(1, mafia.getMafiaId());  // Java에서 계산한 순번
            pstmt.setLong(2, mafia.getUserId());
            pstmt.setLong(3, mafia.getVillageId());
            pstmt.setDate(4, Date.valueOf(mafia.getCreatedAt()));

            return pstmt.executeUpdate();
        }  catch (SQLException e) {
            if (e.getMessage().contains("Duplicate entry")) {
                throw new RuntimeException("오늘 이미 마피아가 선정된 마을입니다.");
            }
            throw e;
        }
    }

    // 모든 학생이 한번 씩 다 마피아 했으면 초기화
    public int deleteMafiaHistory(long villageId) throws SQLException {
        String query = QueryUtil.getQuery("mafia.deleteMafiahistory");
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setLong(1,villageId);

            return pstmt.executeUpdate();
        }
    }

    // DB에 mafidId 번호를 순차적으로 넣기 위해
    public Long selectNextMafiaId() throws SQLException {
        String query = QueryUtil.getQuery("mafia.selectNextMafiaId");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            ResultSet rset = pstmt.executeQuery();
            // 결과값이 하나라도 있다면 그 숫자를 꺼내서 돌려줘라
            if (rset.next()) {
                return rset.getLong(1);
            }
        }
        //만약 DB에 데이터가 하나도 없어서 대답이 안 오면 1번 부터
        return 1L;
    }

    // 오늘 날짜에 해당하는 마피아 아이디 검증
    public Long selectTodayMafiaId(Long userId) throws SQLException {
        String query = QueryUtil.getQuery("mafia.selectTodayMafiaId");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setLong(1, userId);
            ResultSet rset = pstmt.executeQuery();
            if (rset.next()) {
                return rset.getLong("mafia_id");
            }
            throw new SQLException("오늘의 마피아만 퀴즈를 등록할 수 있습니다.");
        }
    }

}
