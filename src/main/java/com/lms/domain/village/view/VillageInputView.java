package com.lms.domain.village.view;

import com.lms.domain.section.view.SectionInputView;
import com.lms.domain.village.dto.VillageDTO;

import java.util.Scanner;

public class VillageInputView {

    private final VillageOutputView outputView;
    private final SectionInputView sectionInputView;
    private final Scanner sc = new Scanner(System.in);

    public VillageInputView(VillageOutputView outputView,
                            SectionInputView sectionInputView) {
        this.outputView = outputView;
        this.sectionInputView = sectionInputView;
    }

    private void enterFixedVillage() {
        VillageDTO village = new VillageDTO();
        village.setVillageId(1L);
        village.setVillageName("1번 마을");

        outputView.printVillageEnterSuccess(village);
        showstudentMainMenu(village);
    }
    public void showstudentMainMenu(VillageDTO village) {
        while (true) {
            System.out.println("\n===> " + village.getVillageName() + " 메인페이지 입장");
            System.out.println("1. 출석 체크");
            System.out.println("2. 교육센터로 이동");
            System.out.println("3. 게시판으로 이동");
            System.out.println("4. 숙소(마이페이지)로 이동");
            System.out.println("5. 마피아 게시판으로 이동");
            System.out.println("6. 로그아웃");
            System.out.print("번호 입력 : ");

            int menu;
            try {

                menu = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("숫자만 입력하세요.");
                continue;
            }

            switch (menu) {
                case 1:
                    System.out.println("출석 체크 기능은 준비 중입니다.");
                    break;
                case 2:
                    sectionInputView.displayStudentSectionMenu(village);
                    break;
                case 3:
                    System.out.println("게시판 기능은 준비 중입니다.");
                    break;
                case 4:
                    System.out.println("마이페이지 기능은 준비 중입니다.");
                    break;
                case 5:
                    System.out.println("마피아 게시판 기능은 준비 중입니다.");
                    break;
                case 6:
                    System.out.println("로그아웃합니다.");
                    return;
                default:
                    System.out.println("잘못된 입력입니다.");
            }
        }
    }
    public void showinstructorMainMenu(VillageDTO village) {
        while (true) {
            System.out.println("\n===> " + village.getVillageName() + " 메인페이지 입장");
            System.out.println("1. 출석 체크 및 관리");
            System.out.println("2. 교육센터로 관리");
            System.out.println("3. 게시판 관리");
            System.out.println("4. 마피아 게시판 관리");
            System.out.println("5. 숙소(마이페이지)로 이동");
            System.out.println("6. 수강생 관리");
            System.out.println("7. 오늘의 마피아 뽑기");
            System.out.println("8. 로그아웃");
            System.out.print("번호 입력 : ");

            int menu;
            try {

                menu = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("숫자만 입력하세요.");
                continue;
            }

            switch (menu) {
                case 1:
                    System.out.println("출석 체크 기능은 준비 중입니다.");
                    break;
                case 2:
                    sectionInputView.displayInstructorSectionMenu(village.getVillageId());
                    break;
                case 3:
                    System.out.println("게시판 기능은 준비 중입니다.");
                    break;
                case 4:
                    System.out.println("마이페이지 기능은 준비 중입니다.");
                    break;
                case 5:
                    System.out.println("마피아 게시판 기능은 준비 중입니다.");
                    break;
                case 6:
                    System.out.println("로그아웃합니다.");
                    return;
                default:
                    System.out.println("잘못된 입력입니다.");
            }
        }
    }
}
