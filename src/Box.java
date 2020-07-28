/**
 * Class to represent a bounding volume surround object(s) in the scene
 * with maximum and minimum points.
 * 
 * @author Oliver Jefford
 *
 */
public class Box {

	private Point min;
	private Point max;

	/**
	 * Constructs a bounding volume using a minimum and maximum point.
	 * 
	 * @param min - Minimum x,y,z values
	 * @param max - Maximum x,y,z values
	 */
	public Box(Point min, Point max) {
		this.min = min;
		this.max = max;
	}

	/**
	 * Sets the minimum of the bounding volume.
	 * 
	 * @param min - Minimum x,y,z values
	 */
	public void setMin(Point min) {
		this.min = min;
	}

	/**
	 * Retrieves the minimum point of the bounding volume.
	 * 
	 * @return the minimum point
	 */
	public Point getMin() {
		return min;
	}

	/**
	 * Sets the maximum of the bounding volume.
	 * 
	 * @param min - Maximum x,y,z values
	 */
	public void setMax(Point max) {
		this.max = max;
	}

	/**
	 * Retrieves the maximum point of the bounding volume.
	 * 
	 * @return the maximum point
	 */
	public Point getMax() {
		return max;
	}

	/**
	 * Checks for box intersection with the ray.
	 * 
	 * @param ray - casted ray to check for intersection
	 * @return true for intersections
	 * 		   false for no intersections
	 */
	public boolean isIntersectedBy(Ray ray) {
		double xMin, yMin, zMin, tMin;
		double xMax, yMax, zMax, tMax;

		Point origin = ray.getOrigin();
		Vector direction = ray.getDirection();

		xMin = (min.getX() - origin.getX()) / direction.getX();
		xMax = (max.getX() - origin.getX()) / direction.getX();
				
		yMin = (min.getY() - origin.getY()) / direction.getY();
		yMax = (max.getY() - origin.getY()) / direction.getY();

		zMin = (min.getZ() - origin.getZ()) / direction.getZ();
		zMax = (max.getZ() - origin.getZ()) / direction.getZ();

		tMax = Math.min(Math.min(Math.max(xMin, xMax), Math.max(yMin, yMax)), Math.max(zMin, zMax));

		// If ray is behind camera
		if (tMax < 0) return false;

		tMin = Math.max(Math.max(Math.min(xMin, xMax), Math.min(yMin, yMax)), Math.min(zMin, zMax));

		if (tMin > tMax) return false;

		ray.setDistance(tMin);
		return true;
	}
}
