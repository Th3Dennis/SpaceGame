package ch.dmiceli.game;

import com.badlogic.gdx.Game;

public class SpaceGame extends Game {
    @Override
    public void create() {
        setScreen(new MainMenuScreen(this));
    }
}