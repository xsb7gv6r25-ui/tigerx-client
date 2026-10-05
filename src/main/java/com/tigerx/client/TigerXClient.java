package com.tigerx.client;

import com.tigerx.client.core.ModuleManager;
import net.fabricmc.api.ModInitializer;

public class TigerXClient implements ModInitializer {
    public static final String MOD_ID = "tigerx";
    public static final String MOD_NAME = "TigerX Client";
    public static final String MOD_VERSION = "0.1.0";

    public static ModuleManager moduleManager;

    @Override
    public void onInitialize() {
        moduleManager = new ModuleManager();
    }
}
