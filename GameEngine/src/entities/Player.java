package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

import models.TexturedModel;
import renderEngine.DisplayManager;
import terrains.Terrain;

public class Player extends Entity{
	
	private static final float RUN_SPEED = 2;
	private static final float TURN_SPEED = 160;
	private static final float GRAVITY = -9.84f;
	private static final float JUMP_POWER = 3;
	
	private static final float TERRAIN_HEIGHT = 0;
	
	private float currentSpeed = 0;
	private float currentTurnSpeed = 0;
	private float upwardsSpeed = 0;
	
	private boolean isAirborne = false;
	private boolean isRunning = false;
	

	public Player(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
		super(model, position, rotX, rotY, rotZ, scale);
		
	}
	
	public void move(Terrain terrain) {
		checkInputs();
		super.increaseRotation(0, currentTurnSpeed * DisplayManager.getFrameTimeSeconds(), 0);
		float distance = currentSpeed * DisplayManager.getFrameTimeSeconds();
		float dx = (float) (distance * Math.sin(Math.toRadians(super.getRotY())));
		float dz = (float) (distance * Math.cos(Math.toRadians(super.getRotY())));
		super.increasePosition(dx, 0, dz);
		upwardsSpeed += GRAVITY * DisplayManager.getFrameTimeSeconds();
		super.increasePosition(0, upwardsSpeed * DisplayManager.getFrameTimeSeconds(), 0);
		float terrainHeight = terrain.getHeightOfTerrain(super.getPosition().x, super.getPosition().z);
		if(super.getPosition().y<terrainHeight) {
			upwardsSpeed = 0;
			isAirborne = false;
			super.getPosition().y = terrainHeight;
		}
	}
	
	private void jump() {
		if(!isAirborne) {
		this.upwardsSpeed = JUMP_POWER;
		isAirborne = true;
		}
	}
	
	public void checkInputs() {
		if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
			isRunning = true;
		}else {
			isRunning = false;
		}
		if(isRunning == true) {
			if(Keyboard.isKeyDown(Keyboard.KEY_W)) {
				this.currentSpeed = RUN_SPEED*5;
			}else if(Keyboard.isKeyDown(Keyboard.KEY_S)) {
				this.currentSpeed = -RUN_SPEED*5;
			}else {
				this.currentSpeed = 0;
			}
		}else {
			if(Keyboard.isKeyDown(Keyboard.KEY_W)) {
				this.currentSpeed = RUN_SPEED;
			}else if(Keyboard.isKeyDown(Keyboard.KEY_S)) {
				this.currentSpeed = -RUN_SPEED;
			}else {
				this.currentSpeed = 0;
			}
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_A)) {
			this.currentTurnSpeed = TURN_SPEED;
		}else if(Keyboard.isKeyDown(Keyboard.KEY_D)) {
			this.currentTurnSpeed = -TURN_SPEED;
		}else {
			this.currentTurnSpeed = 0;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
			jump();
		}
	}

}
