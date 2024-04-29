package com.platform.game;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

import objects.Crab;
import objects.Player;
import objects.Token;

public class PlayerContactListener implements ContactListener {
    private GameScreen gameScreen;
    public PlayerContactListener (GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }
    @Override
    public void beginContact(Contact contact) {
        Object userDataA = contact.getFixtureA().getBody().getUserData();
        Object userDataB = contact.getFixtureB().getBody().getUserData();

        if(userDataA instanceof Player || userDataB instanceof Player) {
            if(userDataB instanceof Token || userDataA instanceof Token) {
                gameScreen.queueSetLevel(gameScreen.getLevelManager().currentLevel + 1);
            }
            else if(userDataB instanceof String || userDataA instanceof String) {
                String str = (String) (userDataB instanceof String ? userDataB : userDataA);
                if(str.equals("danger")) {
                    gameScreen.queueSetLevel(gameScreen.getLevelManager().currentLevel);
                }
            }
            else if(userDataB instanceof Crab || userDataA instanceof Crab) {
                gameScreen.queueSetLevel(gameScreen.getLevelManager().currentLevel);
            }
        }

    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
