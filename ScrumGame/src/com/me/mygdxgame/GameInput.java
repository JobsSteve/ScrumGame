package com.me.mygdxgame;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;

public class GameInput implements InputProcessor {
	MainGame game;
	boolean touching;
	float timeTouching;
	Vector2 startTouchPoint;
	Vector2 previousTouchPoint;
	
	public GameInput(MainGame game) {
		this.game = game;
	}
	public void update(float deltaTime) {
		updateMovement(deltaTime);
	}
	public void updateMovement(float deltaTime) {
		if(!touching)
			return;
		
		Drawer drawer = game.getMapDrawer();
		Vector2 movement = startTouchPoint.cpy().sub(previousTouchPoint);
		movement.x /= -drawer.getWidth( );
		movement.y /= drawer.getHeight( );
		
		final int MAX_TPS = 40; //Tiles per second
		movement.mul(MAX_TPS * deltaTime);		
		
		drawer.moveView(movement);
	}
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		touching = true;
		startTouchPoint = new Vector2(screenX, screenY);
		previousTouchPoint = new Vector2(screenX, screenY);
		return false;
	}
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		touching = false;
		startTouchPoint = new Vector2(screenX, screenY);
		previousTouchPoint = new Vector2(screenX, screenY);
		return false;
	}
	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		previousTouchPoint = new Vector2(screenX, screenY);
		return false;
	}
	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean keyDown(int keycode) {
		// Nothing to do...
		return false;
	}
	@Override
	public boolean keyUp(int keycode) {
		// Nothing to do...
		return false;
	}
	@Override
	public boolean keyTyped(char character) {
		// Nothing to do...
		return false;
	}
}