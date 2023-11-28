package com.almasb.fxglgames;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.dsl.FXGL;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class AsteroidsMainMenu extends FXGLMenu {

    public AsteroidsMainMenu() {
        super(MenuType.MAIN_MENU);
        ImageView backgroundImageView = new ImageView(new Image("C:\\Users\\Asher Siddique\\Downloads\\pxfuel.jpg"));
        backgroundImageView.setFitWidth(FXGL.getAppWidth());
        backgroundImageView.setFitHeight(FXGL.getAppHeight());
        getContentRoot().getChildren().add(backgroundImageView);
        var newGameButton = new AsteroidsButton("Start new game", this::fireNewGame);
        var exitGameButton = new AsteroidsButton("Exit Game", this::fireExit);
        var creditsButton = new AsteroidsButton("Credits");
        var browserButton = new AsteroidsButton("Waitlist");

        var cordX = FXGL.getAppWidth() / 2 - 200 / 2;
        var cordY = FXGL.getAppHeight() / 2 - 40 / 2;
        newGameButton.setTranslateX(cordX);
        newGameButton.setTranslateY(cordY - 100);
        exitGameButton.setTranslateX(cordX);
        exitGameButton.setTranslateY(cordY - 50);
        creditsButton.setTranslateX(cordX);
        creditsButton.setTranslateY(cordY);
        browserButton.setTranslateX(cordX);
        browserButton.setTranslateY(cordY + 50);

        getContentRoot().getChildren().add(newGameButton);
        getContentRoot().getChildren().add(exitGameButton);
        getContentRoot().getChildren().add(creditsButton);
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
            var bg = new Rectangle(200, 40);
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
                    case "Credits":
                        //show cred screen
                        break;
                    case "Waitlist":
                        //launch browser
                        break;
                }
                // launch browser
                //credits whatever
            });
            getChildren().addAll(bg, text);
        }
    }

}
