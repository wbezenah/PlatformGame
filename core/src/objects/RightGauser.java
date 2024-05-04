package objects;

import static com.platform.game.Constants.PPM;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.platform.game.BodyManager;
import com.platform.game.GameScreen;

import java.util.ArrayList;

public class RightGauser extends Entity{
    private String spritePathRight = "enemy/right Gauser.png.png";
    private String spritePathLeft = "enemy/left Gauser.png.png";
    private Texture texture;
    private Sprite sprite;
    private ArrayList<Bubble> bubbles;
    private ArrayList<Float> bLoc;
    int bubbleTimer = 0;
    public RightGauser(float width, float height, Body body, int dir) {
        super(width, height, body);
        this.vx = dir;
        if(dir == -1){
            this.texture = new Texture(Gdx.files.internal(spritePathRight));
        }else{
            this.texture = new Texture(Gdx.files.internal(spritePathLeft));
        }
        this.sprite = new Sprite(texture);
        bubbles = new ArrayList<Bubble>();
        bLoc = new ArrayList<Float>();
    }

    @Override
    public void update() {
        bubbleTimer++;
        if(bubbleTimer == 700){
            bubbleTimer = 0;
            makeBubble();
        }
        for(int i =0; i < bubbles.size(); i++){
            bubbles.get(i).update();
            if(Math.abs(bubbles.get(i).getPos().x - bLoc.get(i)) <= .001) {
                bubbles.get(i).dispose();
                bubbles.remove(i);
                bLoc.remove(i);
            }else {
                bLoc.set(i, bubbles.get(i).getPos().x);
            }
        }

        pos = new Vector2(body.getPosition().x * PPM, body.getPosition().y * PPM);
        this.sprite.setPosition(pos.x - width / 2, pos.y - height / 2);

    }

    @Override
    public void render(SpriteBatch batch) {
        for(int i =0; i < bubbles.size(); i++){
            bubbles.get(i).render(batch);
        }
        this.sprite.draw(batch);
    }

    public void dispose() {
        this.texture.dispose();
    }

    private void makeBubble(){
        System.out.println("new bubble");
        Vector2 tmpPos = new Vector2(pos.x, pos.y);
        System.out.println(tmpPos);
        Body tmp = BodyManager.createBody(tmpPos, 64, 64, false, this.body.getWorld());
        bubbles.add(new Bubble(64, 64, tmp, this.vx));
        bLoc.add(tmp.getPosition().x);
    }
}
