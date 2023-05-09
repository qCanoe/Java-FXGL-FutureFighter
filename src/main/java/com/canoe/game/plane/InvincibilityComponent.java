package com.canoe.game.plane;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.util.Duration;

public class InvincibilityComponent extends Component {

    private boolean invincible;
    private double duration;
    private double timeLeft;


    public InvincibilityComponent(double duration) {

        this.duration = duration;
        this.timeLeft = duration;
    }

    public boolean isInvincible() {
        return invincible;
    }

    public void setInvincible(boolean invincible) {
        this.invincible = invincible;
        if (invincible) {
            timeLeft = duration;
        }
    }



    @Override
    public void onUpdate(double tpf) {
        if (invincible) {
            timeLeft -= tpf;
            if (timeLeft <= 0) {
                invincible = false;
                timeLeft = 0;
            }
        }
    }
}