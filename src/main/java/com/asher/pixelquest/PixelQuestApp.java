package main.java.com.asher.pixelquest;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class PixelQuestApp extends GameApplication {
    @Override
    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setWidth(1000);
        gameSettings.setHeight(600);
        gameSettings.setTitle("Pixel Quest");
        gameSettings.setVersion("0.0.1");
    }
    Entity player;
    @Override
    protected void initGame(){
        player = FXGL.entityBuilder().at(300, 300).view(new Rectangle(10, 10, Color.RED)).buildAndAttach();
    }

    @Override
    protected void initInput() {
        FXGL.onKey(KeyCode.D, ()-> player.translateX(+1));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
