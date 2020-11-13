package ch.dmiceli.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import ch.dmiceli.game.objects.Laser;
import ch.dmiceli.game.objects.Meteorite;
import ch.dmiceli.game.objects.Spaceship;

public class GameScreen extends BaseScreen {
    SpriteBatch batch;
    Texture img;
    private final Background background1;
    private final Spaceship spaceship;
    private final ArrayList<Laser> lasers;
    private final ArrayList<Spaceship> enemySpaceships;
    private final ArrayList<Meteorite> meteorites;
    private final Skin skin;

    public GameScreen(Game parent) {
        super(parent);
        skin = new Skin(Gdx.files.internal("skin/vhs-ui.json"));
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
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background1.render(batch);


        //Input
        inputHandler();


        //Logic for Lasers
        laserLogic();


        //place meteors
        meteoritePlacer();

        //Move meteors
        meteoriteMover();


        for (Spaceship spaceship : enemySpaceships) {
            spaceship.render(batch);
        }

        checkPlayerCollision();
        spaceship.render(batch);

        if (isPlayerDead()) {
            parent.setScreen(new MainMenuScreen(parent));
        }

        batch.end();
    }

    private boolean isPlayerDead() {
        return spaceship.getEnergy() <= 0;
    }

    private void meteoritePlacer() {
        Random r = new Random();
        int high = 500;
        int low = 1;
        int result = r.nextInt(high - low) + low;
        if (result == 1) {
            int xDirection = r.nextInt(5 - 2) + 2;
            int yDirection = (r.nextInt((2 - 1) + 1) - 1) - 1;
            int startHeight = r.nextInt(Gdx.graphics.getHeight() - 1) + 1;

            meteorites.add(new Meteorite(Gdx.graphics.getWidth() + 1, startHeight, new Texture("Meteors/meteorBrown_big4.png"), xDirection, yDirection, 50));
        }
    }

    private void inputHandler() {
        if (Gdx.input.isTouched()) {
            if (Gdx.input.getX() < Gdx.graphics.getWidth() / 2) {
                spaceship.moveUp(10);
                Laser laser = spaceship.shoot();
                if (laser != null) {
                    lasers.add(laser);
                }

            }
            if (Gdx.input.getX() > Gdx.graphics.getWidth() / 2) {
                spaceship.moveDown(10);
            }
        }
    }

    private void laserLogic() {
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
    }

    private void meteoriteMover() {
        for (Iterator<Meteorite> iterator = meteorites.iterator(); iterator.hasNext(); ) {
            Meteorite meteorite = iterator.next();
            meteorite.move();
            meteorite.render(batch);
            if (meteorite.getX() + meteorite.getWidth() < 0 || meteorite.getX() > Gdx.graphics.getWidth()) {
                iterator.remove();
            }
        }
    }

    private boolean checkLaserCollisions(Laser laser) {
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


    private void checkPlayerCollision() {
        for (Iterator<Meteorite> iterator = meteorites.iterator(); iterator.hasNext(); ) {
            Meteorite meteorite = iterator.next();
            if (spaceship.overlaps(meteorite)) {
                spaceship.damage(meteorite.getDamage());
                iterator.remove();
            }
        }
    }


    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void show() {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
