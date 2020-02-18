package com.ivn.holalibgdx;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class Nave extends Personaje{
    public enum TipoDisparo{
        UNO, RAFAGA
    }
    private boolean inmune;
    private TipoDisparo disparo;

    public Nave(Vector2 posicion, Texture textura, int vidas, int velocidad, boolean inmune, TipoDisparo disparo) {
        super(posicion, textura, vidas, velocidad);
        this.inmune = inmune;
        this.disparo = disparo;
    }

    public boolean isInmune() {
        return inmune;
    }

    public void setInmune(boolean inmune) {
        this.inmune = inmune;
    }

    public TipoDisparo getDisparo() {
        return disparo;
    }

    public void setDisparo(TipoDisparo disparo) {
        this.disparo = disparo;
    }
}
