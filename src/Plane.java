import java.awt.Color;
/**
 * Plane class to check for intersections and construction.
 * 
 * @author Oliver Jefford
 *
 */
public class Plane extends Shape {

	private Point point;
	private Vector normal;
	
	/**
	 * Constructs a plane object using a point and a normal.
	 * 
	 * @param point - Any point on the plane
	 * @param normal - The normal at any point of the plane
	 * @param colour - The colour of the plane
	 * @param ambient - The ambient reflectivity of the surface
	 * @param diffuse - The diffuse reflectivity of the surface
	 * @param specular - The specular reflectivity of the surface
	 */
	public Plane(Point point, Vector normal, Color colour, double ambient, double diffuse, double specular) {
		super(colour, ambient, diffuse, specular);
		this.point = point;
		this.normal = normal;
	}
	
	@Override
	public double isIntersectedBy(Ray ray) {
		double denominator = getNormal().dot(ray.getDirection());

		if (denominator < 1e-6 && denominator > 1.0e30) {
			return -1;
		} else {
			return findTValue(ray, denominator);
		}
	}
	
	@Override
	public Vector getNormal() {
		return normal.normalise();
	}
	
	/**
	 * Retrieves the point on the plane.
	 * 
	 * @return the constructed point on the plane
	 */
	public Point getPoint() {
		return point;
	}
	
	/**
	 * Calculates the t, distance, value of the ray to the intersection point.
	 * 
	 * @param ray - casted ray into the scene
	 * @param denominator - initial valid ray check
	 * @return the distance to the intersection point from the ray origin
	 */
	private double findTValue(Ray ray, double denominator) {
		Point pointOnPlaneMinusRayOrigin = getPoint().minus(ray.getOrigin());
		double numerator = pointOnPlaneMinusRayOrigin.dot(getNormal());
		double t = numerator / denominator;
		if (t <= 1e-6) {
			return -1;
		} else {
			ray.setDistance(t);
			return t;
		}
	}
}
