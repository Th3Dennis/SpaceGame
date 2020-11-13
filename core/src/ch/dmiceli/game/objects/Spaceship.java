package ch.dmiceli.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Spaceship extends GameObject implements CanShoot, Movable {

    private final boolean enemy;
    private int energy;
    private long time;


    public Spaceship(Texture img, int xCoordinate, int yCoordinate, int energy, boolean enemy) {
        super(xCoordinate, yCoordinate, img);
        this.enemy = enemy;
        this.energy = energy;
        time = System.currentTimeMillis();
    }


    @Override
    public Laser shoot() {
        System.out.println("time: " + time + " now: " + System.currentTimeMillis());
        if (System.currentTimeMillis() - 250 > time) {
            int xCoord = (int) (getX() + getWidth() + 1);
            int yCoord = (int) (getY() + (getHeight() / 2));

            time = System.currentTimeMillis();
            return new Laser(xCoord, yCoord, new Texture("Lasers/laserRed01.png"), 50);
        }

        return null;
    }

    @Override
    public void move(int x, int y) {
        setX(x);
        setY(y);
    }

    public void moveUp(int yDifference) {
        if (getY() + getImg().getHeight() < Gdx.graphics.getHeight()) {
            setY(getY() + yDifference);
        }
    }

    public void moveDown(int yDifference) {
        if (getY() > 0) {
            setY(getY() - yDifference);
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(getImg(), getX(), getY(), getWidth(), getHeight());

    }

    public boolean isEnemy() {
        return enemy;
    }

    public void damage(int damage) {
        energy -= damage;
    }


    @Override
    public Texture getImg() {
        return super.getImg();
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }
}
