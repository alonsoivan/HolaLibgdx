package com.ivn.holalibgdx;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.Timer;


import static com.ivn.holalibgdx.Constantes.VELOCIDAD_NAVE;

public class Application extends ApplicationAdapter {
	private SpriteBatch batch;
	private Nave nave;
	private Array<Marciano> marcianos;
	private Array<Roca> rocas;
	private long tiempoJuegoE;

	@Override
	public void create () {
		batch = new SpriteBatch();
		nave = new Nave(Vector2.Zero,new Texture("ship/f1.png"),3,VELOCIDAD_NAVE,false, Nave.TipoDisparo.RAFAGA);

		marcianos = new Array<>();
		rocas = new Array<>();

		generarRocas();
	}

	private void generarEnemigos() {
		if (TimeUtils.millis() - tiempoJuegoE > Constantes.TIEMPO_ENTRE_MARCIANOS) {
			marcianos.add(new Marciano(new Vector2(Gdx.graphics.getWidth() - 20, MathUtils.random(0, Gdx.graphics.getHeight())), new Texture("enemy/e_f1.png"), 3, 10));
			tiempoJuegoE = TimeUtils.millis();
		}
	}

	private void generarRocas() {

        Timer.schedule(new Timer.Task() {
            public void run(){
                rocas.add(new Roca(new Vector2(Gdx.graphics.getWidth() - 20, MathUtils.random(0, Gdx.graphics.getHeight())), new Texture("enemy/stone1.png"), 3, 10));
            }
        }, 1, 1);

	}

	@Override
	public void render () {
		actualizar();
		pintar();
	}

	private void actualizar() {

		comprobarTeclado();

		generarEnemigos();

		//generarRocas();

		moverEnemigos();

		moverRocas();

		comprobarColisiones();

	}

	public void comprobarColisiones(){
		for(Marciano marciano: marcianos) {

			if(marciano.rect.overlaps(nave.rect)) {

				nave.quitarvida();
				marcianos.removeValue(marciano,true);

			}
		}

		for(Roca roca: rocas)
			if(roca.rect.overlaps(nave.rect))
				System.exit(0);

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
	}

	private void pintar() {
		Gdx.gl.glClearColor(1, 1, 1, 1);
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
	public void dispose () {
		batch.dispose();

		nave.getTextura().dispose();

		for(Roca roca: rocas)
			roca.getTextura().dispose();

		for(Marciano marciano: marcianos)
			marciano.getTextura().dispose();
	}
}
