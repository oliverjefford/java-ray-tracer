/**
 * Class to represent a vector model.
 * Contains formulae to manipulate vectors and perform simple arithmetic operations.
 * 
 * @author Oliver Jefford
 *
 */
public class Vector {
	private double x;
	private double y;
	private double z;
	
	/**
	 * Constructs a direction vector with world space coordinates.
	 * 
	 * @param x - x value in world space coordinates
	 * @param y - y value in world space coordinates
	 * @param z - z value in world space coordinates
	 */
	public Vector(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	/**
	 * Constructs a direction vector using two points.
	 * 
	 * @param a - the subtracting point
	 * @param b - the point the vector is pointing at
	 */
	public Vector(Point a, Point b) {
		x = (b.getX() - a.getX());
		y = (b.getY() - a.getY());
		z = (b.getZ() - a.getZ());
	}
	
	/**
	 * Vector addition method.
	 * 
	 * @param v - Add v to this vector
	 * @return the new vector
	 */
	public Vector plus(Vector v) {
		return new Vector(x + v.x, y + v.y, + z + v.z);
	}
	
	/**
	 * Vector subtraction method.
	 * 
	 * @param v - Subtract v from this vector 
	 * @return the new vector
	 */
	public Vector minus(Vector v) {
		return new Vector(x - v.x, y - v.y, + z - v.z);
	}
	
	/**
	 * Apply a scalar multiple to all axis values of the vector.
	 * 
	 * @param figure - the scalar value
	 * @return the new calculated vector
	 */
	public Vector times(double figure) {
		return new Vector((x * figure), (y * figure), (z * figure));
	}
	
	public Vector negate() {
		return times(-1.0);
	}
	
	/**
	 * Calculate the cross product of the vector. Performs a perpendicular vector
	 * as an output of this vector and vector u.
	 * 
	 * @param u - The second vector
	 * @return a perpendicular vector
	 */
	public Vector cross(Vector u) {
		double xValue = (y * u.z)-(z * u.y);
		double yValue = (z * u.x)-(x * u.z);
		double zValue = (x * u.y)-(y * u.x);
		
		return new Vector (xValue, yValue, zValue); 
	}
	
	/**
	 * Calculate the dot product of the vector. Returns a dotted value between two
	 * vectors.
	 * 
	 * @param u - The second vector
	 * @return the dot value
	 */
	public double dot(Vector u) {
		double dotValue = (x * u.x) + (y * u.y) + (z * u.z);
		return dotValue;
	}
	
	/**
	 * Normalise the vector into a unit vector for easier computation.
	 * 
	 * @return a unit vector
	 */
	public Vector normalise() {
		double vectorLength = length();
		Vector normalisedVector = new Vector(x / vectorLength, y / vectorLength, z / vectorLength);
		return normalisedVector;
	}
	
	/**
	 * Calculates the length of the current vector.
	 * 
	 * @return The length of the vector in world space
	 */
	private double length() {
		return Math.sqrt((x * x) + (y * y) + (z * z));
	}
	
	/**
	 * Retrieves the x value of the direction vector.
	 * 
	 * @return the x value in world space
	 */
	public double getX() {
		return x;
	}
	
	/**
	 * Retrieves the y value of the direction vector.
	 * 
	 * @return the y value in world space
	 */
	public double getY() {
		return y;
	}
	
	/**
	 * Retrieves the z value of the direction vector.
	 * 
	 * @return the z value in world space
	 */
	public double getZ() {
		return z;
	}
	
	/**
	 * Creates a string representation of the vector for debugging.
	 */
	public String toString() {
		return getX() + ", " + getY() + ", " + getZ();
	}
}
