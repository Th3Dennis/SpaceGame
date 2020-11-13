package ch.dmiceli.game.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public abstract class GameObject extends Rectangle {

    private Texture img;

    public GameObject(float x, float y, Texture img) {
        super(x, y, img.getWidth(), img.getHeight());
        this.img = img;
    }

    public GameObject(float x, float y, float width, float height, Texture img) {
        super(x, y, width, height);
        this.img = img;
    }

    public void render(SpriteBatch batch) {
    }

    public Texture getImg() {
        return img;
    }

    public void setImg(Texture img) {
        this.img = img;
    }


}
