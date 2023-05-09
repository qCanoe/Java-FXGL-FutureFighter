package com.canoe;

import com.almasb.fxgl.app.CursorInfo;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.LoadingScene;
import com.almasb.fxgl.app.scene.SceneFactory;
import com.almasb.fxgl.audio.AudioPlayer;
import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.cutscene.Cutscene;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.texture.AnimationChannel;
import com.almasb.fxgl.texture.Texture;
import com.canoe.game.*;
import com.canoe.game.GameFactory;
import com.canoe.game.collision.BulletEnemyHandler;
import com.canoe.game.collision.PlayerBulletHandler;
import com.canoe.game.collision.PlayerItemHandler;
import com.canoe.game.plane.EnemyComponent1;
import com.canoe.game.plane.InvincibilityComponent;
import com.canoe.game.plane.PlayerComponent;
import com.canoe.game.ui.GameLoadingScene;
import com.canoe.game.ui.MainMenu;
import com.canoe.login.window.LoginWindow;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.print.attribute.standard.Media;
import java.io.File;
import java.util.List;
import java.util.Map;

import static com.almasb.fxgl.dsl.FXGL.*;



public class Game extends GameApplication {
    public static final Object obj = new Object();
    private PlayerComponent playerComponent;
    private Entity Bg1;
    private Entity Bg2;
    private final int BACKGROUND_HEIGHT = 1024;
    private double tiemr = 0;
    private double timer2 = 0;
    private double timer3 = 0;
    private Entity player;
    private int e1 = 0;
    private double sp = 100;
    private AudioPlayer audioPlayer;
    SimpleDoubleProperty hpProperty = new SimpleDoubleProperty();
    SimpleDoubleProperty SP = new SimpleDoubleProperty();



    @Override
    protected void initSettings(GameSettings settings) {

        settings.setTitle("FutureFighter");
        settings.setVersion("0.1");
        settings.setWidth(512);
        settings.setHeight(1024);
        settings.setAppIcon("icon.png");
        settings.setMainMenuEnabled(true);
        settings.setDefaultCursor(new CursorInfo("cursor.png", 0, 0));
        settings.setSceneFactory(new SceneFactory() {
            @Override
            public FXGLMenu newMainMenu() {
                return new MainMenu();
            }

            @Override
            public LoadingScene newLoadingScene() {
                return new GameLoadingScene();
            }
        });
    }

    @Override
    protected void initGameVars(Map<String, Object> vars) {
        vars.put("score", 0);
    }

    @Override
    protected void initInput() {
        onKey(KeyCode.W, () -> playerComponent.up());
        onKey(KeyCode.S, () -> playerComponent.down());
        onKey(KeyCode.A, () -> playerComponent.left());
        onKey(KeyCode.D, () -> playerComponent.right());

        FXGL.getInput().addAction(new UserAction("protect") {
            @Override
            protected void onAction() {
                if (sp >= 50) {
                    sp = sp - 50;
                    player.getComponent(InvincibilityComponent.class).setInvincible(true);
                    audioPlayer.playSound(FXGL.getAssetLoader().loadSound("bubble.mp3"));
                }
            }
        }, KeyCode.SPACE);

        //按F键发射lazer
        FXGL.getInput().addAction(new UserAction("lazer") {
            @Override
            protected void onAction() {
                if (sp >= 20) {
                    sp = sp - 20;
                    getGameWorld().spawn("lazer",
                            new SpawnData(FXGL.random(50,500), player.getY() - 15));
                    audioPlayer.playSound(FXGL.getAssetLoader().loadSound("lazer.mp3"));
                }
            }
        }, KeyCode.F);
    }

    @Override
    protected void onPreInit() {
        getAssetLoader().loadSound("launch_sound.wav");
        getAssetLoader().loadSound("exp1.mp3");
        getAssetLoader().loadSound("select.wav");
        getAssetLoader().loadImage("enemy/enemy1.png");
        getAssetLoader().loadImage("enemy/enemy2.png");
    }

