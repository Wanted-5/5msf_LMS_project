package com.lms.global.AppContext;

import com.lms.global.common.VillageAppContext;

import java.sql.Connection;

public class AppContext {

    public final VillageAppContext villageAppContext;

    public AppContext(Connection con) {
        this.villageAppContext = new VillageAppContext(con);
    }
}