package com.ivn.holalibgdx.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.ivn.holalibgdx.model.Personaje;

public class Roca extends Personaje {

    public Roca(Vector2 posicion, Texture textura, int vidas, int velocidad) {
        super(posicion, textura, vidas, velocidad);
    }
}
