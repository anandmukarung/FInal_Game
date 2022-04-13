package a3;

import tage.*;
import tage.input.InputManager;
import tage.input.action.AbstractInputAction;
import tage.shapes.*;
import tage.nodeControllers.*;

import java.lang.Math;
import java.lang.ModuleLayer.Controller;
import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import org.joml.*;


import tage.physics.PhysicsEngine;
import tage.physics.PhysicsObject;
import tage.physics.PhysicsEngineFactory;
import tage.physics.JBullet.*;
import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.collision.dispatch.CollisionObject;
import java.io.*;
import java.util.*;
import java.util.UUID;
import java.net.InetAddress;

import java.net.UnknownHostException;

import org.joml.*;

import tage.CameraOrbit3D;

import net.java.games.input.*;
import net.java.games.input.Component.Identifier.*;
import tage.networking.IGameConnection.ProtocolType;

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
    private Light light1;


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
    
    private GameObject terr;
    private ObjShape terrShape;
    private TextureImage hills;
    private TextureImage trash;

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

    private int fluffyClouds;

    private File scriptFile1; 
    private File scriptFile2;
    private File scriptFile3;

    private TextureImage GrafTexture;
    private GameObject Graffiti;
    private ObjShape graffitiShape;

    private TextureImage streetlightTexture;
    private GameObject streetlight;
    private ObjShape streetlightShape;

    private TextureImage buildingTexture;
    private GameObject building1;
    private GameObject building2;
    private GameObject building3;
    private ObjShape buildingShape;

    private TextureImage cityTexture;
    private GameObject city;
    private ObjShape cityShape;

    private TextureImage garbageFenceTexure;
    private GameObject garbageFence;
    private ObjShape garbageFenceShape;

    private GhostManager ghostManager;

    private ObjShape ghostShape;
    private TextureImage ghostTexture; 

    private GameObject Janitor;
    private ObjShape janitorShape;
    private TextureImage janitorImage;

    private GameObject SecurityGaurd;
    private ObjShape secuirtyGaurdShape;
    private TextureImage secuirtyImage;

    private String serverAddress;
    private int serverPort;
    private ProtocolType serverProtocol;
    private ProtocolClient protocolClient;
    private boolean isClientConnected = false;

	private long fileLastModifiedTime = 0;

    ScriptEngine jsEngine;

    private Matrix4f behindPlayer = (new Matrix4f()).scaling(0.2f);

	public MyGame(String serverAddress, int serverPort, String protocol) { 
        super(); 
        ghostManager = new GhostManager(this);
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
        if(protocol.toUpperCase().compareTo("TCP") == 0){
            this.serverProtocol = ProtocolType.TCP;
        }
        else{
            this.serverProtocol = ProtocolType.UDP;
        }
    }
    /**
     * main method
     * @param args
     */
	public static void main(String[] args){
        MyGame game = new MyGame(args[0], Integer.parseInt(args[1]), args[2]);
		engine = new Engine(game);
		game.initializeSystem();
		game.game_loop();
	}
    /**
     * loads all the shapes into the shapes variables
     */
	@Override
	public void loadShapes(){
        graffitiShape = new ImportedModel("graffiti_artist.obj");
        ghostShape = new ImportedModel("graffiti_artist.obj");
        hills = new TextureImage("hills.jpg");
        streetlightShape = new ImportedModel("streetlight.obj");
        xAxsisShape = new Line(center, xBorder);
        yAxsisShape = new Line(center, yBorder);
        zAxsisShape = new Line(center, zBorder);
        groundPlane = new Plane();
        buildingShape = new Cube();
        garbageFenceShape = new Cube();
        cityShape = new ImportedModel("city.obj");
        janitorShape = new ImportedModel("Janitor.obj");
        secuirtyGaurdShape = new ImportedModel("Secuirty_Gaurd.obj");
	}
    /**
     * loads all the textures into the texture variables
     */
	@Override
	public void loadTextures(){
        GrafTexture = new TextureImage("GraffText.png");
        groundTexture = new TextureImage("street.png");
        ghostTexture = new TextureImage("GraffText.png");
        streetlightTexture = new TextureImage("streetlight.png");
        hills = new TextureImage("hills.jpg");
        trash = new TextureImage("trash.png");
        terrShape = new TerrainPlane(100);
        buildingTexture = new TextureImage("street.png");
        garbageFenceTexure = new TextureImage("garbageFence.png");
        cityTexture = new TextureImage("city.png");
        secuirtyImage = new TextureImage("guard.png");
        janitorImage = new TextureImage("janitor.png");
    }
    /**
     * builds the objects in the game 
     * rigs the translation, shape, and textures to all the
     * objects in the game
     */
	@Override
	public void buildObjects(){

		Matrix4f initialTranslation, initalScale;

        Graffiti = new GameObject(GameObject.root(), graffitiShape, GrafTexture);
        initialTranslation = (new Matrix4f().translation(0,0,0));
        initalScale = (new Matrix4f()).scaling(10.2f);
        Graffiti.setLocalTranslation(initialTranslation);
        Graffiti.setLocalScale(initalScale);


        streetlight = new GameObject(GameObject.root(),streetlightShape,streetlightTexture);
        initialTranslation = (new Matrix4f().translation(0,0,0));
        initalScale = (new Matrix4f()).scaling(10.2f);
        streetlight.setLocalTranslation(initialTranslation);
        streetlight.setLocalScale(initalScale);

        Janitor = new GameObject(GameObject.root(),janitorShape, janitorImage);
        initialTranslation = (new Matrix4f().translation(10,10,20));
        initalScale = (new Matrix4f()).scaling(10.2f);
        streetlight.setLocalTranslation(initialTranslation);
        streetlight.setLocalScale(initalScale);

        SecurityGaurd = new GameObject(GameObject.root(),secuirtyGaurdShape, secuirtyImage);
        initialTranslation = (new Matrix4f().translation(0,0,10));
        initalScale = (new Matrix4f()).scaling(10.2f);
        streetlight.setLocalTranslation(initialTranslation);
        streetlight.setLocalScale(initalScale);


        ground = new GameObject(GameObject.root(), groundPlane, groundTexture);
        initialTranslation = (new Matrix4f().translation(0,0,0));
        initalScale = (new Matrix4f()).scaling(100.f);
        ground.setLocalTranslation(initialTranslation);
        ground.setLocalScale(initalScale);

        building1 = new GameObject(GameObject.root(), buildingShape, buildingTexture);
        initialTranslation = (new Matrix4f().translation(0,0,0));
        initalScale = (new Matrix4f()).scaling(100.f);
        building1.setLocalTranslation(initialTranslation);
        building1.setLocalScale(initalScale);

        building2 = new GameObject(GameObject.root(), buildingShape, buildingTexture);
        initialTranslation = (new Matrix4f().translation(0,0,0));
        initalScale = (new Matrix4f()).scaling(100.f);
        building2.setLocalTranslation(initialTranslation);
        building2.setLocalScale(initalScale);

        building3 = new GameObject(GameObject.root(), buildingShape, buildingTexture);
        initialTranslation = (new Matrix4f().translation(0,0,0));
        initalScale = (new Matrix4f()).scaling(100.f);
        building3.setLocalTranslation(initialTranslation);
        building3.setLocalScale(initalScale);

        xAxsis = new GameObject(GameObject.root(), xAxsisShape, trash );
        yAxsis = new GameObject(GameObject.root(), yAxsisShape, trash );
        zAxsis = new GameObject(GameObject.root(), zAxsisShape, trash );

        city = new GameObject(GameObject.root(), cityShape, cityTexture);
        initialTranslation = (new Matrix4f().translation(0,0,0));
        initalScale = (new Matrix4f()).scaling(100.f);
        city.setLocalTranslation(initialTranslation);
        city.setLocalScale(initalScale);
        
        //make garbage fence obj

        //maybe add some terrain for like a garbage dump, doens't make sense for that place tobe an area so probobly
        //just some background stuff
        terr = new GameObject(GameObject.root(), terrShape, trash);
		initialTranslation = (new Matrix4f()).translation(0f,0f,0f);
		terr.setLocalTranslation(initialTranslation);
		terr.setHeightMap(hills);
	}
    @Override
    public void loadSkyBoxes(){
        fluffyClouds = (engine.getSceneGraph()).loadCubeMap("night");
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
        Graffiti.setLocalTranslation(new Matrix4f().translation((Vector3fc) (jsEngine.get("dolphinTranslate"))));
        //use these as templates for init
        Graffiti.setLocalScale(new Matrix4f().scaling(((Double)(jsEngine.get("dolphinScale"))).floatValue()));

        SecurityGaurd.setLocalTranslation(new Matrix4f().translation((Vector3fc) (jsEngine.get("GaurdTranslate"))));
        SecurityGaurd.setLocalScale(new Matrix4f().scaling(((Double)(jsEngine.get("GaurdScale"))).floatValue()));

        Janitor.setLocalTranslation(new Matrix4f().translation((Vector3fc) (jsEngine.get("JanitorTranslate"))));
        Janitor.setLocalScale(new Matrix4f().scaling(((Double)(jsEngine.get("JanitorScale"))).floatValue()));

        streetlight.setLocalTranslation(new Matrix4f().translation((Vector3fc) (jsEngine.get("streetlightTranslate"))));
        streetlight.setLocalScale(new Matrix4f().scaling(((Double)(jsEngine.get("streetlightScale"))).floatValue()));

        terr.setLocalScale(new Matrix4f().scaling((Vector3fc) (jsEngine.get("garbageScale"))));
        terr.setLocalTranslation(new Matrix4f().translation((Vector3fc) (jsEngine.get("garbageTranslate"))));

        ground.setLocalScale(new Matrix4f().scaling(((Double)(jsEngine.get("groundScale"))).floatValue()));
        //garbageFence.setLocalScale(new Matrix4f().scaling(((Double)(jsEngine.get("building1"))).floatValue()));
        //garbageFence.setLocalTranslation(new Matrix4f().translation((Vector3fc) (jsEngine.get("garbageTranslate"))));

        
        building1.setLocalScale(new Matrix4f().scaling(((Double)(jsEngine.get("buildingScale"))).floatValue()));
        building1.setLocalTranslation(new Matrix4f().translation((Vector3fc) (jsEngine.get("buidling1"))));

        building2.setLocalScale(new Matrix4f().scaling(((Double)(jsEngine.get("buildingScale"))).floatValue()));
        building2.setLocalTranslation(new Matrix4f().translation((Vector3fc) (jsEngine.get("buidling2"))));

        building3.setLocalScale(new Matrix4f().scaling(((Double)(jsEngine.get("buildingScale"))).floatValue()));
        building3.setLocalTranslation(new Matrix4f().translation((Vector3fc) (jsEngine.get("buidling3"))));

        city.setLocalScale(new Matrix4f().scaling(((Double)(jsEngine.get("cityScale"))).floatValue()));
        city.setLocalTranslation(new Matrix4f().translation((Vector3fc) (jsEngine.get("cityTranslation"))));

        scriptFile2 = new File("assets/scripts/CreateLight.js");
		this.runScript(scriptFile2);
        //object light
		(engine.getSceneGraph()).addLight((Light)jsEngine.get("light"));

        scriptFile3 = new File("assets/scripts/UpdateLightColor.js");
		this.runScript(scriptFile3);

        rc = new RotationController(engine, new Vector3f(0,1,0), ((Double)(jsEngine.get("rumble"))).floatValue());
        rc.enable();
        (engine.getSceneGraph()).addNodeController(rc);



		//----------------- adding lights -----------------
		Light.setGlobalAmbient(0.5f, 0.5f, 0.5f);

		light1 = new Light();
		light1.setLocation(new Vector3f(2.0f, 3.0f, 2.7f));
		(engine.getSceneGraph()).addLight(light1);



		// ------------- positioning the camera -------------
		(engine.getRenderSystem().getViewport("LEFT").getCamera()).setLocation(new Vector3f(0,20,5));
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
        setupNetworking();
        orbit = new CameraOrbit3D(camera, Graffiti, engine, this);
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
            String dispStr2 = "p1 " + Graffiti.getWorldLocation() + "camera" + camera.getLocation();
            Vector3f hud1Color = new Vector3f(1,1,1);
            Vector3f hud2Color = new Vector3f(0,0,1);
            Vector3f hudCameraVector = Graffiti.getLocalLocation();
            (engine.getHUDmanager()).setHUD1(dispStr1, hud1Color, 15, 15);
            (engine.getHUDmanager()).setHUD2(dispStr2, hud2Color, 1000, 275);
            hudCameraVector.y = hudCameraVector.y + 2.5f + zoomPan;
            HudCamera.setLocation(hudCameraVector);
            inputManager.update((float)elaspedTime);
            processNetworking((float)elaspedTime);
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
     * getPlayer gets player and returns it
     * @return dolphin
     */
    public GameObject getPlayer(){
        return Graffiti;
    }
    /**
     * gets Game Camera and returns it
     * @return camera
     */
    public Camera getCamera(){
        return camera; 
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
    public ObjShape getGhostShape() { 
        return ghostShape;
    }
	public TextureImage getGhostTexture() {
        return ghostTexture;
    }
	public GhostManager getGhostManager() {
        return ghostManager; 
    }
	private void setupNetworking(){	
        isClientConnected = false;	
		try {	
            protocolClient = new ProtocolClient(InetAddress.getByName(serverAddress), serverPort, serverProtocol, this);
		} 	
        catch (UnknownHostException e) {	
            e.printStackTrace();
		}	
        catch (IOException e) {
            e.printStackTrace();
		}
		if (protocolClient == null){
            System.out.println("missing protocol host");
		}
		else{
            // Send the initial join message with a unique identifier for this client
			System.out.println("sending join message to protocol host");
			protocolClient.sendJoinMessage();
		}
	}
	
	protected void processNetworking(float elapsTime)
	{	// Process packets received by the client from the server
		if (protocolClient != null)
        protocolClient.processPackets();
	}

	public Vector3f getPlayerPosition() { return Graffiti.getWorldLocation(); }

	public void setIsConnected(boolean value) { this.isClientConnected = value; }
	
	private class SendCloseConnectionPacketAction extends AbstractInputAction
	{	@Override
		public void performAction(float time, net.java.games.input.Event evt) 
		{	if(protocolClient != null && isClientConnected == true)
			{	protocolClient.sendByeMessage();
			}
		}
	}
}