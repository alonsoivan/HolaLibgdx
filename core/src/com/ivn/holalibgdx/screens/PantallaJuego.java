package com.ivn.holalibgdx.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.Timer;
import com.ivn.holalibgdx.model.Bala;
import com.ivn.holalibgdx.model.Marciano;
import com.ivn.holalibgdx.model.Nave;
import com.ivn.holalibgdx.model.Roca;
import com.ivn.holalibgdx.util.Constantes;

import static com.ivn.holalibgdx.util.Constantes.*;

public class PantallaJuego implements Screen {
    private SpriteBatch batch;
    private Nave nave;
    private Array<Marciano> marcianos;
    private Array<Roca> rocas;
    private long tiempoJuegoE;
    private Sound impacto;
    private Music fondo;

    @Override
    public void show() {
        batch = new SpriteBatch();
        nave = new Nave(Vector2.Zero,new Texture("ship/f1.png"),3,VELOCIDAD_NAVE,false, Nave.TipoDisparo.RAFAGA);

        marcianos = new Array<>();
        rocas = new Array<>();

        impacto = Gdx.audio.newSound(Gdx.files.internal("sounds/impacto.mp3"));
        fondo = Gdx.audio.newMusic(Gdx.files.internal("sounds/fondo.mp3"));

        fondo.setLooping(true);
        fondo.play();
        fondo.setVolume(0.8F);

        generarRocas();
    }

    private void generarEnemigos() {
        if (TimeUtils.millis() - tiempoJuegoE > Constantes.TIEMPO_ENTRE_MARCIANOS) {
            marcianos.add(new Marciano(new Vector2(Gdx.graphics.getWidth() - 20, MathUtils.random(0, Gdx.graphics.getHeight())), new Texture("enemy/e_f6.png"), 3, VELOCIDAD_MARCIANOS));
            tiempoJuegoE = TimeUtils.millis();
        }
    }

    private void generarRocas() {

        Timer.schedule(new Timer.Task() {
            public void run(){
                rocas.add(new Roca(new Vector2(Gdx.graphics.getWidth() - 20, MathUtils.random(0, Gdx.graphics.getHeight())), new Texture("enemy/stone1.png"), 3, VELOCIDAD_ROCAS));
            }
        }, 1, 1);

    }

    @Override
    public void render(float delta) {
        actualizar();
        pintar();
    }

    @Override
    public void resize(int width, int height) {

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

    private void actualizar() {

        comprobarTeclado();

        generarEnemigos();

        //generarRocas();

        moverEnemigos();

        moverRocas();

        comprobarColisiones();

        moverBalas();
    }

    public void moverBalas(){
        for (Bala bala: nave.balas) {
            bala.mover();

            if (bala.getPosicion().x >= Gdx.graphics.getWidth())
                nave.balas.removeValue(bala, true);
        }
    }

    public void comprobarColisiones(){
        for(Marciano marciano: marcianos) {

            if(marciano.rect.overlaps(nave.rect)) {

                impacto.play(0.35f);

                nave.quitarvida();
                marcianos.removeValue(marciano,true);
                if (!nave.estaVivo())
                    System.exit(0);
            }

            for (Bala bala: nave.balas) {
                if (bala.rect.overlaps(marciano.rect)) {

                    impacto.play(0.35f);

                    marciano.quitarvida();

                    nave.balas.removeValue(bala, true);

                    if(!marciano.estaVivo())
                        marcianos.removeValue(marciano,true);
                }
            }
        }

        for(Roca roca: rocas) {
            if (roca.rect.overlaps(nave.rect))
                System.exit(0);


            for (Bala bala: nave.balas) {
                if (bala.rect.overlaps(roca.rect)) {
                    impacto.play(0.35f);

                    nave.balas.removeValue(bala, true);
                }
            }
        }
    }

    public void moverEnemigos(){
        for(Marciano marciano: marcianos) {
            marciano.moverIzquierda();

            if(marciano.getPosicion().x <= 0)
                marcianos.removeValue(marciano, true);

        }
    }

    public void moverRocas(){
        for(Roca roca: rocas) {
            roca.moverIzquierda();

            if(roca.getPosicion().x <= 0)
                rocas.removeValue(roca, true);
        }
    }

    private void comprobarTeclado(){
        if(Gdx.input.isKeyPressed(Input.Keys.UP))
            nave.moverArriba();
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN))
            nave.moverAbajo();
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT))
            nave.moverIzquierda();
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            nave.moverDerecha();

        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE))
            nave.disparar();

        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE))
            ((Game) Gdx.app.getApplicationListener()).setScreen(new PantallaMenuPrincipal());

    }

    private void pintar() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();

        nave.pintar(batch);

        for(Marciano marci: marcianos)
            marci.pintar(batch);

        for(Roca roca: rocas)
            roca.pintar(batch);

        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();

        nave.getTextura().dispose();

        for(Roca roca: rocas)
            roca.getTextura().dispose();

        for(Marciano marciano: marcianos)
            marciano.getTextura().dispose();
    }
}
