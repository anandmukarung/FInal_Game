package a3; 

import tage.*;
import tage.input.InputManager;
import tage.shapes.*;
import tage.nodeControllers.*;
import tage.CameraOrbit3D;

import java.lang.Math;
import java.lang.ModuleLayer.Controller;
import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import org.joml.*;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import net.java.games.input.Component.Identifier.Axis;
import net.java.games.input.Component;
import net.java.games.input.Keyboard;
/**
 * Main class for a2, extenentsions of professor Gordons 
 * code given in canvas
 * @author John Geronimo 
 * @version 1.2
 * @since 1.0
 */
public class MyGame extends VariableFrameRateGame{
    /**
     * initlizes all the variables needed for gameworld
     */
	private static Engine engine;
	public static Engine getEngine() { return engine; }

	private double startTime;
    private GameObject dolphin;
    private ObjShape dolphinShape;
    private TextureImage dolphinTexture;
    private Light light1;

    private GameObject prize1;
    private GameObject prize2;
    private GameObject prize3;
    private ObjShape prizeShape;
    private TextureImage prizeTexture;
    private GameObject crabCatcher;
    private ObjShape crabCatcherShape;
    private TextureImage crabCatcherTexture;

    private GameObject ground;
    private Plane groundPlane;
    private TextureImage groundTexture;

    private Vector3f center = new Vector3f(0,0,0);
    private Vector3f xBorder = new Vector3f(40,0,0);
    private Vector3f yBorder = new Vector3f(0,40,0);
    private Vector3f zBorder = new Vector3f(0,0,40);

    private ObjShape xAxsisShape;
    private ObjShape yAxsisShape;
    private ObjShape zAxsisShape;

    private GameObject xAxsis;
    private GameObject yAxsis;
    private GameObject zAxsis;
    
    private GameObject bomb;
    private TextureImage bombTexture;
    private ObjShape bombShape;

    private GameObject crate;
    private TextureImage crateTexture;
    private ObjShape crateShape;
    
    private Random rand = new Random();
    private boolean mount = true;

    private boolean invisbleAxis = false;

    private Camera camera;
    private Camera HudCamera;
    private CameraOrbit3D orbit;
    private int score = 0;

    private long previousTime = 0;
	private long elaspedTime = 50;
	private long currentTime = 0;

    private InputManager inputManager;

    private float speed = 0.0f;
    private float zoomPan = 0.0f;
	private NodeController rc;
    private NodeController rumbleController;
    private NodeController torpedoController;

    private int fluffyClouds;
    private int lakeIslands;

    private File scriptFile1; 
    private File scriptFile2;
    private File scriptFile3;

	private long fileLastModifiedTime = 0;

    ScriptEngine jsEngine;

    private Matrix4f behindDolphin = (new Matrix4f()).scaling(0.2f);

