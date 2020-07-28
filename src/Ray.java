/**
 * Ray class to model a ray containing an origin, distance and direction.
 * 
 * @author Oliver Jefford
 *
 */
public class Ray {

	private Point origin;
	private Vector direction;
	private double t;
	
	/**
	 * Constructs a ray using an origin and direction vector. 
	 * 
	 * @param origin - The starting point of the ray
	 * @param direction - The direction of the ray
	 */
	public Ray(Point origin, Vector direction) {
		this.origin = origin;
		this.direction = direction;
	}
	
	/**
	 * Retrieves the direction vector of the ray.
	 * 
	 * @return direction of the ray
	 */
	public Vector getDirection() {
		return direction;
	}
	
	/**
	 * Retrieves the origin of the ray.
	 * 
	 * @return the starting point of the ray
	 */
	public Point getOrigin() {
		return origin;
	}
	
	/**
	 * Sets the distance to the intersection point on the ray.
	 * 
	 * @param t - The distance to the intersection point
	 */
	public void setDistance(double t) {
		this.t = t;
	}
	
	/**
	 * Retrieves the distance to the intersection point on the ray.
	 * 
	 * @return the distance value in world space coordinates
	 */
	public double getDistance() {
		return t;
	}
	
	/**
	 * Calculates the intersection point using all attributes of the ray.
	 *  P = o + dt
	 *  
	 * @return the point of intersection
	 */
	public Point getIntersectionPoint() {
		Point point = origin.add(direction.times(t));
		return point;
	}
}
