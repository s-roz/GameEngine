package engineTester;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import entity.Camera;
import entity.Entity;
import models.RawModel;
import models.TexturedModel;
import renderEngine.*;
import shaders.StaticShader;
import textures.ModelTexture;

public class MainGameLoop {

	public static void main(String[] args) {

		DisplayManager.createDisplay();
		
		Loader loader = new Loader();
		StaticShader shader = new StaticShader();
		Renderer renderer = new Renderer(shader);

		
		float[] vertices = {
				-0.5f, 0.5f, 0f,   //V0
				-0.5f, -0.5f, 0f,  //V1
				0.5f, -0.5f, 0f,   //V2
				0.5f, 0.5f, 0f     //V3
		};
		
		int[] indices = {
			0,1,3, //Top left tri (0,1,3)
			3,1,2  //Bottom right tri (3,1,2)
		};
		
		float[] textureCoords = {
				0,0, //V0
				0,1, //V1
				1,1, //V2
				1,0  //V3
		};
		
		RawModel model = loader.loadToVAO(vertices,textureCoords,indices);
		
		TexturedModel staticModel = new TexturedModel(model,new ModelTexture(loader.loadTexture("Wheel")));
		
		Entity entity = new Entity(staticModel, new Vector3f(0,0,-1),0,0,0,1);
		
		Camera camera = new Camera();
		
		
		while(!Display.isCloseRequested()) {
			renderer.prepare();
			camera.move();
			entity.increasePosition(0, 0, 0);
			entity.increaseRotation(0, 0, .5f);
			shader.start();
			shader.loadViewMatrix(camera);
			renderer.render(entity,shader);
			shader.stop();
			DisplayManager.updateDisplay();
			
		}
		
		shader.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();
		
	}

}