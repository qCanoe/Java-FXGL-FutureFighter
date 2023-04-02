package com.canoe.FinalFantasy;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.cutscene.Cutscene;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.input.virtual.VirtualButton;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;
import com.almasb.fxgl.physics.PhysicsComponent;

import java.util.List;
import java.util.Map;


import static com.almasb.fxgl.dsl.FXGL.*;
//不再需要FXGL.

public class FinalFantasy extends GameApplication {

    private Entity player;

    public FinalFantasy() {
        super();
    }

    //初始化游戏设置：长宽高，版本，图标，菜单等
    @Override
    protected void initSettings(GameSettings settings) {
        settings.setMainMenuEnabled(true);
        settings.setWidth(960);
        settings.setHeight(714);
        settings.setTitle("最终幻想");
    }

    //游戏预处理：加载资源，设置音量
    @Override
    protected void onPreInit() {
    }

    //设置输入：键盘，鼠标
    @Override
    protected void initInput() {
    }

    //设置游戏的变量，方便在其它类进行访问
    @Override
    protected void initGameVars(Map<String, Object> vars) {
    }

    //初始化游戏
    @Override
    protected void initGame() {
        getGameWorld().addEntityFactory(new PlayerFactory());
        getGameWorld().addEntityFactory(new SceneFactory());

        spawn("bg");

        

        player = spawn("p1", new SpawnData(100, 500));

        set("p1", player);


    }

    //初始化物理设置：碰撞
    @Override
    protected void initPhysics() {
    }

    //初始化一些界面上的组件
    @Override
    protected void initUI() {
    }

    //游戏开始后每一帧都会调用
    @Override
    protected void onUpdate(double tpf) {
    }

    //启动
    public static void main(String[] args) {
        launch(args);
    }
}