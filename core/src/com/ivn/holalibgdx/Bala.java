package com.ivn.holalibgdx;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class Bala extends Personaje {
    public Bala(Vector2 posicion, Texture textura, int velocidad) {
        super(posicion, textura, 0, velocidad);
    }

    public void mover(){
        getPosicion().add(new Vector2(1,0).scl(getVelocidad()));
        rect.setPosition(getPosicion());
    }
}
