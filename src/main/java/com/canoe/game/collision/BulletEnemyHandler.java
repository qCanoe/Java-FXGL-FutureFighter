package com.canoe.game.collision;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import com.canoe.game.EntityType;
import com.canoe.game.plane.EnemyComponent1;

import static com.almasb.fxgl.dsl.FXGLForKtKt.inc;

public class BulletEnemyHandler extends CollisionHandler {

    public BulletEnemyHandler() {
        super(EntityType.BULLET, EntityType.ENEMY);
    }

    @Override
    protected void onCollisionBegin(Entity bullet, Entity enemy) {
        // 在子弹和敌人碰撞开始时，将它们都从游戏世界中删除
        FXGL.inc("score", 10);
        bullet.removeFromWorld();
        HealthIntComponent health = enemy.getComponent(HealthIntComponent.class);
        health.setValue(health.getValue() - 1);
        if (health.isZero()) {
            enemy.getComponent(EnemyComponent1.class).die();
        }
    }
}
