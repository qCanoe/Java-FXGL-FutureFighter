package com.canoe.FinalFantasy;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;
import com.almasb.fxgl.physics.PhysicsComponent;

import static com.almasb.fxgl.dsl.FXGL.image;
import static com.almasb.fxgl.dsl.FXGL.onKeyDown;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getInput;

/**
 * @author Almas Baimagambetov (almaslvl@gmail.com)
 */
public class PlayerComponent extends Component {


    private AnimatedTexture texture;

    private AnimationChannel animIdle, animWalkright, animWalkleft;

    private static final int SPEED = 200;

    //创建布尔变量检测是否按下了键盘
    private boolean isRightPressed = false;


    public PlayerComponent() {
        //写一个能使角色动起来的动画
        animIdle = new AnimationChannel
                (image("motionless.png"),
                        3, 32, 32, Duration.seconds(0.65), 0, 2);
        //使用forward.png完成animWalk，总共有8帧，每帧的宽高为85*67，每帧的间隔为0.65秒，从第0帧开始，到第7帧结束
        animWalkright = new AnimationChannel
                (image("forward.png"),
                        8, 85, 67, Duration.seconds(0.65), 0, 7);
        //使用backward.png完成animWalk，总共有8帧，每帧的宽高为85*67，每帧的间隔为0.65秒，从第0帧开始，到第7帧结束
        animWalkleft = new AnimationChannel
                (image("backward.png"),
                        8, 85, 67, Duration.seconds(0.65), 0, 7);

        texture = new AnimatedTexture(animIdle);
    }

    @Override
    public void onAdded() {
        entity.getViewComponent().addChild(texture);
    }

    @Override
    public void onUpdate(double tpf) {
        onKeyDown(KeyCode.D, "MRight", () -> {
            entity.translateX(SPEED * tpf);
            texture.loopAnimationChannel(animWalkright);
            isRightPressed = true;
        });
        onKeyDown(KeyCode.A, "MLeft", () -> {
            entity.translateX(-SPEED * tpf);
            texture.loopAnimationChannel(animWalkleft);
            isRightPressed = true;
        });
        if (!isRightPressed) {
            texture.loopAnimationChannel(animIdle);
        }
        //如果没有按下D或者A，就让角色停止移动
    }
}
