package com.lms.domain.comment.view;

import com.lms.domain.comment.dto.CommentDTO;

import java.util.List;

public class CommentOutputView {


    public void printSuccess(String message) {

        System.out.println("✅ " + message);
    }

    public void printError(String message) {

        System.out.println("🚨🚨 " + message);
    }

    public void printMessage(String message){
        System.out.println(message);
    }

    public void printComment (List<CommentDTO> commentList) {

        if (commentList == null || commentList.isEmpty()) {
            System.out.println("조회 된 강좌가 없습니다!!");
            return;
        }

        System.out.println("===============강의 전체 조회 목록 결과==================");
        for (CommentDTO commentDTO : commentList) {
            System.out.println(commentDTO);
        }

    }


}
