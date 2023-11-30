package com.asher.scdpixelquest;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.dsl.FXGL;
import javafx.beans.binding.Bindings;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class PixelQuestMainMenu extends FXGLMenu {
    public PixelQuestMainMenu() {
        super(MenuType.MAIN_MENU);
        ImageView backgroundImageView = new ImageView(new Image("assets/pxfuel-overlay.jpg"));
        backgroundImageView.setFitWidth(FXGL.getAppWidth());
        backgroundImageView.setFitHeight(FXGL.getAppHeight());
        getContentRoot().getChildren().add(backgroundImageView);
        var newGameButton = new AsteroidsButton("Start New Game", this::fireNewGame);
        var exitGameButton = new AsteroidsButton("Exit Game", this::fireExit);
        //var creditsButton = new AsteroidsButton("Credits");
        var browserButton = new AsteroidsButton("Join PixelQuest 2 Waitlist");

        var cordX = FXGL.getAppWidth() / 2 - 200 / 2;
        var cordY = FXGL.getAppHeight() / 2 - 40 / 2;
        newGameButton.setTranslateX(cordX);
        newGameButton.setTranslateY(cordY - 100);
        exitGameButton.setTranslateX(cordX);
        exitGameButton.setTranslateY(cordY - 50);
//        creditsButton.setTranslateX(cordX);
//        creditsButton.setTranslateY(cordY);
        browserButton.setTranslateX(cordX - 25 );
        browserButton.setTranslateY(cordY);

        getContentRoot().getChildren().add(newGameButton);
        getContentRoot().getChildren().add(exitGameButton);
        //getContentRoot().getChildren().add(creditsButton);
        getContentRoot().getChildren().add(browserButton);

    }

    private static class AsteroidsButton extends StackPane {
        public AsteroidsButton(String name, Runnable action) {

            var bg = new Rectangle(200, 40);
            bg.setStroke(Color.WHITE);

            var text = FXGL.getUIFactoryService().newText(name, Color.WHITE, 18);

            bg.fillProperty().bind(
                    Bindings.when(hoverProperty()).then(Color.WHITE).otherwise(Color.BLACK)
            );

            text.fillProperty().bind(
                    Bindings.when(hoverProperty()).then(Color.BLACK).otherwise(Color.WHITE)
            );

            setOnMouseClicked(e -> action.run());

            getChildren().addAll(bg, text);
        }

        public AsteroidsButton(String name) {
            var bg = new Rectangle(250, 40);
            bg.setStroke(Color.WHITE);
            var text = FXGL.getUIFactoryService().newText(name, Color.WHITE, 18);
            bg.fillProperty().bind(
                    Bindings.when(hoverProperty()).then(Color.WHITE).otherwise(Color.BLACK)
            );
            text.fillProperty().bind(
                    Bindings.when(hoverProperty()).then(Color.BLACK).otherwise(Color.WHITE)
            );

            setOnMouseClicked(e -> {
                switch (name) {
                    case "Join PixelQuest 2 Waitlist":
                        try {
                            Desktop.getDesktop().browse(new URI("http://localhost:8080/prelaunch.html"));
                        } catch (IOException | URISyntaxException ex) {
                            throw new RuntimeException(ex);
                        }
                        break;
                }
            });
            getChildren().addAll(bg, text);
        }
    }

}
