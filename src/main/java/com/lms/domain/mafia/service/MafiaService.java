package com.lms.domain.mafia.service;

import com.lms.domain.mafia.dao.MafiaDAO;
import com.lms.domain.mafia.dto.MafiaDTO;
import com.lms.domain.mafia.dto.Response.SelectMafiaResponse;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

public class MafiaService {

    private final MafiaDAO mafiaDAO;
    private final Connection connection;

    public MafiaService(Connection connection) {
        this.mafiaDAO = new MafiaDAO(connection);
        this.connection = connection;
    }

    // 마을 별 마피아 선정
    public void selectVillageAll() {
        try {
            List<Integer> villageIds = mafiaDAO.selectAllVillageId();

            if(villageIds.isEmpty()) {
                System.out.println("[알림] 마을을 찾을 수 없습니다");
                return;
            }

            for(int vid : villageIds) {

                try {
                    this.selectMafia(vid);

                    System.out.println("[자동화]" + vid + "번 마을 마피아 선정 완료");
                } catch (RuntimeException e) {
                    System.out.println("[주의]" + vid + "번 마을 건너뜀" + e.getMessage());
                }
            }
            System.out.println("모든 마을 오늘의 마피아 선정 완료!!!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // 마피아 뽑기
    public MafiaDTO selectMafia(long villageId) {


        try {

            List<MafiaDTO> list = mafiaDAO.selectMafia(villageId);
            // 더 뽑을 학생이 없으면 초기화
            if(list == null || list.isEmpty()) {
                mafiaDAO.deleteMafiaHistory(villageId);
                list = mafiaDAO.selectMafia(villageId);
                // 해당 마을에 마피아 없을 때 예외 처리
                if (list == null || list.isEmpty()) {
                    throw new RuntimeException("해당 마을(" + villageId + ")에 활동 중인 학생이 한 명도 없습니다.");
                }
            }

            // 목록에서 랜덤으로 1명 뽑기
            Random random = new Random();
            int randomIndex = random.nextInt(list.size());
            MafiaDTO selectedMafia = list.get(randomIndex);
            // 다음 mafia_id 순번 계산 후 DTO에 세팅
            long nextId = mafiaDAO.selectNextMafiaId();  // 다음 순번 조회
            selectedMafia.setMafiaId(nextId);           // DTO에 세팅
            // 오늘 생성된 마피아 아이디
            selectedMafia.setCreatedAt(LocalDate.now());
            // DB에 넣겠디
            mafiaDAO.insertMafiaHistory(selectedMafia);

            return selectedMafia;

        } catch (SQLException e) {
            throw new RuntimeException("마피아 정보를 불러오는 데 실패했습니다. 시스템 관리자에게 문의하세요" + e);
        }

    }

    // 오늘 날짜에 해당하는 마피아 아이디 검증
    public Long selectTodayMafiaId(long userId, long villageId) {
        try {
            return mafiaDAO.selectTodayMafiaId(userId, villageId);
        } catch (SQLException e) {
            throw new RuntimeException("마피아 정보 조회 실패 : " + e.getMessage());
        }
    }


    public SelectMafiaResponse selectTodayMafiaInfo(long villageId) {
        try {
            SelectMafiaResponse mafia = mafiaDAO.selectTodayMafiaInfo(villageId);
            if (mafia == null) {
                throw new RuntimeException("오늘의 마피아가 아직 선정되지 않았습니다.");
            }
            return mafia;
        } catch (SQLException e) {
            throw new RuntimeException("마피아 조회 실패 : " + e.getMessage());
        }
    }
}
