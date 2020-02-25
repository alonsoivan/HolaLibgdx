package com.ivn.holalibgdx;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.ivn.holalibgdx.screens.PantallaJuego;
import com.ivn.holalibgdx.screens.PantallaMenuPrincipal;

public class Aplicacion extends Game {

	@Override
	public void create () {
		((Game) Gdx.app.getApplicationListener()).setScreen(new PantallaMenuPrincipal());
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		super.dispose();
	}
}
