package com.lms.domain.mafia.dao;

import com.lms.domain.mafia.dto.MafiaDTO;
import com.lms.global.util.QueryUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MafiaDAO {

    private final Connection connection;

    public MafiaDAO(Connection connection) {
        this.connection = connection;
    }

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

    public List<MafiaDTO> selectMafia(int villageId) throws SQLException {

        String query = QueryUtil.getQuery("mafia.selectMafia");

        List<MafiaDTO> mafiaList = new ArrayList<>();

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setInt(1, villageId);

            ResultSet rset = pstmt.executeQuery();
            while(rset.next()) {
                MafiaDTO mafia = new MafiaDTO();
                mafia.setUserId(rset.getInt("user_id"));
                mafia.setVillageId(rset.getInt("village_id"));

                mafiaList.add(mafia);
            }
        }
        return mafiaList;
    }

    // 뽑은 마피아 mafiaHistory 테이블에 저장
    public int insertMafiaHistory(MafiaDTO mafia) throws SQLException {
        String query = QueryUtil.getQuery("mafia.insertmafiaHistory");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, mafia.getMafiaId());  // Java에서 계산한 순번
            pstmt.setInt(2, mafia.getUserId());
            pstmt.setInt(3, mafia.getVillageId());
            pstmt.setDate(4, Date.valueOf(mafia.getCreatedAt()));

            return pstmt.executeUpdate();
        }
    }

    // 모든 학생이 한번 씩 다 마피아 했으면 초기화
    public int deleteMafiaHistory(int villageId) throws SQLException {
        String query = QueryUtil.getQuery("mafia.deleteMafiahistory");
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1,villageId);

            return pstmt.executeUpdate();
        }
    }

    // DB에 mafidId 번호를 순차적으로 넣기 위해
    public int selectNextMafiaId() throws SQLException {
        String query = QueryUtil.getQuery("mafia.selectNextMafiaId");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            ResultSet rset = pstmt.executeQuery();
            // 결과값이 하나라도 있다면 그 숫자를 꺼내서 돌려줘라
            if (rset.next()) {
                return rset.getInt(1);
            }
        }
        //만약 DB에 데이터가 하나도 없어서 대답이 안 오면 1번 부터
        return 1;
    }

    public int selectTodayMafiaId(int userId) throws SQLException {
        String query = QueryUtil.getQuery("mafia.selectTodayMafiaId");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, userId);
            ResultSet rset = pstmt.executeQuery();
            if (rset.next()) {
                return rset.getInt("mafia_id");
            }
            throw new SQLException("오늘의 마피아 정보를 찾을 수 없습니다.");
        }
    }

}
