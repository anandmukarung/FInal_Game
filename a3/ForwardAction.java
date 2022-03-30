package a3;

import tage.*;
import tage.input.action.AbstractInputAction;
import net.java.games.input.Event;
import org.joml.*;

public class ForwardAction extends AbstractInputAction{
	private MyGame game;
	private GameObject dolphin;
	private Vector3f fwd, loc, newLocation;
	private Vector4f fwdDirection;

	public ForwardAction(MyGame g){	game = g;
	}

	@Override
	public void performAction(float time, Event e){	
    	dolphin = game.getDolphin();
		fwd = dolphin.getWorldForwardVector();
		loc = dolphin.getWorldLocation();
		newLocation = loc.add(fwd.mul(.08f + game.getSpeed()));
		dolphin.setLocalLocation(newLocation);
	}
}