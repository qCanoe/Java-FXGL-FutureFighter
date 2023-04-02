package com.canoe.FinalFantasy;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.KeepOnScreenComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.util.Duration;

public class PlayerFactory implements EntityFactory {
    @Spawns("p1")
    public Entity newP1(SpawnData data){
        AnimationChannel ac = new AnimationChannel
                (FXGL.image("motionless.png"), Duration.seconds(0.65),3);

        AnimatedTexture at = new AnimatedTexture(ac);
        at.loop();
        return FXGL.entityBuilder(data)
                .view(at)
                .type(GameType.PLAYER)
                .with(new KeepOnScreenComponent())
                .with(new PlayerComponent())
                .build();
    }


}
