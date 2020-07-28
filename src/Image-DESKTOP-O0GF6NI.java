
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.DecimalFormat;

import javax.imageio.ImageIO;

public class Image {

	private int imageHeight;
	private int imageWidth;
	private double aspectRatio = 16 / 9;

	private static File image;
	private static BufferedImage imageData;
	
	private final int MAX_DEPTH = 2;

	public Image(int height, int width) {
		imageHeight = height;
		imageWidth = width;

		image = new File("result.png");
		imageData = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	}

	public void save() {
		try {
			ImageIO.write(imageData, "PNG", image);
		} catch (Exception e) {
			System.out.println("Unable to save image");
			System.exit(0);
		}
	}

	public void rayTrace(Camera camera, Scene scene) {
		double width = (double) imageWidth;
		double height = (double) imageHeight;

		Color colour = null;
		// Loop through all pixels in image for any intersections
		for (int y = 0; y < imageHeight; y++) {
			for (int x = 0; x < imageWidth; x++) {
				Point pointOnImage = findPointOnImage((double) x, (double) y, width, height);
				Ray ray = camera.fireAt(pointOnImage);
				colour = trace(camera, ray, scene, colour, 0);
				imageData.setRGB(x, y, colour.getRGB());	
			}
		}
	}
	
	public Color trace(Camera camera, Ray ray, Scene scene, Color colour, int round) {
		Shape shape = scene.isIntersectedBy(ray);
		Light light = scene.getLightsList(0);
		Ray shadowRay, reflectedRay, refractedRay = null;
		
		if (round == MAX_DEPTH) 
			return colour;
		
		if (shape == null) {
			if (round > 0) {
				return colour;
			} else {
				return Color.BLACK;
			}
		} else {
			Point pointOfIntersection = ray.getOrigin().add(ray.getDirection().times(ray.getDistance()));
			Vector lightDirection = new Vector(pointOfIntersection, light.getPosition()).normalise();
			
			double ambientReflection = shape.getAmbient();
			double ambient = ambientReflection * light.getIntensity();
			
			colour = shape.getColour();
			
			double diffuseReflection = shape.getDiffuse();
			double specularReflection = shape.getSpecular();
			double shine = 110;			
			
			Vector shapeNormal = shape.getNormal();
			Vector vectorToCamera = new Vector(pointOfIntersection, camera.getViewPoint()).normalise();
			
			// 2(L.N) * N - L
			Vector lightReflection = shapeNormal.minus(lightDirection).times(2 * lightDirection.dot(shapeNormal)).normalise();
			Vector rayReflection = (shapeNormal.minus(ray.getDirection()).times(2 * ray.getDirection().dot(shapeNormal))).normalise().negate();

			// Fire reflective ray
			// Fire transmission ray
			double diffuse = diffuseReflection * light.getIntensity() * lightDirection.dot(shapeNormal);
			double specular = specularReflection * light.getIntensity() * Math.pow(lightDirection.dot(shapeNormal), shine);
			double phong = ambient + diffuse + specular;
			colour = performColourMap(colour, phong);
		
			round++;

			if (shape.getSpecular() > 0) {
				reflectedRay = new Ray(pointOfIntersection, rayReflection); 
				colour = trace(camera, reflectedRay, scene, colour, round);
				colour = performColourMap(colour, phong);
			}

			shadowRay = new Ray(pointOfIntersection, lightDirection);
			Shape inShade = scene.isIntersectedBy(shadowRay);
			if (inShade != null && inShade != shape) {
				return performColourMap(colour, phong);
			}
			
			return colour;
		}
		
	}

	private Color performColourMap(Color colour, double phong) {
		double red = colour.getRed();
		double green = colour.getGreen();
		double blue = colour.getBlue();
		int r,g,b;

		r = (int) (red * phong);
		if (r > 255) r = 255;
		if (r < 0) r = 0;

		g = (int) (green * phong);
		if (g > 255) g = 255;
		if (g < 0) g = 0;

		b = (int) (blue * phong);
		if (b > 255) b = 255;
		if (b < 0) b = 0;
		
		return new Color(r,g,b);
	}
	
	public double getAspectRatio() {
		double findAR = (double) imageWidth / (double) imageHeight;
		aspectRatio = findAR;
		return aspectRatio;
	}

	private Point findPointOnImage(double x, double y, double imageWidth, double imageHeight) {
		double xPos = (((2.0 * (x + 0.5)) / imageWidth) - 1.0) * aspectRatio;
		double yPos = (((-2.0 * (y + 0.5)) / imageHeight) + 1.0); // Reversed formula to compensate for parallel projection
															// viewing
		double zPos = 0;
		return new Point(xPos, yPos, zPos);
	}
}
