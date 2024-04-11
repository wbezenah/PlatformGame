package objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public abstract class Entity {
    protected Vector2 pos;
    protected float vx, vy, speed;
    protected float width, height;
    protected Body body;

    public Entity(float width, float height, Body body) {
        this.pos = body.getPosition();
        this.width = width;
        this.height = height;
        this.vx = 0;
        this.vy = 0;
        this.speed = 0;
        this.body = body;
        this.body.setUserData(this);
    }

    public boolean isColliding(Entity other) {
        return  pos.x < other.pos.x + other.width &&
                pos.x + width > other.pos.x &&
                pos.y < other.pos.y + other.height &&
                pos.y + height > other.pos.y;
    }

    public Body getBody() {
        return body;
    }

    public Vector2 getPos() {
        return this.pos;
    }

    public abstract void update();

    public abstract void render(SpriteBatch batch);
}