    @Override
    protected void initGame() {
        //播放开场动画
        runOnce(()-> {
            List<String> lines = getAssetLoader().loadText("chat.txt");
            Cutscene cutscene = new Cutscene(lines);
            getCutsceneService().startCutscene(cutscene);
        }, Duration.ONE);

        audioPlayer = getAudioPlayer();
        audioPlayer.loopMusic(FXGL.getAssetLoader().loadMusic("bgm1.mp3"));
        //返回主菜单后停止播放音乐

        getGameWorld().addEntityFactory(new GameFactory());
        getGameWorld().addEntityFactory(new Scene());
        Bg1 = spawn("bg");
        Bg2 = spawn("bg", new SpawnData(0, -1024));
        player = getGameWorld().spawn("player", new SpawnData(225, 800));


        //每0.5秒产生一个子弹在玩家的位置
        getGameTimer().runAtInterval(() -> {
            getGameWorld().spawn("bullet1",
                    new SpawnData(player.getX() + 10, player.getY() - 15));
        }, Duration.seconds(0.6));
        getGameTimer().runAtInterval(() -> {
            getGameWorld().spawn("bullet1",
                    new SpawnData(player.getX() + 45, player.getY() - 15));
        }, Duration.seconds(0.6));
        playerComponent = player.getComponent(PlayerComponent.class);
    }

    @Override
    protected void onUpdate(double tpf) {
        // 滚动背景
        Bg1.translateY(200 * tpf);
        Bg2.translateY(200 * tpf);
        if (Bg1.getY() >= BACKGROUND_HEIGHT) {
            Bg1.setY(Bg2.getY() - BACKGROUND_HEIGHT);
        }
        if (Bg2.getY() >= BACKGROUND_HEIGHT) {
            Bg2.setY(Bg1.getY() - BACKGROUND_HEIGHT);
        }
        tiemr += tpf;
        timer2 += tpf;
        if (tiemr >= 3 && e1 < 5) {
            tiemr = 0;
            e1++;
            Entity enemy1 = getGameWorld().spawn("enemy1", new SpawnData(FXGLMath.random(0, getAppWidth() - 50), -50));
        }

        if (tiemr >= 3 && e1 < 15 && e1 >= 5) {
            tiemr = 0;
            e1++;
            Entity enemy1 = getGameWorld().spawn("enemy1", new SpawnData(FXGLMath.random(0, getAppWidth() - 50), -50));
            enemy1.getComponent(EnemyComponent1.class).setDhp(2);
        }
        if (tiemr >= 2 && e1 < 30 && e1 >= 15) {
            tiemr = 0;
            e1++;
            Entity enemy1 = getGameWorld().spawn("enemy1", new SpawnData(FXGLMath.random(0, getAppWidth() - 50), -50));
            enemy1.getComponent(EnemyComponent1.class).setDhp(3);
        }
        if (tiemr >= 2 &&e1 >= 30) {
            tiemr = 0;
            e1++;
            Entity enemy1 = getGameWorld().spawn("enemy1", new SpawnData(FXGLMath.random(0, getAppWidth() - 50), -50));
            enemy1.getComponent(EnemyComponent1.class).setDhp(5);
        }


        //更新hpProperty
        hpProperty.set(player.getComponent(HealthIntComponent.class).getValue());

        //更新SP
        if (sp <= 100) {
            sp += tpf * 10;
        }
        SP.set(sp);

        if (player.getComponent(InvincibilityComponent.class).isInvincible()) {
            //在玩家位置循环播放无敌动画
            //如果已经有了，就不再生成
            if (getGameWorld().getEntitiesByType(EntityType.BUBBLE).isEmpty()) {
                Entity bubble = spawn("bubble", new SpawnData(player.getX() + 11, player.getY() - 6));
                //更新bubble位置
                getGameTimer().runAtInterval(() -> {
                    bubble.setX(player.getX() + 11);
                    bubble.setY(player.getY() - 6);
                }, Duration.seconds(0.01));
            }
        }
        if (!player.getComponent(InvincibilityComponent.class).isInvincible()) {
            //停止播放无敌动画
            getGameWorld().getEntitiesByType(EntityType.BUBBLE).forEach(Entity::removeFromWorld);

            timer3 += tpf;
            if(timer3>=10){
                timer3 = 0;
                //在随机位置生成道具item1
                getGameWorld().spawn("item1", new SpawnData(FXGLMath.random(50,500), FXGL.random(100,1000)));
                //过4秒后消失
                getGameTimer().runOnceAfter(() -> {
                    getGameWorld().getEntitiesByType(EntityType.ITEM).forEach(Entity::removeFromWorld);
                }, Duration.seconds(5));
            }
        }

        //如果玩家死亡，停止播放音乐
        if (player.getComponent(HealthIntComponent.class).isZero()) {
            audioPlayer.stopAllMusic();
            audioPlayer.playMusic(FXGL.getAssetLoader().loadMusic("game_crash.mp3"));
        }
    }


