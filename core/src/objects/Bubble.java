package objects;

import static com.platform.game.Constants.PPM;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;


public class Bubble extends Entity{
    private String spritePath = "enemy/bubble.png.png";
    private Texture texture;
    private Sprite sprite;
    public Bubble(float width, float height, Body body, float dir) {
        super(width, height, body);
        this.speed = 1f;
        this.vx = dir;
        body.setTransform(body.getPosition().x + dir , body.getPosition().y, 0);
        System.out.println(width + ", " + height + ", " + body.getPosition());
        this.texture = new Texture(Gdx.files.internal(spritePath));
        this.sprite = new Sprite(texture);
    }

    @Override
    public void update() {
        //System.out.println(body.getPosition().x + ", " + body.getPosition().y);
        body.setLinearVelocity(vx * speed,.15f);
        pos = new Vector2(body.getPosition().x * PPM, body.getPosition().y * PPM);
        this.sprite.setPosition(pos.x - width / 2, pos.y - height / 2);
    }

    @Override
    public void render(SpriteBatch batch) {
        this.sprite.draw(batch);
    }

    public void dispose() {
        this.getBody().getWorld().destroyBody(this.getBody());
        this.texture.dispose();
    }
}
