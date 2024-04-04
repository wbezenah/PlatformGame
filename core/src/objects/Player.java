package objects;

import static com.platform.game.Constants.PPM;
import com.platform.game.TouchControl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;

public class Player extends Entity {

    private int jumps;
    private TouchControl touchControl;
    private String spritePath = "player/player.png";
    private Texture texture;
    private Sprite sprite;

    public Player(float width, float height, Body body, TouchControl touchControl) {
        super(width, height, body);
        this.speed = 10f;
        this.jumps = 0;
        this.touchControl = touchControl;

        this.texture = new Texture(Gdx.files.internal(spritePath));
        this.sprite = new Sprite(texture);
    }

    private void checkInput() {
        vx = 0;
        vy = 0;

        if(touchControl.isTouched(TouchControl.ButtonType.RIGHT)) { vx = 1; }
        if(touchControl.isTouched(TouchControl.ButtonType.LEFT)) { vx = -1; }
        if(touchControl.isTouched(TouchControl.ButtonType.JUMP) && jumps < 2) {
            float force = body.getMass() * 12;
            body.setLinearVelocity(body.getLinearVelocity().x, 0);
            body.applyLinearImpulse(new Vector2(0, force), body.getPosition(), true);
            jumps++;
        }

        if(body.getLinearVelocity().y == 0) { jumps = 0; }

        body.setLinearVelocity(vx * speed, body.getLinearVelocity().y < 25 ? body.getLinearVelocity().y : 25);
    }

    @Override
    public void update() {
        pos = new Vector2(body.getPosition().x * PPM, body.getPosition().y * PPM);
        this.sprite.setPosition(pos.x - width / 2, pos.y - height / 2);

        this.checkInput();
    }

    @Override
    public void render(SpriteBatch batch) {
        this.sprite.draw(batch);
    }

    public void dispose() {
        this.texture.dispose();
    }
}
