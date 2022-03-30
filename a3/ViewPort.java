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
public class ViewPort extends AbstractInputAction{
	private MyGame game;
    private char viewPorts;
	public ViewPort(MyGame g, char viewPorts){	
        game = g;
        this.viewPorts = viewPorts;
	}
	/**
	 * performs invisble axis
	 */
	@Override
	public void performAction(float time, Event e){	
    	game.alterViewPort(viewPorts);
	}
}