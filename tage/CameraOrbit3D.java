package tage;

import tage.*;
import tage.input.*;
import tage.input.action.AbstractInputAction;
import net.java.games.input.Event;
import org.joml.*;

import a3.MyGame;

import java.lang.Math;
import java.util.ArrayList;
/**
 * 3DOrbit Class for 3D orbit
 * @author John Geronimo 
 * @version 1.0
 * @since 1.0
 */
public class CameraOrbit3D
{	private Engine engine;
	private Camera camera;		//the camera being controlled
	private GameObject avatar;	//the target avatar the camera looks at
	private float cameraAzimuth;	//rotation of camera around target Y axis
	private float cameraElevation;	//elevation of camera above target
	private float cameraRadius;	//distance between camera and target
	private MyGame game;
	/** 
	 * consturctor for CameraOrbit3D
	 * @param cam Camera
	 * @param av Dolphin
	 * @param gpName gamepad name
	 * @param e engine
	 * @param g game
	 * @param kbName keyboard
	 * 
	*/
	public CameraOrbit3D(Camera cam, GameObject av, Engine e, MyGame g)
	{	game = g;
		engine = e;
		camera = cam;
		avatar = av;
		cameraAzimuth = 0.0f;		// start from BEHIND and ABOVE the target
		cameraElevation = 20.0f;	// elevation is in degrees
		cameraRadius = 2.0f;		// distance from camera to avatar
		setupInputs();
		updateCameraPosition();
	}
	/**
	 * sets up gamepad inputs and keyboard inputs for the 3D orbit camera
	 * @param gp gamepad
	 * @param kb keyboard
	 */
	private void setupInputs(){	
		OrbitAzimuthAction azmAction = new OrbitAzimuthAction();
		OrbitRadiusAction radiusAction = new OrbitRadiusAction();
		OrbitElevationAction elevationAction = new OrbitElevationAction();
		InputManager im = engine.getInputManager();
		ArrayList<net.java.games.input.Controller> controllers = im.getControllers();
		for(net.java.games.input.Controller gp : controllers){
			if(gp.getType() == net.java.games.input.Controller.Type.GAMEPAD){
			im.associateAction(gp,
				net.java.games.input.Component.Identifier.Axis.RX,
				azmAction, InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
			im.associateAction(gp,
				net.java.games.input.Component.Identifier.Axis.RY,
				radiusAction, InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
			im.associateAction(gp,
				net.java.games.input.Component.Identifier.Axis.Y,
				elevationAction, InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
			}
		}
		for(net.java.games.input.Controller kb : controllers){
			if(kb.getType() == net.java.games.input.Controller.Type.KEYBOARD){
			im.associateAction(kb,
				net.java.games.input.Component.Identifier.Key.UP,
				elevationAction, InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
			im.associateAction(kb,
				net.java.games.input.Component.Identifier.Key.DOWN,
				elevationAction, InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
			im.associateAction(kb,
				net.java.games.input.Component.Identifier.Key.RIGHT,
				radiusAction, InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
			im.associateAction(kb,
				net.java.games.input.Component.Identifier.Key.LEFT,
				radiusAction, InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
			im.associateAction(kb,
				net.java.games.input.Component.Identifier.Key.P,
				azmAction, InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
			im.associateAction(kb,
				net.java.games.input.Component.Identifier.Key.L,
				azmAction, InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);	
			}
		}
	}

	// Updates the camera position by computing its azimuth, elevation, and distance 
	// relative to the target in spherical coordinates, then converting those spherical 
	// coords to world Cartesian coordinates and setting the camera position from that.

	public void updateCameraPosition()
	{	Vector3f avatarRot = avatar.getWorldForwardVector();
		double avatarAngle = Math.toDegrees((double)avatarRot.angleSigned(new Vector3f(0,0,-1), new Vector3f(0,1,0)));
		float totalAz = cameraAzimuth - (float)avatarAngle;
		double theta = Math.toRadians(totalAz);	// rotation around target
		double phi = Math.toRadians(cameraElevation);	// altitude angle
		float x = cameraRadius * (float)(Math.cos(phi) * Math.sin(theta));
		float y = cameraRadius * (float)(Math.sin(phi));
		float z = cameraRadius * (float)(Math.cos(phi) * Math.cos(theta));
		if(!game.groundCollision(new Vector3f(x,y,z).add(avatar.getWorldLocation()))){
			camera.setLocation(new Vector3f(x,y,z).add(avatar.getWorldLocation()));
		}
		camera.lookAt(avatar);
	}
	/**
	 * private class for the rotation
	 */
	private class OrbitAzimuthAction extends AbstractInputAction
	{	public void performAction(float time, Event event)
		{	float rotAmount;
			if (event.getValue() < -0.2 || event.getValue() == 37)
			{	rotAmount=-0.2f;
			}
			else
			{	if (event.getValue() > 0.2 || event.getValue() == 39)
				{	rotAmount=0.2f;
				}
				else
				{	rotAmount=0.0f;
				}
			}
			cameraAzimuth += rotAmount;
			cameraAzimuth = cameraAzimuth % 360;
			updateCameraPosition();
		}
	}
	/**
	 * private class for radius action 
	 */
	private class OrbitRadiusAction extends AbstractInputAction{
		public void performAction(float time, Event event)
		{	float radiusAmount;
			if (event.getValue() < -0.2 || event.getValue() == 80)
			{	radiusAmount=-0.2f;
			}
			else
			{	if (event.getValue() > 0.2 || event.getValue() == 76)
				{	radiusAmount=0.2f;
				}
				else
				{	radiusAmount=0.0f;
				}
			}
			cameraRadius += radiusAmount;
			cameraRadius = cameraRadius % 360;
			updateCameraPosition();
		}
	}
	/**
	 * private class for elveation
	 */
	private class OrbitElevationAction extends AbstractInputAction{
		public void performAction(float time, Event event)
		{	float elevationAmount;
			if (event.getValue() < -0.2 || event.getValue() == 38)
			{	elevationAmount=-0.2f;
			}
			else
			{	if (event.getValue() > 0.2 || event.getValue() == 40)
				{	elevationAmount=0.2f;
				}
				else
				{	elevationAmount=0.0f;
				}
			}
			cameraElevation += elevationAmount;
			cameraElevation = cameraElevation % 360;
			updateCameraPosition();
		}
	}
}