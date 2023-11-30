package com.asher.scdpixelquest;

import com.almasb.fxgl.animation.Interpolators;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.SceneFactory;
import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Map;

import static com.almasb.fxgl.dsl.FXGL.*;

public class PixelQuestApp extends GameApplication {
    private Entity player;
Text text;
    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(500);
        settings.setHeight(750);
        settings.setTitle("PixelQuest");
        settings.setVersion("0.1");
        settings.setMainMenuEnabled(true);
        //settings.setEnabledMenuItems(EnumSet.of(MenuItem.EXTRA));
        settings.setSceneFactory(new SceneFactory() {
            @NotNull
            @Override
            public FXGLMenu newMainMenu() {
                return new PixelQuestMainMenu();
            }
        });
    }

    @Override
    protected void initInput() {
        onKey(KeyCode.A, () -> player.getComponent(PlayerComponent.class).rotateLeft());
        onKey(KeyCode.D, () -> player.getComponent(PlayerComponent.class).rotateRight());
        onKey(KeyCode.W, () -> player.getComponent(PlayerComponent.class).moveForward());
        onKey(KeyCode.S, () -> player.getComponent(PlayerComponent.class).moveBackward());
        //onKey(KeyCode.SPACE, () -> player.getComponent(PlayerComponent.class).shoot());
        onKeyDown(KeyCode.SPACE, () -> player.getComponent(PlayerComponent.class).shoot());
        onKey(KeyCode.G, () -> {
            removeUINode(text);
            double rotation = player.getRotation();
            player.removeFromWorld();
            player = spawn("godplayer", player.getPosition().getX(), player.getPosition().getY());
            player.setRotation(rotation);

            Text text = getUIFactoryService().newText("", 24);
            text.textProperty().bind(getip("score").asString("Score: [%d]"));
            getWorldProperties().addListener("score", (prev, now) -> animationBuilder()
                    .duration(Duration.seconds(0.5))
                    .interpolator(Interpolators.BOUNCE.EASE_OUT())
                    .repeat(2)
                    .autoReverse(true)
                    .scale(text)
                    .from(new Point2D(1, 1))
                    .to(new Point2D(1.2, 1.2))
                    .buildAndPlay());
            addUINode(text, 20, 50);
            addVarText("godmode", 20, 70);
        });
    }

    @Override
    protected void initGameVars(Map<String, Object> vars) {
        vars.put("score", 0);
        vars.put("lives", 3);
        vars.put("godmode", "GOD MODE!!!");
    }

    @Override
    protected void initGame() {
        getSettings().setGlobalSoundVolume(0.1);
        getGameWorld().addEntityFactory(new GameEntityFactory());
        spawn("background");
        player = spawn("player", (double) getAppWidth() / 2, (double) getAppHeight() / 2);
        run(() -> {
            Entity e = getGameWorld().create("asteroid", new SpawnData(random(0, getAppWidth()), random(0, getAppHeight())));
            spawnWithScale(e, Duration.seconds(0.75), Interpolators.BOUNCE.EASE_OUT());
        }, Duration.seconds(2));
    }

    @Override
    protected void initPhysics() {
        onCollisionBegin(EntityType.BULLET, EntityType.ASTEROID, (bullet, asteroid) -> {
            var hp = asteroid.getComponent(HealthIntComponent.class);
            if (hp.getValue() > 1) {
                bullet.removeFromWorld();
                hp.damage(1);
                return;
            }
            spawn("scoreText", new SpawnData(asteroid.getX(), asteroid.getY()).put("text", "+100"));
            killAsteroid(asteroid);
            bullet.removeFromWorld();
            inc("score", +100);
        });
        onCollisionBegin(EntityType.PLAYER, EntityType.ASTEROID, (player, asteroid) -> {
            killAsteroid(asteroid);
            player.setPosition((double) getAppWidth() / 2, (double) getAppHeight() / 2);
            inc("lives", -1);
            int lives = geti("lives");
            if (lives <= 0) {
                player.removeFromWorld();
                getDialogService().showConfirmationBox("Demo Over. Play Again?", yes -> {
                    if (yes) {
                        getGameWorld().getEntitiesCopy().forEach(Entity::removeFromWorld);
                        getGameController().startNewGame();
                    } else {
                        try {
                            Files.write(Path.of("C:\\Users\\Asher Siddique\\Desktop\\highscore.txt"), (geti("score") +"\n").getBytes(), StandardOpenOption.APPEND);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        getGameController().exit();
                    }
                });
            }
        });
    }

    private void killAsteroid(Entity asteroid) {
        Point2D explosionSpawnPoint = asteroid.getCenter().subtract(64, 64);
        spawn("explosion", explosionSpawnPoint);
        asteroid.removeFromWorld();
    }

    @Override
    protected void initUI() {
        text = getUIFactoryService().newText("", 24);
        text.textProperty().bind(getip("score").asString("Score: [%d]"));
        getWorldProperties().addListener("score", (prev, now) -> animationBuilder()
                .duration(Duration.seconds(0.5))
                .interpolator(Interpolators.BOUNCE.EASE_OUT())
                .repeat(2)
                .autoReverse(true)
                .scale(text)
                .from(new Point2D(1, 1))
                .to(new Point2D(1.2, 1.2))
                .buildAndPlay());
        addUINode(text, 20, 50);
        addVarText("lives", 20, 70);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
