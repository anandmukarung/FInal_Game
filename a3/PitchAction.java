package a3;

import tage.*;
import tage.input.action.AbstractInputAction;
import net.java.games.input.Event;

import javax.swing.text.DefaultStyledDocument.ElementSpec;

import org.joml.*;

public class PitchAction extends AbstractInputAction
{
	private MyGame game;
	private GameObject player;
	private Vector3f cameraLocationOnDismount;
	private Camera camera;
	private Vector3f loc;
	private Vector3f fwd;
    private Vector3f newLocation;
	private char key = 'k';

	public PitchAction(MyGame g, String gpName){	
		game = g;
	}
	public PitchAction(MyGame g, char key){
		game = g;
		this.key = key;
	}
	@Override
	public void performAction(float time, Event e){
		player = game.getPlayer();
		if(key == 'u'){
			player.pitch('d');
			return;
		}
		else if(key == 'd'){
			player.pitch('d');
		}
		float keyValue = e.getValue();
		if (keyValue > -.2 && keyValue < .2) return;
		if(e.getValue() < +.5){
			player.pitch('u');
        }
		else if(e.getValue() > -.5){
			player.pitch('d');
        }	
	}
}