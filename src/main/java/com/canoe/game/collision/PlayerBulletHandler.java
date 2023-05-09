package com.canoe.game.collision;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import com.canoe.game.EntityType;
import com.canoe.game.plane.InvincibilityComponent;
import com.canoe.game.ui.FailedScene;

import static com.almasb.fxgl.dsl.FXGL.getb;
import static com.almasb.fxgl.dsl.FXGL.set;

/**
 * @author Almas Baimagambetov (almaslvl@gmail.com)
 */
public class PlayerBulletHandler extends CollisionHandler {

    public PlayerBulletHandler() {
        super(EntityType.PLAYER, EntityType.ENEMY_BULLET);
    }

    @Override
    protected void onCollisionBegin(Entity player, Entity bullet) {
        bullet.removeFromWorld();
        //玩家生命值-10
        if (!player.getComponent(InvincibilityComponent.class).isInvincible()) {
            HealthIntComponent health = player.getComponent(HealthIntComponent.class);

            //生命值不足10，生命值变为0
            if (health.getValue() < 10) {
                health.setValue(0);
            } else {
                health.setValue(health.getValue() - 10);
            }
        }
        //玩家生命值为0时游戏结束
        if (player.getComponent(HealthIntComponent.class).isZero()) {
            set("gameOver", true);
            FXGL.getSceneService().pushSubScene(new FailedScene());
        }
    }
}
