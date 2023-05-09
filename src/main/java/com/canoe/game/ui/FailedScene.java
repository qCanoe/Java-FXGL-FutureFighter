package com.canoe.game.ui;

import com.almasb.fxgl.animation.Interpolators;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.scene.SubScene;
import com.almasb.fxgl.texture.Texture;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.util.Duration;



public class FailedScene extends SubScene {

    private final TranslateTransition tt;
    private Texture texture;
    public FailedScene() {
        texture = FXGL.texture("ui/scene/failedScene.png");
        texture.setLayoutX(90);
        texture.setLayoutY(FXGL.getAppHeight());
        tt = new TranslateTransition(Duration.seconds(1.5), texture);
        tt.setInterpolator(Interpolators.SMOOTH.EASE_OUT());
        tt.setFromY(0);
        tt.setToY(-600);
        //停顿2秒
        tt.setDelay(Duration.seconds(2));
        tt.setOnFinished(event -> {
            texture.setTranslateY(0);
            FXGL.getGameController().gotoMainMenu();
        });
        getContentRoot().getChildren().add(texture);
    }

    @Override
    public void onCreate() {
        tt.play();
    }
}
