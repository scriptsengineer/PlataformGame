package br.com.expressobits.games.plataformer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.TimeUtils;

public class FPSLogger extends com.badlogic.gdx.graphics.FPSLogger {

    private long startTime;
    boolean debugInConsole;
    boolean debugInTitle;
    String titleName;

    public FPSLogger(String titleName, boolean debugInTitle, boolean debugInConsole){
        startTime = TimeUtils.nanoTime();
        this.debugInConsole = debugInConsole;
        this.debugInTitle = debugInTitle;
        this.titleName = titleName;
    }

    @Override
    public void log() {
        if (TimeUtils.nanoTime() - startTime > 1000000000) /* 1,000,000,000ns == one second */{
            if(debugInTitle){
                Gdx.graphics.setTitle(String.format("%s - FPS: %d",titleName,Gdx.graphics.getFramesPerSecond()));
            }
            if(debugInConsole){
                Gdx.app.log("FPSLogger", "fps: " + Gdx.graphics.getFramesPerSecond());
            }
            startTime = TimeUtils.nanoTime();
        }
    }
}
