package objects;

import static com.platform.game.Constants.PPM;
import com.platform.game.TouchControl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public class Crab extends Entity{
    private String spritePath = "enemy/crab.png";
    private Texture texture;
    private Sprite sprite;

    float accel = .1f;

    public Crab(float width, float height, Body body) {
        super(width, height, body);
        this.speed = 0f;
        this.vx = 1;

        this.texture = new Texture(Gdx.files.internal(spritePath));
        this.sprite = new Sprite(texture);
    }
    @Override
    public void update() {
        System.out.println(speed);
        speed = speed + accel;
        if(speed > 10){
            speed = 0;
            this.vx = this.vx * -1;
        }
        body.setLinearVelocity(vx * speed,0);
        pos = new Vector2(body.getPosition().x * PPM, body.getPosition().y * PPM);
        this.sprite.setPosition(pos.x - width / 2, pos.y - height / 2);
    }

    @Override
    public void render(SpriteBatch batch) {
            this.sprite.draw(batch);
    }

    public void dispose() {
        this.texture.dispose();
    }
}
