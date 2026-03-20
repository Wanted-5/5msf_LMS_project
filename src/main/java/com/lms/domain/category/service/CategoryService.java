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
    public List<CategoryDTO> getCategoryList(Long villageId) {
     // 1. [결론] 빈 리스트를 먼저 선언한다 (에러가 나도 null 대신 빈 값을 주기 위해) ?
        List<CategoryDTO> list = new ArrayList<>();

        try {
            list = categoryDAO.findAllByVillageId(villageId);
        } catch (SQLException e) {
            System.out.println("카테고리 조회 실패 : " + e.getMessage());
    }
            return list;

        }
    }

