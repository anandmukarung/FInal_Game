package a3;

import tage.*;
import tage.input.action.AbstractInputAction;
import tage.shapes.AnimatedShape;
import net.java.games.input.Event;
import org.joml.*;

public class ForwardAction extends AbstractInputAction{
	private MyGame game;
	private GameObject player;
	private AnimatedShape playerShape;
	private Vector3f fwd, loc, newLocation;
	private Vector4f fwdDirection;

	public ForwardAction(MyGame g){	game = g;
	}

	@Override
	public void performAction(float time, Event e){	
		playerShape = game.getPlayerShape();
		playerShape.playAnimation("WALK", 0.5f, AnimatedShape.EndType.LOOP, 0);
    	player = game.getPlayer();
		fwd = player.getWorldForwardVector();
		loc = player.getWorldLocation();
		newLocation = loc.add(fwd.mul(.08f + game.getSpeed()));
		player.setLocalLocation(newLocation);
	}
}