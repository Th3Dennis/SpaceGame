package ch.dmiceli.game.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Laser extends GameObject {

    private final int damage;


    public Laser(int xCoordinate, int yCoordinate, Texture img, int damage) {
        super(xCoordinate, yCoordinate, img.getWidth() * 2, img.getHeight() * 2, img);
        this.damage = damage;
    }


    public void move(int xDifference) {
        setX(getX() + xDifference);
    }

    public void move() {
        setX(getX() + 5);
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(super.getImg(), getX(), getY(), getWidth(), getHeight());
    }

    public int getDamage() {
        return damage;
    }
}