    @Override
    protected void initPhysics() {
        getPhysicsWorld().addCollisionHandler(new BulletEnemyHandler());
        getPhysicsWorld().addCollisionHandler(new PlayerBulletHandler());
        getPhysicsWorld().addCollisionHandler(new PlayerItemHandler());
    }

    @Override
    protected void initUI() {
        Texture hpBar1 = FXGL.texture(("ui/HpBar1.png"));
        FXGL.entityBuilder()
                .at(40, 47)
                .view(hpBar1)
                .buildAndAttach();
        Texture hpBar3 = FXGL.texture(("ui/HpBar3.png"));
        FXGL.entityBuilder()
                .at(46, 73)
                .view(hpBar3)
                .buildAndAttach();
        double width1 = hpBar3.getWidth();
        Rectangle rectangle1 = new Rectangle(width1, hpBar3.getHeight());
        hpBar3.setClip(rectangle1);
        SP.addListener((observable, oldValue, newValue) -> {
            rectangle1.setTranslateX(-width1 + newValue.doubleValue() / 100 * width1);
        });

        Texture hpBar2 = FXGL.texture("ui/HpBar2.png");
        double width = hpBar2.getWidth();
        Rectangle rectangle = new Rectangle(width, hpBar2.getHeight());
        hpBar2.setClip(rectangle);
        //显示血条
        FXGL.entityBuilder()
                .at(40, 50)
                .view(hpBar2)
                .buildAndAttach();
        HealthIntComponent hp = player.getComponent(HealthIntComponent.class);
        int HP = hp.getValue();
        hpProperty.addListener((observable, oldValue, newValue) -> {
            rectangle.setTranslateX(-width + newValue.doubleValue() / HP * width);
        });
        //分数框
        spawn("score", 310, 53);

        HBox scoreBox = new HBox();
        scoreBox.setPrefWidth(FXGL.getAppWidth());
        scoreBox.setAlignment(Pos.CENTER_RIGHT);
        scoreBox.setPadding(new Insets(10));

        FXGL.addUINode(scoreBox);
        Image[] imgs = new Image[10];
        ImageView[] ivs = new ImageView[10];
        for (int i = 0; i < imgs.length; i++) {
            imgs[i] = FXGL.image("num/" + i + ".png", 32, 43);
            ivs[i] = new ImageView();
            scoreBox.getChildren().add(ivs[i]);
        }
        FXGL.getip("score").addListener((ob, ov, nv) -> {
            String s = nv + "";
            for (int i = 0; i < 10 - s.length(); i++) {
                ivs[i].setImage(null);
            }
            for (int i = 0; i < s.length(); i++) {
                ivs[10 - s.length() + i].setImage(imgs[Integer.parseInt(s.charAt(i) + "")]);
            }
        });
    }

    public static void main(String[] args) throws InterruptedException {
        // 调用登录界面
        // 点击了登录按钮之后，拿到返回值
        // 如果返回值是true，关闭登录窗口，就调用launch(args)

        LoginWindow loginWindow = new LoginWindow();
        // 加锁
        synchronized (obj) {

            // 线程等待
            obj.wait();
        }
        // 获取登录结果
        if (loginWindow.getLoginRes()) {
            launch(args);
        }
    }
//        launch(args);
    }

