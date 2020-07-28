import java.awt.Color;
/**
 * Sphere class that extends on a shape object. Builds a sphere using a centre and 
 * radius.
 * 
 * @author Oliver Jefford
 *
 */
public class Sphere extends Shape{

	private Point centre;
	private double radius;
	private Vector normal;
	
	/**
	 * Constructs a sphere with a centre and radius, and sets the minimum and 
	 * maximum coordinate values of the current sphere.
	 * 
	 * @param centre - Centre of the sphere
	 * @param radius - Radius of the sphere (diameter / 2)
	 * @param colour - Colour of the sphere
	 * @param ambient - The ambient reflectivity of the sphere
	 * @param diffuse - The diffuse reflectivity of the sphere
	 * @param specular - The specular reflectivity of the sphere
	 */
	public Sphere(Point centre, double radius, Color colour, double ambient, double diffuse, double specular) {
		super(colour, ambient, diffuse, specular);
		this.centre = centre;
		this.radius = radius;
		
		setMinX();
		setMinY();
		setMinZ();
		setMaxX();
		setMaxY();
		setMaxZ();
	}

	@Override
	public Vector getNormal() {
		return normal;
	}
	
	@Override
	public double isIntersectedBy(Ray ray) {
		Point originMinusCentre = ray.getOrigin().minus(centre);
		Vector rayDirection = ray.getDirection();
		
		double a = rayDirection.dot(rayDirection);
		double b = 2 * (originMinusCentre.dot(rayDirection));
		double c = originMinusCentre.dot(originMinusCentre) - (radius * radius);
		double discriminant = (b*b) - (4*a*c);
		
		if (discriminant < 0) {
			return -1;
		} else { 
			return findTValue(a, b, discriminant, ray);
		}
	}
	
	/**
	 * Calculates the distance of the the ray to the intersection point on the current
	 * sphere.
	 * 
	 * @param a - ray direction (dot) ray direction
	 * @param b - 2 * (ray origin - centre) (dot) ray direction
	 * @param discriminant - Initial checks for a valid ray
	 * @param ray - the casted ray into the scene
	 * @return a distance to the intersection point
	 */
	private double findTValue(double a, double b, double discriminant, Ray ray) {
		double t1 = (-b - Math.sqrt(discriminant)) / (2*a);
		double t2 = (-b + Math.sqrt(discriminant)) / (2*a);
		
		if (t1 < 0 && t2 > 0) {
			return -1;
		} else if (t1 > 0 && t2 > 0) {
			if (t1 <= t2) {
				ray.setDistance(t1);
				setNormalUsing(ray);
				return t1;
			} else { 
				ray.setDistance(t2);
				setNormalUsing(ray);
				return t2;
			}
		} else {
			return -1;
		}
	}
	
	/**
	 * Calculates the normal at the intersection point on the sphere.
	 * 
	 * @param ray - the casted ray into the scene
	 */
	private void setNormalUsing(Ray ray) {
		Point intersectionPoint = ray.getIntersectionPoint();
		normal = new Vector(centre, intersectionPoint).normalise();
	}
	
	/**
	 * Sets the max x coordinate of the sphere.
	 */
	private void setMaxX() {
		xMax = radius + centre.getX();
	}
	
	/**
	 * Sets the max y coordinate of the sphere.
	 */
	private void setMaxY() {
		yMax = radius + centre.getY();
	}
	
	/**
	 * Sets the max z coordinate of the sphere.
	 */
	private void setMaxZ() {
		zMax = radius + centre.getZ();
	}
	
	/**
	 * Sets the min x coordinate of the sphere.
	 */
	private void setMinX() {
		xMin = centre.getX() - radius;
	}
	
	/**
	 * Sets the min y coordinate of the sphere.
	 */
	private void setMinY() {
		yMin = centre.getY() - radius;
	}
	
	/**
	 * Sets the min z coordinate of the sphere.
	 */
	private void setMinZ() {
		zMin = centre.getZ() - radius;
	}
}
