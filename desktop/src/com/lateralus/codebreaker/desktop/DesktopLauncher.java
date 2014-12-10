package com.lateralus.codebreaker.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.lateralus.codebreaker.CodeMain;

public class DesktopLauncher {

    public static void main(String[] args) {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "Code Breaker";
        cfg.width = 540;
        cfg.height = 960;
        new LwjglApplication(new CodeMain(), cfg);
    }

}
