/**
 * Class to represent a point in world space and allow simple arithmetic 
 * operations on each point.
 * 
 * @author Oliver Jefford
 *
 */
public class Point {

	private double x;
	private double y;
	private double z;
	
	/**
	 * Constructs a point with an x,y,z world space value.
	 * 
	 * @param x - the x axis value
	 * @param y - the y axis value
	 * @param z - the z axis value
	 */
	public Point (double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	/**
	 * Calculates a point subtracted from the current point.
	 * 
	 * @param point - the point to subtract from this
	 * @return the new point value
	 */
	public Point minus(Point point) {
		double xValue = x - point.x;
		double yValue = y - point.y;
		double zValue = z - point.z;
		
		return new Point(xValue, yValue, zValue);
	}
	
	/**
	 * Calculates a point using addition.
	 * 
	 * @param point - The point to add to the current point
	 * @return the new calculated point
	 */
	public Point add(Point point) {
		double xValue = x + point.x;
		double yValue = y + point.y;
		double zValue = z + point.z;
		
		return new Point(xValue, yValue, zValue);
	}
	
	/**
	 * Calculates the dot product between two points.
	 * 
	 * @param point - The second point to perform the dot product with
	 * @return the dot value
	 */
	public double dot(Point point) {
		double dotValue = (x * point.x) +
						  (y * point.y) +
						  (z * point.z);
		return dotValue;
	}
	
	/**
	 * Calculates the dot product between a point and a vector.
	 * 
	 * @param vector - the vector to perform the dot product with
	 * @return the dot value
	 */
	public double dot(Vector vector) {
		double dotValue = (x * vector.getX()) +
						  (y * vector.getY()) +
						  (z * vector.getZ());
		return dotValue;
	}
	
	/**
	 * Adds a vector to the current point value.
	 * 
	 * @param vector - the vector to add to the current point
	 * @return the new point value
	 */
	public Point add(Vector vector) {
		double xValue = x + vector.getX();
		double yValue = y + vector.getY();
		double zValue = z + vector.getZ();
		
		return new Point(xValue, yValue, zValue);
	}
	
	/**
	 * Negate the current x axis value.
	 * 
	 * @return the negated x value
	 */
	public double negateX() {
		return -x;
	}
	
	/**
	 * Negate the current y axis value.
	 * 
	 * @return the negated y value
	 */
	public double negateY() {
		return -y;
	}
	
	/**
	 * Negate the current z axis value.
	 * 
	 * @return the negated z value
	 */
	public double negateZ() {
		return -z;
	}
	
	/**
	 * Retrieves the current x axis value.
	 * 
	 * @return the x axis value
	 */
	public double getX() {
		return x;
	}
	
	/**
	 * Retrieves the current y axis value.
	 * 
	 * @return the y axis value
	 */
	public double getY() {
		return y;
	}
	
	/**
	 * Retrieves the current z axis value.
	 * 
	 * @return the z axis value
	 */
	public double getZ() {
		return z;
	}
	
	/**
	 * Prints a formatted x,y,z format for debugging.
	 */
	public String toString() {
		return getX() + ", " + getY() + ", " + getZ();
	}
}
