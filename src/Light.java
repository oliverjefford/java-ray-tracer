import java.awt.Color;
/**
 * Represents a light within the scene to illuminate the scene.
 * 
 * @author Oliver Jefford
 *
 */
public class Light {
	
	Point position;
	Color colour;
	double intensity;
	Vector lightDirection;
	
	/**
	 * Creates a light object within the scene.
	 * 
	 * @param position - Origin of the light
	 * @param intensity - Intensity of light emitted
	 * @param colour - colour of the light
	 */
	public Light(Point position, double intensity, Color colour) {
		this.position = position;
		this.intensity = intensity;
		this.colour = colour;
	}
	
	/**
	 * Retrieves the intensity of light being emitted.
	 * 
	 * @return the intensity of the light source
	 */
	public double getIntensity() {
		return intensity;
	}
	
	/**
	 * Retrieves the position of the light in the scene.
	 * 
	 * @return the position of the light
	 */
	public Point getPosition() {
		return position;
	}
	
	/**
	 * Retrieves the direction of the light in the scene.
	 * 
	 * @return the direction vector of light
	 */
	public Vector getLightDirection() {
		return lightDirection;
	}
	
	/**
	 * Applies an attenuation factor to the light striking scene objects.
	 * 
	 * @param distance - Distance the light travels to the intersection point
	 * @return the new intensity 
	 */
	public double attenuation(double distance) {
		return 1.0 / distance;
	}
}
