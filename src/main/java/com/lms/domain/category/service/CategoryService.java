package com.lms.domain.category.service;

import com.lms.domain.category.dao.CategoryDAO;
import com.lms.domain.category.dto.CategoryDTO;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryService {

    private final CategoryDAO categoryDAO;

    public CategoryService(Connection con) {
        this.categoryDAO = new CategoryDAO(con);
    }

    // 조회는 학생도 가능해서 userRole 체크가 필요 없습니다.
    public List<CategoryDTO> getCategoryList(Long villageId) {
        List<CategoryDTO> list = new ArrayList<>();
        try {
            list = categoryDAO.findAllByVillageId(villageId);
        } catch (SQLException e) {
            System.out.println("카테고리 조회 실패 : " + e.getMessage());
        }
        return list;
    }

    // [추가] 강사, 관리자만 가능
    public boolean insert(CategoryDTO dto, String userRole) {
        if (!isAdminOrINSTRUCTOR(userRole)) return false;

        if (dto.getCategoryName() == null || dto.getCategoryName().trim().isEmpty()) {
            System.out.println("오류: 카테고리 이름을 입력해주세요");
            return false;
        }

        try {
            return categoryDAO.insert(dto) > 0;
        } catch (SQLException e) {
            System.out.println("DB 오류 " + e.getMessage());
            return false;
        }
    }

    // 수정 강사, 관리자만 가능
    public boolean update(CategoryDTO dto, String userRole) {
        if (!isAdminOrINSTRUCTOR(userRole)) return false;

        if (dto.getCategoryName() == null || dto.getCategoryName().trim().isEmpty()) {
            System.out.println("오류: 카테고리 이름을 입력해주세요");
            return false;
        }

        try {
            return categoryDAO.update(dto) > 0;
        } catch (SQLException e) {
            System.out.println("DB 오류 " + e.getMessage());
            return false;
        }
    }

    // 삭제 강사, 관리자만 가능
    public boolean delete(CategoryDTO dto, String userRole) {
        if (!isAdminOrINSTRUCTOR(userRole)) return false;

        try {
            return categoryDAO.delete(dto) > 0;
        } catch (SQLException e) {
            System.out.println("DB 오류 " + e.getMessage());
            return false;
        }
    }

    // [중복 방지용 프라이빗 메서드] 권한 체크를 한 곳에서 관리합니다.
    private boolean isAdminOrINSTRUCTOR(String userRole) {
        if (!"INSTRUCTOR".equals(userRole) && !"ADMIN".equals(userRole)) {
            System.out.println(" 권한 오류: 강사 또는 관리자만 가능합니다.");
            return false;
        }
        return true;
    }
}