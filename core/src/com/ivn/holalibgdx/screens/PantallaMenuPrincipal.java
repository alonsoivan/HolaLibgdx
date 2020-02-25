package com.ivn.holalibgdx.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisTextButton;

public class PantallaMenuPrincipal implements Screen {

    Stage stage;

    @Override
    public void show() {

        if (!VisUI.isLoaded())
            VisUI.load();

        stage = new Stage();

        VisTable table = new VisTable(true);
        table.setFillParent(true);
        stage.addActor(table);

            VisTextButton playButton = new VisTextButton("JUGAR");
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new PantallaJuego());
                dispose();
            }
        });

        VisTextButton configButton = new VisTextButton("CONFIGURACIÓN");
        configButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Ir a la pantalla de configuración
                dispose();
            }
        });

        VisTextButton quitButton = new VisTextButton("SALIR");
        quitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                VisUI.dispose();
                // Salir del juego
            }
        });

        VisLabel aboutLabel = new VisLabel("Hola libGDX\n(c) IVÁN ALONSO DÍAZ 2020");

        // Añade filas a la tabla y añade los componentes
        table.row();
        table.add(playButton).center().width(200).height(100).pad(5);
        table.row();
        table.add(configButton).center().width(200).height(50).pad(5);
        table.row();
        table.add(quitButton).center().width(200).height(50).pad(5);
        table.row();
        table.add(aboutLabel).left().width(200).height(20).pad(5);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float dt) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Pinta la UI en la pantalla
        stage.act(dt);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        // Redimensiona la escena al redimensionar la ventana del juego
        stage.getViewport().update(width, height);
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
    public void dispose() {
        // Libera los recursos de la escena
        stage.dispose();
    }

}