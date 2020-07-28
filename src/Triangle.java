import java.awt.Color;
/**
 * Builds a triangle using vertices and normals to connect each vertex. Checks for
 * intersections with each ray casted into the scene.
 * 
 * @author Oliver Jefford
 *
 */
public class Triangle extends Shape {

	private Point v0;
	private Point v1;
	private Point v2;
	private Vector vectorAB;
	private Vector vectorAC;
	private Vector normal;

	/**
	 * Constructs a triangle object within the scene.
	 * 
	 * @param v0 - Point one on the triangle
	 * @param v1 - Point two on the triangle
	 * @param v2 - Point three on the triangle
	 * @param colour - Colour of the triangle
	 * @param ambient - The ambient reflectivity of the surface
	 * @param diffuse - The diffuse reflectivity of the surface
	 * @param specular - The specular reflectivity of the surface
	 */
	public Triangle(Point v0, Point v1, Point v2, Color colour, double ambient, double diffuse, double specular) {
		super(colour, ambient, diffuse, specular);
		this.v0 = v0;
		this.v1 = v1;
		this.v2 = v2;
		
		vectorAB = getVectorAB();
		vectorAC = getVectorAC();
		normal = vectorAB.cross(vectorAC);
		setMidPoint(v0, v1, v2);
		
		setMinX();
		setMinY();
		setMinZ();
		setMaxX();
		setMaxY();
		setMaxZ();
	}
	
	@Override
	public double isIntersectedBy(Ray ray) {
		Vector pointVector = ray.getDirection().cross(vectorAC);
		double determinant = vectorAB.dot(pointVector);

		if (determinant < 1e-6 && determinant > 1.0e30) {
			return -1;
		} else {
			return findTValue(ray, determinant, pointVector);
		}
	}
	
	/**
	 * Calculates the distance to the intersection point of the current ray
	 * that has been casted from the camera.
	 * 
	 * @param ray - casted ray from the camera
	 * @param determinant - Initial checks for a valid ray
	 * @param pointVector - ray direction X vector A to C
	 * @return the distance to the intersection point
	 */
	private double findTValue(Ray ray, double determinant, Vector pointVector) {
		double invertedDeterminant = (double) (1.0 / determinant);
		Vector tVector = new Vector(v0, ray.getOrigin());
		double u = tVector.dot(pointVector) * invertedDeterminant;
		if (u < 0 || u > 1) return -1;

		Vector vVector = tVector.cross(vectorAB);
		double v = ray.getDirection().dot(vVector) * invertedDeterminant;
		if (v < 0 || u + v > 1) return -1;
		
		double t = vectorAC.dot(vVector) * invertedDeterminant;
		if (t <= 0) return -1;
		
		ray.setDistance(t);
		return t;
	}
	
	@Override
	public Vector getNormal() {
		return normal.normalise();
	}
	
	public void invertNormal() {
		normal = normal.negate();
	}
	
	/**
	 * Creates a vector from the corner to the point of intersection.
	 * 
	 * @param cornerPoint - corner point on the triangle
	 * @param pointOfIntersection - intersection point
	 * @return vector from the IP to the corner
	 */
	public Vector getVectorToPoint(Point cornerPoint, Point pointOfIntersection) {
		return new Vector(cornerPoint, pointOfIntersection);
	}
	
	@Override
	public Point getMidPoint() {
		return mid;
	}
	
	/**
	 * Sets the min x coordinate value.
	 */
	public void setMinX() {
		xMin = Math.min(Math.min(v0.getX(), v1.getX()), v2.getX());
	}
	
	/**
	 * Sets the min y coordinate value.
	 */
	public void setMinY() {
		yMin = Math.min(Math.min(v0.getY(), v1.getY()), v2.getY());
	}
	
	/**
	 * Sets the min z coordinate value.
	 */
	public void setMinZ() {
		zMin = Math.min(Math.min(v0.getZ(), v1.getZ()), v2.getZ());
	}
	
	/**
	 * Sets the max x coordinate value.
	 */
	public void setMaxX() {
		xMax = Math.max(Math.max(v0.getX(), v1.getX()), v2.getX());
	}
	
	/**
	 * Sets the max y coordinate value.
	 */
	public void setMaxY() {
		yMax = Math.max(Math.max(v0.getY(), v1.getY()), v2.getY());
	}
	
	/**
	 * Sets the max z coordinate value.
	 */
	public void setMaxZ() {
		zMax = Math.max(Math.max(v0.getZ(), v1.getZ()), v2.getZ());
	}
	
	/**
	 * Calculates the mid point of the triangle by finding the centroid.
	 * 
	 * @param a - vertex v0
	 * @param b - vertex v1
	 * @param c - vertex v2
	 */
	private void setMidPoint(Point a, Point b, Point c) {
		double x = (a.getX() + b.getX() + c.getX()) / 3;
		double y = (a.getY() + b.getY() + c.getY()) / 3;
		double z = (a.getZ() + b.getZ() + c.getZ()) / 3;
		
		mid = new Point(x,y,z);
	}
	
	/**
	 * Calculates the vector from one point to another. Creates the edge on
	 * the triangle.
	 * 
	 * @return vector A to B
	 */
	private Vector getVectorAB() {
		return new Vector(v0, v1);
	}
	
	/**
	 * Calculates the vector from one point to another. Creates the edge on
	 * the triangle.
	 * 
	 * @return vector A to C
	 */
	private Vector getVectorAC() {
		return new Vector(v0, v2);
	}
}
