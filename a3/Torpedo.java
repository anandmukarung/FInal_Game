package a3;

import tage.*;
import tage.shapes.*;
/**
 * class for making torpedo
 * extended from cube object 
 * basically a poorly made cube with spikes coming out of it
 */
public class Torpedo extends ManualObject{
	float[] vertices =
	{ 1.0f,  1.0f, -1.0f,  -1.0f, -1.0f, -1.0f,  1.0f, -1.0f, -1.0f, // back face lower left
	1.0f, -1.0f, -1.0f,  1.0f,  1.0f, -1.0f,  -1.0f,  1.0f, -1.0f, // back face upper right
	1.0f, -1.0f, -1.0f,  1.0f, -1.0f,  1.0f,  1.0f,  1.0f, -1.0f, // right face lower back
	1.0f, -1.0f,  1.0f,  1.0f,  1.0f,  1.0f,  1.0f,  1.0f, -1.0f, // right face upper front
	1.0f, -1.0f,  1.0f,  -1.0f, -1.0f,  1.0f,  1.0f,  1.0f,  1.0f, // front face lower right
	-1.0f, -1.0f,  1.0f,  -1.0f,  1.0f,  1.0f,  1.0f,  1.0f,  1.0f, // front face upper left
    -4.0f, 0.0f, 0.0f, 4.0f, 0.0f, 0.0f, //spikes
    0.0f, 2.0f, 0.0f,  0.0f, -2.0f, 0.0f //spikes
	-1.0f, -1.0f,  1.0f,  -1.0f, -1.0f, -1.0f,  -1.0f,  1.0f,  1.0f, // left face lower front
	-1.0f, -1.0f, -1.0f,  -1.0f,  1.0f, -1.0f,  -1.0f,  1.0f,  1.0f, // left face upper back
	-1.0f, -1.0f,  1.0f,  1.0f, -1.0f,  1.0f,  1.0f, -1.0f, -1.0f, // bottom face right front
	1.0f, -1.0f, -1.0f,  -1.0f, -1.0f, -1.0f,  -1.0f, -1.0f,  1.0f, // bottom face left back
	-1.0f,  1.0f, -1.0f,  1.0f,  1.0f, -1.0f,  1.0f,  1.0f,  1.0f, // top face right back
	1.0f,  1.0f,  1.0f,  -1.0f,  1.0f,  1.0f,  -1.0f,  1.0f, -1.0f }; // top face left front

	float[] texCoords = new float[]
	{ 1.0f, 1.0f,  1.0f, 0.0f,  0.0f, 0.0f, // back face
	0.0f, 0.0f,  0.0f, 1.0f,  1.0f, 1.0f,
	1.0f, 0.0f,  0.0f, 0.0f,  1.0f, 1.0f, // right face
	0.0f, 0.0f,  0.0f, 1.0f,  1.0f, 1.0f,
	1.0f, 0.0f,  0.0f, 0.0f,  1.0f, 1.0f, // front face
	0.0f, 0.0f,  0.0f, 1.0f,  1.0f, 1.0f,
	1.0f, 0.0f,  0.0f, 0.0f,  1.0f, 1.0f, // left face
	0.0f, 0.0f,  0.0f, 1.0f,  1.0f, 1.0f,
	0.0f, 1.0f,  1.0f, 1.0f,  1.0f, 0.0f, // bottom face
	1.0f, 0.0f,  0.0f, 0.0f,  0.0f, 1.0f,
	0.0f, 1.0f,  1.0f, 1.0f,  1.0f, 0.0f, // top face
	1.0f, 0.0f,  0.0f, 0.0f,  0.0f, 1.0f };

	float[] normals = new float[]
	{ 0.0f, 0.0f, -1.0f,  0.0f, 0.0f, -1.0f,  0.0f, 0.0f, -1.0f, // back face
	0.0f, 0.0f, -1.0f,  0.0f, 0.0f, -1.0f,  0.0f, 0.0f, -1.0f,
	1.0f, 0.0f, 0.0f,  1.0f, 0.0f, 0.0f,  1.0f, 0.0f, 0.0f, // right face
	1.0f, 0.0f, 0.0f,  1.0f, 0.0f, 0.0f,  1.0f, 0.0f, 0.0f,
	0.0f, 0.0f, 1.0f,  0.0f, 0.0f, 1.0f,  0.0f, 0.0f, 1.0f, // front face
	0.0f, 0.0f, 1.0f,  0.0f, 0.0f, 1.0f,  0.0f, 0.0f, 1.0f,
	-1.0f, 0.0f, 0.0f,  -1.0f, 0.0f, 0.0f,  -1.0f, 0.0f, 0.0f, // left face
	-1.0f, 0.0f, 0.0f,  -1.0f, 0.0f, 0.0f,  -1.0f, 0.0f, 0.0f,
	0.0f, -1.0f, 0.0f,  0.0f, -1.0f, 0.0f,  0.0f, -1.0f, 0.0f, // bottom face
	0.0f, -1.0f, 0.0f,  0.0f, -1.0f, 0.0f,  0.0f, -1.0f, 0.0f,
	0.0f, 1.0f, 0.0f,  0.0f, 1.0f, 0.0f,  0.0f, 1.0f, 0.0f, // top face
	0.0f, 1.0f, 0.0f,  0.0f, 1.0f, 0.0f,  0.0f, 1.0f, 0.0f };

	/** Creates a 38-vertex cube with texture coordinates ranging from (0,0) to (1,1) on each side. */
	public Torpedo(){	
		setNumVertices(38);
		setVertices(vertices);
		setTexCoords(texCoords);
		setNormals(normals);
		setWindingOrderCCW(false);
	}
}