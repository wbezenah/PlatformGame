package objects;

import static com.platform.game.Constants.PPM;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class RightGauser extends Entity{
    private String spritePath = "enemy/right Gauser.png.png";
    private Texture texture;
    private Sprite sprite;
    public RightGauser(float width, float height, Body body) {
        super(width, height, body);
        this.vx = 1;

        this.texture = new Texture(Gdx.files.internal(spritePath));
        this.sprite = new Sprite(texture);
    }

    @Override
    public void update() {
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
