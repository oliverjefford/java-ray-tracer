/**
 * Models a camera (viewpoint) of the world space. Allows the scene to be rendered in
 * a final image after all pixels have been checked for intersections. 
 * 
 * @author Oliver Jefford
 *
 */
public class Camera {

	private Point origin;
	private Vector viewUp;
	private Vector viewRight;
	private Vector viewPlaneNormal;
	private double fov;
	
	private double windowHeight;
	private double windowWidth;
	private double aspectRatio;
	
	/**
	 * Constructs a camera using given coordinates in world space.
	 * 
	 * @param origin - Viewpoint of the camera
	 * @param viewUp - The view up vector of the camera
	 * @param lookAt - The looking direction of the camera
	 * @param fov - The field of view for the image
	 * @param aspectRatio - The aspect ratio of the created image
	 */
	public Camera(Point origin, Vector viewUp, Point lookAt, double fov, double aspectRatio) {
		this.origin = origin;
		viewPlaneNormal = new Vector(origin, lookAt);
		
		this.fov = fov;
		this.aspectRatio = aspectRatio;
		windowHeight = calculateHeight();
		windowWidth = calculateWidth();
		
		Vector n, u, v;
		n = viewPlaneNormal.normalise(); 
		u = viewUp.cross(n).normalise();
		v = n.cross(u);

		this.viewUp = v;
		viewRight = u;
		viewPlaneNormal = n;
	}

	/**
	 * Casts a ray at a given point using world coordinates.
	 * 
	 * @param pointOnImage - The point to fire the ray at
	 * @return the casted ray
	 */
	public Ray fireAt(Point pointOnImage) {
		Vector forward = getVPN();
		Vector up = getVUV().times(windowHeight * pointOnImage.getY());
		Vector right = getVRV().times(windowWidth * pointOnImage.getX());
		
		Vector direction = (forward.plus(right).plus(up)).normalise();
		Ray ray = new Ray(origin, direction);
		return ray;
	}
	
	/**
	 * Calculates the height of the world space viewing plane.
	 * 
	 * @return the world space height of the viewing plane
	 */
	private double calculateHeight() {
		// FOV = tan(h)
		double height = Math.atan(Math.toRadians(fov));
		return height;
	}
	
	/**
	 * Calculated the width of the world space viewing plane.
	 * 
	 * @return the world space width of the viewing plane
	 */
	private double calculateWidth() {
		return (aspectRatio * windowHeight);
	}
	
	/**
	 * Gets the origin of the camera.
	 * 
	 * @return point of the origin for the camera
	 */
	public Point getViewPoint() {
		return origin;
	}
	
	/**
	 * Gets the up vector of the camera.
	 * 
	 * @return the view up vector
	 */
	public Vector getVUV() {
		return viewUp;
	}
	
	/**
	 * Gets the right vector of the camera.
	 * 
	 * @return the view right vector
	 */
	public Vector getVRV() {
		return viewRight;
	}
	
	/**
	 * Gets the looking direction of the camera as a vector.
	 * 
	 * @return the view plane normal
	 */
	public Vector getVPN() {
		return viewPlaneNormal;
	}
	
	/**
	 * Gets the height of the world space window.
	 * 
	 * @return the height in world space
	 */
	public double getHeight() {
		return windowHeight;
	}
	
	/** 
	 * Gets the width of the world space window.
	 * 
	 * @return the width in world space
	 */
	public double getWidth() {
		return windowWidth;
	}
	
}
