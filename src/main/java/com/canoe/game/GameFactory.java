package com.canoe.game;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.ExpireCleanComponent;
import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.dsl.components.KeepOnScreenComponent;
import com.almasb.fxgl.dsl.components.OffscreenCleanComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.canoe.game.bullet.BulletComponent;
import com.canoe.game.bullet.EnemyBullet;
import com.canoe.game.plane.EnemyComponent1;
import com.canoe.game.plane.InvincibilityComponent;
import com.canoe.game.plane.PlayerComponent;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGL.*;

public class GameFactory implements EntityFactory {

    @Spawns("bullet1")
    public Entity newBullet1(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(EntityType.BULLET)
                .viewWithBBox("player/bullet1.png")
                .with(new CollidableComponent(true))
                .with(new OffscreenCleanComponent(), new BulletComponent())
                .build();
    }
    @Spawns("bullet2")
    public Entity newBullet2(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(EntityType.ENEMY_BULLET)
                .viewWithBBox("enemy/bullet2.png")
                .with(new CollidableComponent(true))
                .with(new OffscreenCleanComponent(), new EnemyBullet())
                .build();
    }
    @Spawns("bullet3")
    public Entity newBullet3(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(EntityType.ENEMY_BULLET)
                .viewWithBBox("enemy/bullet3.png")
                .with(new CollidableComponent(true))
                .with(new OffscreenCleanComponent(), new EnemyBullet())
                .build();
    }
    @Spawns("lazer")
    public Entity newLazer(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(EntityType.BULLET)
                .viewWithBBox("player/lazer1.png")
                .with(new CollidableComponent(true))
                .with(new OffscreenCleanComponent(), new BulletComponent())
                .build();
    }

    @Spawns("enemy1")
    public Entity newEnemy1(SpawnData data) {
        return FXGL.entityBuilder(data)
                .viewWithBBox("enemy/enemy1.png")
                .type(EntityType.ENEMY)
                .with(new CollidableComponent(true))
                .with(new OffscreenCleanComponent(), new EnemyComponent1())
                .build();
    }
    @Spawns("enemy2")
    public Entity newEnemy2(SpawnData data) {
        return FXGL.entityBuilder(data)
                .viewWithBBox("enemy/enemy2.png")
                .type(EntityType.ENEMY)
                .with(new CollidableComponent(true))
                .with(new OffscreenCleanComponent(), new EnemyComponent1())
                .build();
    }

    @Spawns("player")
    public Entity newPlayer(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(EntityType.PLAYER)
                .viewWithBBox("player/player1_main.png")
                .with(new PlayerComponent())
                .with(new KeepOnScreenComponent())
                .with(new CollidableComponent(true))
                .with(new HealthIntComponent(100))
                .with(new InvincibilityComponent(4.0))
                .build();
    }

    @Spawns("Explosion")
    public Entity newExplosion(SpawnData data) {

        return entityBuilder()
                .at(data.getX() - 40, data.getY() - 40)
                // we want a smaller texture, 80x80
                // it has 16 frames, hence 80 * 16
                .view(texture("ui/explosion.png", 80 * 16, 80).toAnimatedTexture(16, Duration.seconds(0.5)).play())
                .with(new ExpireCleanComponent(Duration.seconds(0.5)))
                //添加爆炸音效
                .build();
    }

    @Spawns("score")
    public Entity newScore(SpawnData data){
        return entityBuilder(data)
                .view("ui/score.png")
                .build();
    }

    @Spawns("bubble")
    public Entity newBubble(SpawnData data){
        return entityBuilder(data)
                .type(EntityType.BUBBLE)
                .view(texture("ui/bubble.png", 62*6, 71).toAnimatedTexture(6, Duration.seconds(0.5)).loop())
                .build();
    }

    @Spawns("item1")
    public Entity newItem1(SpawnData data){
        return entityBuilder(data)
                .viewWithBBox("ui/hpUP.png")
                .with(new CollidableComponent(true))
                .with(new OffscreenCleanComponent())
                .type(EntityType.ITEM)
                .build();
    }
}


