package a3; 

import tage.*;
import tage.input.action.AbstractInputAction;
import net.java.games.input.Event;

import javax.swing.text.DefaultStyledDocument.ElementSpec;

import org.joml.*;

public class PitchAction extends AbstractInputAction
{
	private MyGame game;
	private GameObject dolphin;
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

		if(key == 'u'){
			if(game.getMount()){
				dolphin.pitch('u');
			}
			else{
				game.getEngine().getRenderSystem().getViewport("MAIN").getCamera().pitch('u');
			}
			return;
		}
		else if(key == 'd'){
			if(game.getMount()){
				dolphin.pitch('d');
			}
			else{
				game.getEngine().getRenderSystem().getViewport("MAIN").getCamera().pitch('d');
			}
			return;
		}
		camera = game.getEngine().getRenderSystem().getViewport("MAIN").getCamera();
		dolphin = game.getDolphin();
		float keyValue = e.getValue();
		if (keyValue > -.2 && keyValue < .2) return;
		if(e.getValue() < +.5){
            if(game.getMount()){
				dolphin.pitch('u');
			}
			else{
				game.getEngine().getRenderSystem().getViewport("MAIN").getCamera().pitch('u');
			}
        }
			
		else if(e.getValue() > -.5){
            if(game.getMount()){
				dolphin.pitch('d');
			}
			else{
				game.getEngine().getRenderSystem().getViewport("MAIN").getCamera().pitch('d');
			}
        }	
	}
}