	public MyGame() { super(); }
    /**
     * main method
     * @param args
     */
	public static void main(String[] args){
        MyGame game = new MyGame();
		engine = new Engine(game);
		game.initializeSystem();
		game.game_loop();
	}
    /**
     * loads all the shapes into the shapes variables
     */
	@Override
	public void loadShapes(){
        dolphinShape = new ImportedModel("dolphinHighPoly.obj");
        prizeShape = new Cube();
        xAxsisShape = new Line(center, xBorder);
        yAxsisShape = new Line(center, yBorder);
        zAxsisShape = new Line(center, zBorder);
        bombShape =  new Torpedo();
        crabCatcherShape = new Cube();
        groundPlane = new Plane();
        crateShape = new Cube();
	}
    /**
     * loads all the textures into the texture variables
     */
	@Override
	public void loadTextures(){
        dolphinTexture = new TextureImage("Dolphin_HighPolyUV.png");
        prizeTexture = new TextureImage("present.png");
        bombTexture = new TextureImage("bombTexture.png");
        crabCatcherTexture = new TextureImage("crabcatcher.png");
        groundTexture = new TextureImage("seafloor.png");
        crateTexture = new TextureImage("crate.png");
    }
    /**
     * builds the objects in the game 
     * rigs the translation, shape, and textures to all the
     * objects in the game
     */
	@Override
	public void buildObjects(){

		Matrix4f initialTranslation, initalScale;
        dolphin = new GameObject(GameObject.root(), dolphinShape, dolphinTexture);

        crate = new GameObject(GameObject.root(), crateShape, crateTexture);
        initialTranslation = (new Matrix4f().translation(0,0,0));
        initalScale = (new Matrix4f()).scaling(.2f);
        crate.setLocalTranslation(initialTranslation);
        crate.setLocalScale(initalScale);

        ground = new GameObject(GameObject.root(), groundPlane, groundTexture);
        initialTranslation = (new Matrix4f().translation(0,0,0));
        initalScale = (new Matrix4f()).scaling(100.f);
        ground.setLocalTranslation(initialTranslation);
        ground.setLocalScale(initalScale);

        prize1 = new GameObject(GameObject.root(), prizeShape, prizeTexture);
        initialTranslation= (new Matrix4f().translation((float)(1 + (10.0 - 1) * 
            rand.nextFloat()),1,(float)(1 + (10.0 - 1) * rand.nextFloat())));
        initalScale = (new Matrix4f()).scaling((float)(.1 + (2.0 - .1) * rand.nextFloat()));
        prize1.setLocalTranslation(initialTranslation);
        prize1.setLocalScale(initalScale);
        
        prize2 = new GameObject(GameObject.root(), prizeShape, prizeTexture);
        initialTranslation= (new Matrix4f().translation((float)(2 + (10.0 - 2) * 
            rand.nextFloat()), 1 ,(float)(2 + (10.0 - 2) * rand.nextFloat())));
        initalScale = (new Matrix4f()).scaling((float)(.1 + (2.0 - .1) * rand.nextFloat()));
        prize2.setLocalTranslation(initialTranslation);
        prize2.setLocalScale(initalScale);
        
        prize3 = new GameObject(GameObject.root(), prizeShape, prizeTexture);
        initialTranslation= (new Matrix4f().translation((float)(2 + (10.0 - 2) * 
            rand.nextFloat()),1,(float)(2 + (10.0 - 2) * rand.nextFloat())));
        initalScale = (new Matrix4f()).scaling((float)(.1 + (2.0 - .1) *  rand.nextFloat()));
        prize3.setLocalTranslation(initialTranslation);
        prize3.setLocalScale(initalScale);
        
        xAxsis = new GameObject(GameObject.root(), xAxsisShape, prizeTexture );
        yAxsis = new GameObject(GameObject.root(), yAxsisShape, prizeTexture );
        zAxsis = new GameObject(GameObject.root(), zAxsisShape, prizeTexture );
        //bomb translation is a little different than the prizes so the game doesn't start
        // in a gameover
        bomb = new GameObject(GameObject.root(), bombShape, bombTexture);
        initialTranslation= (new Matrix4f().translation((float)(2.0 + (10.0 - 2.0) * 
            rand.nextFloat()),1,(float)(2.0 + (10.0 - 2.0) * rand.nextFloat())));
        initalScale = (new Matrix4f()).scaling((float)(.1 + (1.0 - .1) *  rand.nextFloat()));
        bomb.setLocalTranslation(initialTranslation);
        bomb.setLocalScale(initalScale);

        crabCatcher = new GameObject(GameObject.root(), crabCatcherShape, crabCatcherTexture);
        initialTranslation= (new Matrix4f().translation((float)(2 + (10.0 - 2) * 
            rand.nextFloat()),1,(float)(2 + (10.0 - 2) * rand.nextFloat())));
        initalScale = (new Matrix4f()).scaling((float)(.1 + (1.0 - .1) *  rand.nextFloat()));
        crabCatcher.setLocalTranslation(initialTranslation);
        crabCatcher.setLocalScale(initalScale);
	}
    @Override
    public void loadSkyBoxes(){
        fluffyClouds = (engine.getSceneGraph()).loadCubeMap("fluffyClouds");
		lakeIslands = (engine.getSceneGraph()).loadCubeMap("lakeIslands");
		(engine.getSceneGraph()).setActiveSkyBoxTexture(fluffyClouds);
		(engine.getSceneGraph()).setSkyBoxEnabled(true);
    }
    /**
     * initlizes the games objects and inputs
     */
	@Override
	public void initializeGame(){
        startTime = System.currentTimeMillis();
		(engine.getRenderSystem()).setWindowDimensions(1900,1000);
        //creating jsEngine object
        ScriptEngineManager factory = new ScriptEngineManager();
		java.util.List<ScriptEngineFactory> list = factory.getEngineFactories();
		jsEngine = factory.getEngineByName("js");	
        //scriptfiles beiong read
        scriptFile1 = new File("assets/scripts/InitParams.js");
		this.runScript(scriptFile1);
        //initlize has to happen in initlize game, so translations, scaling, etc has to be done here
        dolphin.setLocalTranslation(new Matrix4f().translation((Vector3fc) (jsEngine.get("dolphinTranslate"))));
        //use these as templates for init
        dolphin.setLocalScale(new Matrix4f().scaling(((Double)(jsEngine.get("dolphinScale"))).floatValue()));

        scriptFile2 = new File("assets/scripts/CreateLight.js");
		this.runScript(scriptFile2);
        //object light
		(engine.getSceneGraph()).addLight((Light)jsEngine.get("light"));

        scriptFile3 = new File("assets/scripts/UpdateLightColor.js");
		this.runScript(scriptFile3);

        rc = new RotationController(engine, new Vector3f(0,1,0), ((Double)(jsEngine.get("rumble"))).floatValue());
        rumbleController = new RumbleController(engine, new Vector3f(0,1,0),((Double)(jsEngine.get("rumble"))).floatValue());
        torpedoController = new TorpedoController(engine, new Vector3f(0,1,0),((Double)(jsEngine.get("rumble"))).floatValue());
        //adding node controllers
        rc.addTarget(prize1);
        rc.addTarget(prize2);
        rc.addTarget(prize3);
        rumbleController.addTarget(crabCatcher);
        torpedoController.addTarget(bomb);
        torpedoController.enable();
        rumbleController.enable();
        rc.enable();
        (engine.getSceneGraph()).addNodeController(torpedoController);
        (engine.getSceneGraph()).addNodeController(rumbleController);
        (engine.getSceneGraph()).addNodeController(rc);



		//----------------- adding lights -----------------
		Light.setGlobalAmbient(0.5f, 0.5f, 0.5f);

		light1 = new Light();
		light1.setLocation(new Vector3f(2.0f, 3.0f, 2.7f));
		(engine.getSceneGraph()).addLight(light1);



		// ------------- positioning the camera -------------
		(engine.getRenderSystem().getViewport("LEFT").getCamera()).setLocation(new Vector3f(0,1,5));
        camera = engine.getRenderSystem().getViewport("LEFT").getCamera();
        HudCamera = engine.getRenderSystem().getViewport("RIGHT").getCamera();

        //adding input for gamepad
        inputManager = engine.getInputManager();
        String gpName = inputManager.getFirstGamepadName();
        String kbName = inputManager.getKeyboardName();
        TurnRightAction turnRightAction = new TurnRightAction(this);
        ForwardAction forwardAction = new ForwardAction(this);
        BackwardAction backwardAction = new BackwardAction(this);
        TurnLeftAction turnLeftAction = new TurnLeftAction(this);
        InvisibleAxis invisibleAxis = new InvisibleAxis(this);
        ViewPort viewPort1 = new ViewPort(this, 'u'); 
        ViewPort viewPort2 = new ViewPort(this, 'd');
        ArrayList<net.java.games.input.Controller> controllers = inputManager.getControllers();
        for (net.java.games.input.Controller gp : controllers){
            if (gp.getType() == net.java.games.input.Controller.Type.GAMEPAD){
                inputManager.associateAction(gp,
                    net.java.games.input.Component.Identifier.Button._3,
                    forwardAction, InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
                inputManager.associateAction(gp,
                    net.java.games.input.Component.Identifier.Button._0,
                    backwardAction, InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
                inputManager.associateAction(gp,
                    net.java.games.input.Component.Identifier.Button._1,
                    turnRightAction, InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
                inputManager.associateAction(gp,
                    net.java.games.input.Component.Identifier.Button._2,
                    turnLeftAction, InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
                inputManager.associateAction(gp,
                    net.java.games.input.Component.Identifier.Button._5,
                    invisibleAxis, InputManager.INPUT_ACTION_TYPE.ON_PRESS_ONLY);
                inputManager.associateAction(gp,
                    net.java.games.input.Component.Identifier.Button._8,
                    viewPort1, InputManager.INPUT_ACTION_TYPE.ON_PRESS_ONLY);
                inputManager.associateAction(gp,
                    net.java.games.input.Component.Identifier.Button._9,
                    viewPort2, InputManager.INPUT_ACTION_TYPE.ON_PRESS_ONLY);
                }
        }
        for (net.java.games.input.Controller kb : controllers){
            if (kb.getType() == net.java.games.input.Controller.Type.KEYBOARD){
                inputManager.associateAction(kb, Component.Identifier.Key.W,
                    forwardAction, InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
                inputManager.associateAction(kb,
                    net.java.games.input.Component.Identifier.Key.S,
                    backwardAction, InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
                inputManager.associateAction(kb,
                    net.java.games.input.Component.Identifier.Key.A,
                    turnLeftAction, InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
                inputManager.associateAction(kb,
                    net.java.games.input.Component.Identifier.Key.D,
                    turnRightAction, InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
                inputManager.associateAction(kb,
                    net.java.games.input.Component.Identifier.Key.Z,
                    invisibleAxis, InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
                inputManager.associateAction(kb,
                    net.java.games.input.Component.Identifier.Key.X,
                    viewPort1, InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
                inputManager.associateAction(kb,
                    net.java.games.input.Component.Identifier.Key.C,
                    viewPort2, InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
            }
        }
        orbit = new CameraOrbit3D(camera, dolphin, engine, this);
    }
    /**
     *creating the viewports 
     * 
     */
    @Override
	public void createViewports(){	
        (engine.getRenderSystem()).addViewport("LEFT",0,0,1f,1f);
		(engine.getRenderSystem()).addViewport("RIGHT",.75f,0,.25f,.25f);

		Viewport rightVp = (engine.getRenderSystem()).getViewport("RIGHT");
		HudCamera = rightVp.getCamera();

		rightVp.setHasBorder(true);
		rightVp.setBorderWidth(4);
		rightVp.setBorderColor(0.0f, 1.0f, 0.0f);

		HudCamera.setLocation(new Vector3f(0,1.5f,0));
		HudCamera.setU(new Vector3f(1,0,0));
		HudCamera.setV(new Vector3f(0,0,-1));
		HudCamera.setN(new Vector3f(0,-1,0));
	}
    /**
     * updates the game world,
     * updates inputs
     * collisions
     * and the time and score to the HUD
     */
	@Override
	public void update(){
        currentTime = System.currentTimeMillis();
        elaspedTime = System.currentTimeMillis() - previousTime;
        previousTime = System.currentTimeMillis();
		if(currentTime - elaspedTime < previousTime){
		    int elapsTimeSec = Math.round((float)(System.currentTimeMillis()-startTime)/1000.0f);
            String elapsTimeStr = Integer.toString(elapsTimeSec);
            String dispStr1 = "Time = " + elapsTimeStr + "Score = " + score;
            String dispStr2 = "dolphin " + dolphin.getWorldLocation() + "camera" + camera.getLocation();
            Vector3f hud1Color = new Vector3f(1,1,1);
            Vector3f hud2Color = new Vector3f(0,0,1);
            Vector3f hudCameraVector = dolphin.getLocalLocation();
            (engine.getHUDmanager()).setHUD1(dispStr1, hud1Color, 15, 15);
            (engine.getHUDmanager()).setHUD2(dispStr2, hud2Color, 1000, 275);
            hudCameraVector.y = hudCameraVector.y + 2.5f + zoomPan;
            HudCamera.setLocation(hudCameraVector);
            inputManager.update((float)elaspedTime);
            bombDetection();
            prizeDetection();
            propagateTranslation();
            crateCollision();
            if(crabCatcherCollision(crabCatcher)){
                speed = speed + 0.05f;
                crabCatcher.setLocalTranslation( 
                    (new Matrix4f().translation((float)(50 + (100.0 - 50) * rand.nextFloat()), 10000 ,
                        (float)(50 + (100 - 50) * rand.nextFloat()))));

            }
        }
        previousTime = System.currentTimeMillis();
	}
    /**
     * zoomPan method for controller
     * @param zoomPanCommand
     */
    public void alterViewPort(char zoomPanCommand){
        if(zoomPanCommand == 'u'){
            zoomPan = zoomPan + .5f;
        }
        else if(zoomPanCommand == 'd'){
            zoomPan = zoomPan - .5f;
        }
    }
    /**
     * checks to see if the dolphin has any prizes, and sets the new parent and score if that is the case
     */
    private void crateCollision(){
        float crateX = crate.getWorldLocation().x();
        float crateY = crate.getWorldLocation().y();
        float crateZ = crate.getWorldLocation().z();
        if(Math.abs(dolphin.getWorldLocation().x() - crateX)< 0.5f &&  
            Math.abs(dolphin.getWorldLocation().z() - crateZ) < 0.5f){
            //only scores if the dolphin child is prize
            if(prize1.getParent() == dolphin){
                prize1.setParent(bomb);
                prize1.setLocalTranslation(new Matrix4f().translation((float)(1 + (10.0 - 1) * 
                rand.nextFloat()),-1,(float)(1 + (10.0 - 1) * rand.nextFloat())));
                score = score + 10;
            }
            if(prize2.getParent() == dolphin){
                prize2.setParent(bomb);
                prize2.setLocalTranslation(new Matrix4f().translation((float)(1 + (10.0 - 1) * 
                rand.nextFloat()),-1,(float)(1 + (10.0 - 1) * rand.nextFloat())));
                score = score + 10;
            }
            if(prize3.getParent() == dolphin){
                prize3.setParent(bomb);
                prize3.setLocalTranslation(new Matrix4f().translation((float)(1 + (10.0 - 1) * 
                rand.nextFloat()),-1,(float)(1 + (10.0 - 1) * rand.nextFloat())));
                score = score + 10;
            }
        }
    }
    /**
     * detects if the player has touched a bomb
     * The game is over if the player touchs the bomb
     */
    private void bombDetection(){
        float bombX = bomb.getWorldLocation().x();
        float bombY = bomb.getWorldLocation().y();
        float bombZ = bomb.getWorldLocation().z();
        if(Math.abs(dolphin.getWorldLocation().x() - bombX)< 0.5f && 
            Math.abs(dolphin.getWorldLocation().y() - bombY) < 0.5f && 
                Math.abs(dolphin.getWorldLocation().z() - bombZ) < 0.5f){
                    (engine.getHUDmanager()).setHUD2("Game Over!", new Vector3f(0,1,0), 500, 15);
                    System.exit(0);
        }
    }
    /**
     * checks to see if the player has collided with the crab catcher
     * @param crabCatcher gameObject for crab Catcher object
     * @return if the player has collided with the crab catcher
     */
    private boolean crabCatcherCollision(GameObject crabCatcher){
        float crabX = crabCatcher.getWorldLocation().x();
        float crabY = crabCatcher.getWorldLocation().y();
        float crabZ = crabCatcher.getWorldLocation().z();
        if(Math.abs(dolphin.getWorldLocation().x() - crabX)< 1.5f && 
            Math.abs(dolphin.getWorldLocation().y() - crabY) < 1.5f && 
            Math.abs(dolphin.getWorldLocation().z() - crabZ) < 1.5f){
                return true;
        }
        return false;
    }
    /**
     * detects if one of the prizes has a collision with the camera
    */
    private boolean prizeCollision(GameObject prize){
        float prizeX = prize.getWorldLocation().x();
        float prizeY = prize.getWorldLocation().y();
        float prizeZ = prize.getWorldLocation().z();
        if(Math.abs(dolphin.getWorldLocation().x() - prizeX)< 1.5f && 
            Math.abs(dolphin.getWorldLocation().y() - prizeY) < 1.5f && 
            Math.abs(dolphin.getWorldLocation().z()-prizeZ) < 1.5f){
                return true;
        }
        return false;
    }
    /**
     * employs pizeCollision() to return wether or not the camera has touched the present
     * if it does it adds to your score and moves the present to a different area
     */
    private void prizeDetection(){
        if(prizeCollision(prize1)){
            score++;
            prize1.setParent(dolphin);
            prize1.setLocalScale(behindDolphin);
        }
        else if(prizeCollision(prize2)){
            score++;
            prize2.setParent(dolphin);
            prize2.setLocalScale(behindDolphin);
        }
        else if(prizeCollision(prize3)){
            score++;
            prize3.setParent(dolphin);
            prize3.setLocalScale(behindDolphin);
        }
    }
    /**
     * propagates the prizes around the dolphin
     */
    private void propagateTranslation(){
        Vector3f progagatingPresents = dolphin.getWorldLocation();
        float distanceChecker = progagatingPresents.x;
        distanceChecker = (distanceChecker + 0.2f) - distanceChecker;
        if(prize1.getParent() == dolphin){
            if(distanceChecker > 0.1f ){
                return;
            }
            prize1.setLocalLocation(progagatingPresents);
        }
        if(prize2.getParent() == dolphin){
            if(distanceChecker > 0.1f ){
                return;
            }
            prize2.setLocalLocation(progagatingPresents);
        }
        if(prize3.getParent() == dolphin){
            if(distanceChecker > 0.1f ){
                return;
            }
            prize3.setLocalLocation(progagatingPresents);
        }
    }
    /**
     * sets the axis as invisble, really just translates it super far away from the
     * gameworld
     */
    public void invisbleAxis(){
        if(!invisbleAxis){
            invisbleAxis = true;
            xAxsis.setLocalTranslation(new Matrix4f().translation(100f,100f,100f));
            yAxsis.setLocalTranslation(new Matrix4f().translation(100f,100f,100f));
            zAxsis.setLocalTranslation(new Matrix4f().translation(100f,100f,100f));
            return;
        }
        if(invisbleAxis){
            invisbleAxis = false;
            xAxsis.setLocalTranslation(new Matrix4f().translation(0f,0f,0f));
            yAxsis.setLocalTranslation(new Matrix4f().translation(0f,0f,0f));
            zAxsis.setLocalTranslation(new Matrix4f().translation(0f,0f,0f));
            return;
        }
    }
    /**
     * checks to see if the camera is under the ground
     * @param newLocation location of the camera
     * @return whether or not the camera is hitting the ground
     */
    public boolean groundCollision(Vector3f newLocation){
        if(newLocation.y < 0){
            return true;
        }
        return false;
    }
    /**
     * get Dolphin gets Dolphin and returns it
     * @return dolphin
     */
    public GameObject getDolphin(){
        return dolphin;
    }
    /**
     * gets Game Camera and returns it
     * @return camera
     */
    public Camera getCamera(){
        return camera; 
    }
    /**
     * returns wether the character is mounted or not
     * @return mount
     */
    public boolean getMount(){
        return mount;
    }
    /**
     * returns speed
     * @return speed
     */
    public float getSpeed(){
        return speed;
    }
    private void runScript(File scriptFile){
        try{
            FileReader fileReader = new FileReader(scriptFile);
			jsEngine.eval(fileReader);
			fileReader.close();
		}
		catch (FileNotFoundException e1){
            System.out.println(scriptFile + " not found " + e1);
        }
		catch (IOException e2){
            System.out.println("IO problem with " + scriptFile + e2);
        }
		catch (ScriptException e3) {
            System.out.println("ScriptException in " + scriptFile + e3);
        }
		catch (NullPointerException e4){
            System.out.println ("Null ptr exception reading " + scriptFile + e4);
		}
	}
}