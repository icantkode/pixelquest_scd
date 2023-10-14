package main.java.com.asher.pixelquest;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;

public class PlayerEnemyHandler extends CollisionHandler {
    public PlayerEnemyHandler(Object a, Object b) {
        super(AppEntities.PLAYER, AppEntities.ENEMY);
    }

    @Override
    protected void onCollisionBegin(Entity a, Entity b) {
        //set animation to death.gif
        //show game over screen
        //play goofy ahhh death sound
    }
}
// add Collidable Componenent to P and E
// add Bounding Box
// add a type
// attach this handler to the phy subsystem
// add PhysicsComponent to entities //config methods
// setGravity