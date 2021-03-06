package a3;

import tage.*;
import tage.input.action.AbstractInputAction;
import net.java.games.input.Event;

import javax.swing.text.DefaultStyledDocument.ElementSpec;

import org.joml.*;
/**
 * TurnRightAction for the gamepad input
 * @author John Geronimo 
 * @version 1.0
 * @since 1.0
 */
public class TurnRightAction extends AbstractInputAction{
	private MyGame game;
	private GameObject player;
	private char key = 'k';
	/**
	 * constuctor for gamepad input
	 * @param g game object
	 */
	public TurnRightAction(MyGame g){	
		game = g;
	}
	/**
	 * constructor for keyabord input
	 * @param g game object
	 * @param key key input
	 */
	public TurnRightAction(MyGame g, char key){	
		game = g;
		this.key = key;
	}
	/**
	 * if the abstract action was form a keyabord it will treat it like a keyboard input
	 * otherwise acts like its a gamepad
	 */
	@Override
	public void performAction(float time, Event e){	
		player = game.getPlayer();
		player.yaw('r');
	}
}