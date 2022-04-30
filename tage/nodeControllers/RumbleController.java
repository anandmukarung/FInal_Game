package tage.nodeControllers;
import tage.*;
import org.joml.*;
import java.util.Random;

/**
* A RotationController is a node controller that, when enabled, causes any object
* it is attached to to rotate in place around the tilt axis specified.
* @author Scott Gordon
*/
public class RumbleController extends NodeController
{
	private Vector3f rotationAxis = new Vector3f(0.0f, 1.0f, 0.0f);
	private float rotationSpeed = 1.0f;
	Vector3f curLocation;
    private Matrix4f rotMatrix, newRotation;
	private Engine engine;
    private Random random = new Random();
	/** Creates a rotation controller with vertical axis, and speed=1.0. */
	public RumbleController() { super(); }

	/** Creates a rotation controller with rotation axis and speed as specified. */
	public RumbleController(Engine e, Vector3f axis, float speed)
	{	super();
		rotationAxis = new Vector3f(axis);
		rotationSpeed = speed;
		engine = e;
		rotMatrix = new Matrix4f();
	}

	/** sets the rotation speed when the controller is enabled */
	public void setSpeed(float s) { rotationSpeed = s; }

	/** This is called automatically by the RenderSystem (via SceneGraph) once per frame
	*   during display().  It is for engine use and should not be called by the application.
	*/
	public void apply(GameObject go)
	{	
        Vector3f rumble = go.getLocalLocation();
        rumble.x = rumble.x + (float)(-.05 + (.05 - -.05) * random.nextFloat());
        rumble.y = rumble.y + (float)(-.0005 + (.0005 - -.0005) * random.nextFloat());
        rumble.z = rumble.z + (float)(-.05 + (.05 - -.05) * random.nextFloat());
        go.setLocalLocation(rumble);
    }
}