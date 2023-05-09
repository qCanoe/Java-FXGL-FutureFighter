package com.canoe.game.plane;

import com.almasb.fxgl.audio.AudioPlayer;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;

import static com.almasb.fxgl.dsl.FXGL.*;

public class EnemyComponent1 extends Component {
    private double speed = 170;
    private double timer = 0.0;
    private AudioPlayer audioPlayer;
    public int dhp = 1;

    @Override
    public void onAdded() {
        //添加敌人生命值
        entity.addComponent(new HealthIntComponent(dhp));
        audioPlayer = getAudioPlayer();
    }

    //添加方法设置敌人生命值
    public void setDhp(int dhp) {
        this.dhp = dhp;
    }

    @Override
    public void onUpdate(double tpf) {
        entity.translateY(speed * tpf);
        timer += tpf;
        if (timer > 1.0) {
            shoot();
            timer = 0.0;
        }
    }

    public void shoot() {
        spawn("bullet2", new SpawnData(entity.getX()+12, entity.getY()+10));
    }

    public void die() {
        spawn("Explosion", entity.getCenter());
        audioPlayer.playSound(FXGL.getAssetLoader().loadSound("exp1.mp3"));
        entity.removeFromWorld();
    }
}
