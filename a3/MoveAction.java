package a3;

import tage.*;
import tage.input.action.AbstractInputAction;
import net.java.games.input.Event;
import org.joml.*;
/**
 * moveAction for the gamepad input
 * @author John Geronimo 
 * @version 1.1
 * @since 1.0
 */
public class MoveAction extends AbstractInputAction
{
	private MyGame game;
	private GameObject dolphin;
	Vector3f loc;
    Vector3f fwd;
    Vector3f newLocation;
    Vector3f cameraLocationOnDismount;
	private char key = 'k';
	private String gpName;
	private Camera camera;
	/**
	 * contructor for the gamepad abrsract action
	 * @param g game obect
	 */
	public MoveAction(MyGame g, String gpName, Camera camera){	
		game = g;
		this.gpName = gpName;
		this.camera = camera;
	}
	/**
	 * constructor for keyboard abstract action
	 * @param g game obeject
	 * @param key keyabord for input
	 */
	public MoveAction(MyGame g, char key){
		game = g;
		this.key = key;
	}
	/**
	 * when the action is performed it moves the objects
	 * according to the postion of the gamepad
	 */
	@Override
	public void performAction(float time, Event e){
		fwd = dolphin.getWorldForwardVector();
		loc = dolphin.getWorldLocation();
		newLocation = loc.add(fwd.mul(.08f + game.getSpeed()));
		if(!game.groundCollision(newLocation)){
            dolphin.setLocalLocation(newLocation);
            game.getCamera().setLocation(newLocation);
        }
	}
}