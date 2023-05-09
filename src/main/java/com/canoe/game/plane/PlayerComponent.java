package com.canoe.game.plane;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGL.getGameTimer;

public class PlayerComponent extends Component {
    private double speed;


    public PlayerComponent() {

    }

    @Override
    public void onUpdate(double tpf) {
        speed = 300 * tpf;
    }


    public void up() {entity.translateY(-speed);
    }

    public void down() {
        entity.translateY(speed);
    }

    public void left() {
        entity.translateX(-speed);
    }

    public void right() {
        entity.translateX(speed);
    }
}
