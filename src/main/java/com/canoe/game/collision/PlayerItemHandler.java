package com.canoe.game.collision;

import com.almasb.fxgl.audio.AudioPlayer;
import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import com.canoe.game.EntityType;
import com.canoe.game.plane.InvincibilityComponent;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getAudioPlayer;

public class PlayerItemHandler extends CollisionHandler {
private AudioPlayer audioPlayer;
    public PlayerItemHandler() {
        super(EntityType.PLAYER, EntityType.ITEM);
    }

    @Override
    protected void onCollisionBegin(Entity player, Entity item) {
        audioPlayer = getAudioPlayer();
        item.removeFromWorld();
        //玩家生命值+30
            HealthIntComponent health = player.getComponent(HealthIntComponent.class);
            if(health.getValue() + 30 > 100)
                health.setValue(100);
            else{
            health.setValue(health.getValue() + 30);}
            audioPlayer.playSound(FXGL.getAssetLoader().loadSound("items.wav"));
        }
    }