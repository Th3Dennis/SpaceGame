package ch.dmiceli.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import ch.dmiceli.game.objects.Laser;
import ch.dmiceli.game.objects.Meteorite;
import ch.dmiceli.game.objects.Spaceship;

import java.util.ArrayList;
import java.util.Iterator;

public class MyGdxGame extends ApplicationAdapter {
    SpriteBatch batch;
    Texture img;
    private Background background1;
    private Spaceship spaceship;
    private ArrayList<Laser> lasers;
    private ArrayList<Spaceship> enemySpaceships;
    private ArrayList<Meteorite> meteorites;


    @Override
    public void create() {
        batch = new SpriteBatch();
        lasers = new ArrayList<>();
        meteorites = new ArrayList<>();
        enemySpaceships = new ArrayList<>();
        background1 = new Background(new Texture("back.png"), 0);

        int middleY = Gdx.graphics.getHeight() / 2;

        spaceship = new Spaceship(new Texture("playerShip1_red.png"), 0, 0, 100, false);
        spaceship.setWidth(spaceship.getWidth() * 1.3f).setHeight(spaceship.getHeight() * 1.3f);
        spaceship.move(spaceship.getImg().getWidth(), middleY);


        meteorites.add(new Meteorite(Gdx.graphics.getWidth() + 1, Gdx.graphics.getHeight(), new Texture("Meteors/meteorBrown_big4.png"), 5, 1, 50));

        final Spaceship spaceshipEnemy = new Spaceship(new Texture("Enemies/enemyGreen3.png"), Gdx.graphics.getWidth() + 1, middleY, 100, true);
        spaceshipEnemy.setWidth(spaceship.getWidth() * 1.3f).setHeight(spaceship.getHeight() * 1.3f);
        spaceshipEnemy.move((int) (Gdx.graphics.getWidth() - spaceshipEnemy.getWidth() - 100), middleY);
        enemySpaceships.add(spaceshipEnemy);



    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background1.render(batch);

        //Input
        if (Gdx.input.isTouched()) {
            if (Gdx.input.getX() < Gdx.graphics.getWidth() / 2) {
                spaceship.moveUp(10);
                Laser laser = spaceship.shoot();
                if (laser != null){
                    lasers.add(laser);
                }

            }
            if (Gdx.input.getX() > Gdx.graphics.getWidth() / 2) {
                spaceship.moveDown(10);
            }
        }


        //Logic for Lasers
        for (Iterator<Laser> iterator = lasers.iterator(); iterator.hasNext(); ) {
            Laser laser = iterator.next();
            laser.move();
            laser.render(batch);
            //Check for collisions
            if (checkLaserCollisions(laser)) {
                iterator.remove();
            } else if (laser.getX() > Gdx.graphics.getWidth()) {
                iterator.remove();
            }
        }


        //Move meteors
        for (Iterator<Meteorite> iterator = meteorites.iterator(); iterator.hasNext(); ) {
            Meteorite meteorite = iterator.next();
            meteorite.move();
            meteorite.render(batch);
            if (meteorite.getX() + meteorite.getWidth() < 0 || meteorite.getX() > Gdx.graphics.getWidth()) {
                iterator.remove();
            }
        }


        for (Spaceship spaceship : enemySpaceships) {
            spaceship.render(batch);
        }

        checkPlayerCollision();
        spaceship.render(batch);


        batch.end();
    }

    public boolean checkLaserCollisions(Laser laser) {
        for (Spaceship spaceship : enemySpaceships) {
            if (laser.overlaps(spaceship)) {
                spaceship.damage(laser.getDamage());
                if (spaceship.getEnergy() <= 0) {
                    enemySpaceships.remove(spaceship);
                }
                return true;
            }
        }

        for (Meteorite meteorite : meteorites) {
            if (laser.overlaps(meteorite)) {
                return true;
            }
        }
        return false;
    }


    public void checkPlayerCollision(){
        for (Meteorite meteorite : meteorites){
            if (spaceship.overlaps(meteorite)){
                spaceship.damage(meteorite.getDamage());
                meteorites.remove(meteorite);
            }
        }
    }


    @Override
    public void dispose() {
        batch.dispose();
        img.dispose();
    }
}
