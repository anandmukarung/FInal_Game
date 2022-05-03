package a3;

import tage.*;
import tage.input.action.AbstractInputAction;
import net.java.games.input.Event;
import org.joml.*;
/**
 * abstract action for setting invisble axis
 * @author John Geronimo 
 * @version 1.0
 * @since 1.0
 */
public class Jump extends AbstractInputAction{
	private MyGame game;

	public Jump(MyGame g){	
        game = g;

	}
	/**
	 * performs invisble axis
	 */
	@Override
	public void performAction(float time, Event e){	
    	//game.flingWater();
	}
}