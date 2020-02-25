package com.ivn.holalibgdx.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import static com.ivn.holalibgdx.util.Constantes.VELOCIDAD_BALA;

public class Nave extends Personaje{

    public enum TipoDisparo{
        UNO, RAFAGA
    }

    private boolean inmune;
    private TipoDisparo disparo;

    private Sound sDisparo;

    public Array<Bala> balas;

    public Nave(Vector2 posicion, Texture textura, int vidas, int velocidad, boolean inmune, TipoDisparo disparo) {
        super(posicion, textura, vidas, velocidad);

        this.inmune = inmune;
        this.disparo = disparo;

        sDisparo = Gdx.audio.newSound(Gdx.files.internal("sounds/disparo.mp3"));

        balas = new Array<>();
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

    @Override
    public void pintar(SpriteBatch batch) {
        super.pintar(batch);

        for (Bala bala : balas)
            batch.draw(bala.getTextura(),bala.getPosicion().x,bala.getPosicion().y);
    }

    public void disparar(){

        sDisparo.play(0.35f);

        balas.add(new Bala(new Vector2(getPosicion().x+(getTextura().getWidth()),getPosicion().y+(getTextura().getHeight()/2)-(new Texture("ship/bullet.png").getHeight()/2)), new Texture("ship/bullet.png"),  VELOCIDAD_BALA));
    }
}
