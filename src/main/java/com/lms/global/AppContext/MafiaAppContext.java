package com.lms.global.AppContext;


import com.lms.domain.mafia.controller.MafiaController;
import com.lms.domain.mafia.service.MafiaService;
import com.lms.domain.mafia.view.MafiaInputView;
import com.lms.domain.mafia.view.MafiaOutputView;

import java.sql.Connection;

public class MafiaAppContext {


    public final MafiaInputView mafiaInputView;

    public MafiaAppContext(Connection con) {

        MafiaService mafiaService = new MafiaService(con);
        MafiaController mafiaController = new MafiaController(mafiaService);
        MafiaOutputView mafiaOutputView = new MafiaOutputView();

        this.mafiaInputView =  new MafiaInputView(mafiaController, mafiaOutputView);
    }

}
