package a3;

import tage.*;
import tage.input.action.AbstractInputAction;
import net.java.games.input.Event;
import org.joml.*;
/**
 * abstract action for backward action
 * @author John Geronimo 
 * @version 1.0
 * @since 1.0
 */
public class BackwardAction extends AbstractInputAction{
	private MyGame game;
	private GameObject dolphin;
	private Vector3f fwd, loc, newLocation;
	private Vector4f fwdDirection;
	/**
	 * constructor for backward action
	 * 
	 * */
	public BackwardAction(MyGame g){	game = g;
	}
	/**
	 * performs move backwards
	 */
	@Override
	public void performAction(float time, Event e){	
    	dolphin = game.getDolphin();
		fwd = dolphin.getWorldForwardVector();
		loc = dolphin.getWorldLocation();
		newLocation = loc.add(fwd.mul(-.08f + game.getSpeed()));
		dolphin.setLocalLocation(newLocation);
	}
}