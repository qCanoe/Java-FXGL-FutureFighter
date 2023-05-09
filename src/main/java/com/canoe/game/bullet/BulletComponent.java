package com.canoe.game.bullet;


import com.almasb.fxgl.entity.component.Component;

public class BulletComponent extends Component {
    private double speed = 500;

    @Override
    public void onUpdate(double tpf) {
        entity.translateY(-speed * tpf);
    }
}
