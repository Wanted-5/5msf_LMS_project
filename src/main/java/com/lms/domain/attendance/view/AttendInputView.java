//package com.lms.domain.attendance.view;
//
//import com.lms.domain.attendance.controller.AttendController;
//

//
//public class AttendInputView {
//
//    public final AttendController attendController;
//    public final AttendOutputView attendOutputView;
//
//    public AttendInputView(AttendController attendController, AttendOutputView attendOutputView) {
//        this.attendController = attendController;
//        this.attendOutputView = attendOutputView;
//    }
//
//
//    Scanner sc = new Scanner(System.in);
//
//    public void attendMenu(){
//        System.out.print("출석 체크 메뉴");
//        System.out.print("1. 출석체크 하기");
//        System.out.print("2. 출결확인 하기");
//        System.out.print("3. 이전으로");
//        int no = sc.nextInt();
//
//        switch(no){
//            case 1 :
//                attendCheck();
//            break;
//            case 2 :
//                attendConfirm();
//            break;
//            case 3 :
//                //메인메뉴 메서드 칸
//            break;
//        }
//
//    }
//
//    public void attendCheck(){
//    enum result == attendController.attendCheck();
//
//    if(result){
//        attendOutputView.printSuccess("출석이 완료 되었습니다.");
//        //출석으로 바뀐 DB정보 출력 칸
//    }else{
//        attendOutputView.printError("출석을 다시 시도해 주십시오.");
//        attendMenu();
//    }
//
//    }
//
//    public void attendConfirm(){
//
//    }
//}
