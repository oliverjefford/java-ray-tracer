import java.awt.Color;

/**
 * An abstract class to represent a base model for all shapes within the scene.
 * 
 * @author Oliver Jefford
 *
 */
public abstract class Shape {
	protected Color colour;
	protected double ambient;
	protected double specular;
	protected double diffuse;
	protected Point mid;
	protected double xMin, yMin, zMin;
	protected double xMax, yMax, zMax;
	
	/**
	 * Constructs a general shape object within the scene.
	 * 
	 * @param colour - Colour of the shape
	 * @param ambient - The ambient reflection of the shape
	 * @param diffuse - The diffuse reflection of the shape
	 * @param specular - The specular reflection of the shape
	 */
	public Shape(Color colour, double ambient, double diffuse, double specular) {
		this.colour = colour;
		this.ambient = ambient;
		this.diffuse = diffuse;
		this.specular = specular;
	}
	
	/**
	 * Overriding method for each shape type (plane, triangle, sphere) to check for 
	 * any intersections within the scene.
	 *  
	 * @param ray - casted ray into the scene
	 * @return the distance to the intersection point
	 */
	public double isIntersectedBy(Ray ray) {
		return -1;
	}
	
	/**
	 * Retrieves the colour of the shape.
	 * 
	 * @return the colour of the shape
	 */
	public Color getColour() {
		return colour;
	}
	
	/**
	 * Alters the colour of the current shape.
	 * 
	 * @param colour - New colour of the shape
	 */
	public void alterColour(Color colour) {
		this.colour = colour;
	}
	
	/**
	 * Overriding method to get the normal of each shape.
	 * 
	 * @return the normal of the shape
	 */
	public Vector getNormal() {
		return null;
	}
	
	/**
	 * Retrieves the ambient intensity of the shape.
	 * 
	 * @return ambient intensity
	 */
	public double getAmbient() {
		return ambient;
	}
	
	/**
	 * Retrieves the diffuse intensity of the shape.
	 * 
	 * @return diffuse intensity
	 */
	public double getDiffuse() {
		return diffuse;
	}
	
	/**
	 * Retrieves the specular intensity of the shape.
	 * 
	 * @return specular intensity
	 */
	public double getSpecular() {
		return specular;
	}
	
	/**
	 * Overriding method to set the mid point of the shape.
	 * 
	 * @param mid - the calculated mid point of the shape
	 */
	public void setMidPoint(Point mid) {
		this.mid = mid;
	}
	
	/**
	 * Retrieves the mid point of the shape.
	 * 
	 * @return the mid point
	 */
	public Point getMidPoint() {
		return mid;
	}
	
	/**
	 * Retrieves the minimum x value of the shape.
	 * 
	 * @return the min x coordinate
	 */
	public double getMinX() {
		return xMin;
	}
	
	/**
	 * Retrieves the minimum y value of the shape.
	 * 
	 * @return the min y coordinate
	 */
	public double getMinY() {
		return yMin;
	}
	
	/**
	 * Retrieves the minimum z value of the shape.
	 * 
	 * @return the min z coordinate
	 */
	public double getMinZ() {
		return zMin;
	}
	
	/**
	 * Retrieves the maximum x value of the shape.
	 * 
	 * @return the max x coordinate
	 */
	public double getMaxX() {
		return xMax;
	}
	
	/**
	 * Retrieves the maximum y value of the shape.
	 * 
	 * @return the max y coordinate
	 */
	public double getMaxY() {
		return yMax;
	}
	
	/**
	 * Retrieves the maximum z value of the shape.
	 * 
	 * @return the max z coordinate
	 */
	public double getMaxZ() {
		return zMax;
	}
}
