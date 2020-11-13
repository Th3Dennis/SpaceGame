package ch.dmiceli.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public class Background {
    private Texture img;
    private int xPosition;
    private int xPosition2;
    private final int factor;


    public Background(Texture img, int xPosition) {
        this.img = img;
        factor = 2;
        this.xPosition = xPosition;
        this.xPosition2 = Gdx.graphics.getWidth() * factor;
    }

    public void move() {
        int speed = 10;
        xPosition -= speed;
        xPosition2 -= speed;
    }


    public boolean checkIfOutLeft(int width) {
        return xPosition + width <= 0;
    }

    public boolean checkIfOutLeft2(int width) {
        return xPosition2 + width <= 0;
    }

    public void render(Batch batch) {

        move();


        //Wenn erster BG aus dem Bild ist, Position x wieder nach rechts setzen
        if (checkIfOutLeft(Gdx.graphics.getWidth() * factor)) {
            xPosition = Gdx.graphics.getWidth() * 2 + xPosition2;
        }

        //Wenn zweiter BG aus dem Bild ist, Position x wieder nach rechts setzen
        if (checkIfOutLeft2(Gdx.graphics.getWidth() * factor)) {
            xPosition2 = Gdx.graphics.getWidth() * 2 + xPosition;
        }

        batch.draw(img, xPosition, 0, Gdx.graphics.getWidth() * factor, Gdx.graphics.getHeight());
        batch.draw(img, xPosition2, 0, Gdx.graphics.getWidth() * factor, Gdx.graphics.getHeight());
    }


    public Texture getImg() {
        return img;
    }

    public void setImg(Texture img) {
        this.img = img;
    }

    public int getxPosition() {
        return xPosition;
    }

    public void setxPosition(int xPosition) {
        this.xPosition = xPosition;
    }
}
