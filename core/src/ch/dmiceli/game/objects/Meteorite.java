package ch.dmiceli.game.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Meteorite extends GameObject {
    private final int moveDirectionX;
    private final int moveDirectionY;
    private int damage;

    public Meteorite(int xCoordinate, int yCoordinate, Texture img, int moveDirectionX, int moveDirectionY, int damage) {
        super(xCoordinate, yCoordinate, img);
        this.moveDirectionX = moveDirectionX;
        this.moveDirectionY = moveDirectionY;
    }

    public void move() {
        setX(getX() - moveDirectionX);
        super.setY(getY() - moveDirectionY);
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(getImg(), getX(), getY());
    }

    @Override
    public Texture getImg() {
        return super.getImg();
    }

    public int getDamage() {
        return damage;
    }
}
