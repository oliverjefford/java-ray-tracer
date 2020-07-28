
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.DecimalFormat;

import javax.imageio.ImageIO;
/**
 * Performs the tracing method and calculates the intensity for each pixel in the image.
 * Distributed anti-aliasing method included to reduce jagged line. Handles object as a 
 * buffered image to manipulate each pixel and store the image to a file.
 * 
 * @author Oliver Jefford
 *
 */
public class Image {

	private int imageHeight;
	private int imageWidth;
	private double aspectRatio = 16 / 9;

	private static File image;
	private static BufferedImage imageData;
	static long intersectionCheckCounter = 0;
	private final int MAX_DEPTH = 1;

	/**
	 * Constructs a buffered image object of resolution width x heigth .
	 * 
	 * @param width - The width of the image 
	 * @param height - The height of the image
	 */
	public Image(int width, int height) {
		imageHeight = height;
		imageWidth = width;

		image = new File("result.png");
		imageData = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	}

	/**
	 * Saves the buffered image to the file path.
	 */
	public void save() {
		try {
			ImageIO.write(imageData, "PNG", image);
		} catch (Exception e) {
			System.out.println("Unable to save image");
			System.exit(0);
		}
	}

	/**
	 * Loops through each pixel within the image and casts a ray through each pixel at a given
	 * point. Sets the intensity of each pixel dependent on the traced ray.
	 * 
	 * @param camera - The viewpoint of the image
	 * @param scene - The scene to determine what is visible from the viewpoint
	 */
	public void rayTrace(Camera camera, Scene scene) {
		double width = (double) imageWidth;
		double height = (double) imageHeight;
		
		// Construct tree using the depth
		KDTree tree = scene.buildTree(1000);
		Node root = tree.getRoot();

		System.out.println("Tree constructed.");
		System.out.println(scene.getShapesListLength() + " total shapes");
		Color colour = null;
		
		// Loop through all pixels in image for any intersections
		for (int y = 0; y < imageHeight; y++) {
			for (int x = 0; x < imageWidth; x++) {
				Point pointOnImage = findPointOnImage((double) x, (double) y, width, height, 0.25, 0.25);
				Point pointOnImage2 = findPointOnImage((double) x, (double) y, width, height, 0.75, 0.25);
				Point pointOnImage3 = findPointOnImage((double) x, (double) y, width, height, 0.25, 0.75);
				Point pointOnImage4 = findPointOnImage((double) x, (double) y, width, height, 0.75, 0.75);
				
				Ray ray = camera.fireAt(pointOnImage);
				Ray ray2 = camera.fireAt(pointOnImage2);	
				Ray ray3 = camera.fireAt(pointOnImage3);	
				Ray ray4 = camera.fireAt(pointOnImage4);	
				
				Color c1 = trace(camera, ray, scene, tree, root, colour, 0);	
				Color c2 = trace(camera, ray2, scene, tree, root, colour, 0);		
				Color c3 = trace(camera, ray3, scene, tree, root, colour, 0);		
				Color c4 = trace(camera, ray4, scene, tree, root, colour, 0);		

				colour = getPixelIntensity(c1,c2,c3,c4);
				imageData.setRGB(x, y, colour.getRGB());	
			}
		}
	}
	
	/**
	 * Calculates the intensity of each pixel by checking for intersections in the scene. Uses
	 * the colour of each intersection point to determine the intensity of each pixel within
	 * the image.
	 * 
	 * @param camera - Viewpoint of the image
	 * @param ray - Ray casted from the camera into the scene
	 * @param scene - Scene containing all objects
	 * @param tree - Acceleration structure to allow fast traversal for efficiency
	 * @param root - The initial bounding box surrounding the object(s)
	 * @param colour - The colour obtained at the intersection points
	 * @param round - Depth of the rays in the scene
	 * @return Colour intensity of the pixel from the traced ray
	 */
	public Color trace(Camera camera, Ray ray, Scene scene, KDTree tree, Node root, Color colour, int round) {
		// Return colour if at max depth
		if (round == MAX_DEPTH) 
			return colour;
		// Traverse through tree to find intersections
		Shape shape = tree.findShapeWith(root, ray);
		
		Light light = scene.getLightsList(0);
		Ray shadowRay, reflectedRay = null;
		
		if (shape == null) {
			if (round > 0) {
				return colour;
			} else {
				return Color.BLACK;
			}
		}
		
		Point pointOfIntersection = ray.getIntersectionPoint();
		Vector lightDirection = new Vector(pointOfIntersection, light.getPosition()).normalise();
			
		double ambientReflection = shape.getAmbient();
		double ambient = ambientReflection * light.getIntensity();		
		double diffuseReflection = shape.getDiffuse();
		double specularReflection = shape.getSpecular();
		double shine = 400;			
		
		colour = shape.getColour();
		Vector shapeNormal = shape.getNormal();
			
		// 2(L.N) * N - L
		Vector rayReflection = (shapeNormal.minus(ray.getDirection()).times(2.0 * ray.getDirection().dot(shapeNormal))).normalise().negate();

		// Calculate Phong value using formula
		double diffuse = diffuseReflection * light.getIntensity() * lightDirection.dot(shapeNormal);
		double specular = specularReflection * light.getIntensity() * Math.pow(lightDirection.dot(shapeNormal), shine);
		double phong = ambient + diffuse + specular;		
		colour = performColourMap(colour, phong);

		// Fire reflective ray if object is reflective
		if (shape.getSpecular() > 0) {
			reflectedRay = new Ray(pointOfIntersection, rayReflection); 
			colour = trace(camera, reflectedRay, scene, tree, root, colour, round + 1);
			colour = performColourMap(colour, phong);
		}

		shadowRay = new Ray(pointOfIntersection, lightDirection);
		// Check for shadow rays in scene
//		Shape inShade = tree.findShapeWith(root, shadowRay);
//		if (inShade != null && inShade != shape) {
//			return performColourMap(colour, ambient);
//		}
		
		return colour;
	}

	/**
	 * Perform a colour map to bring colours back into range if necessary after applying
	 * Phong value to each RGB value.
	 * 
	 * @param colour - RGB values to remap
	 * @param phong - Phong value to apply
	 * @return colour - pixel intensity at intersection point
	 */
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
	
	/**
	 * Calculates the average intensity value from n traced rays. Prevents aliased issues
	 * during rendering.
	 * 
	 * @param c1 - First traced ray colour value
	 * @param c2 - Second traced ray colour value
	 * @param c3 - Third traced ray colour value
	 * @param c4 - Fourth traced ray colour value
	 * @return average - average of all colours in method
	 */
	private Color getPixelIntensity(Color c1, Color c2, Color c3, Color c4) {
		int red = (int) (c1.getRed() + c2.getRed() + c3.getRed() + c4.getRed()) / 4;
		int green = (int) (c1.getGreen() + c2.getGreen() + c3.getGreen() + c4.getGreen()) / 4;
		int blue = (int) (c1.getBlue() + c2.getBlue() + c3.getBlue() + c4.getBlue()) / 4;

		if (red > 255) {
			red = 255;
		} else if (red < 0) {
			red = 0;
		}
		
		if (green > 255) {
			green = 255;
		} else if (green < 0) {
			green = 0;
		}
		
		if (blue > 255) {
			blue = 255;
		} else if (blue < 0) {
			blue = 0;
		}
		return new Color(red,green,blue);
	}
	
	/**
	 * Calculate the aspect ratio of the image using the width and height.
	 * 
	 * @return aspect ratio of the image
	 */
	public double getAspectRatio() {
		double findAR = (double) imageWidth / (double) imageHeight;
		aspectRatio = findAR;
		return aspectRatio;
	}

	/**
	 * Calculates the screen coordinate in the world space coordinates to cast the ray
	 * through the pixel.
	 * 
	 * @param x - Pixel x
	 * @param y - Pixel y
	 * @param imageWidth - Number of pixels in the width
	 * @param imageHeight - Number of pixels in the height
	 * @param xOffset - Offset for x to cast the ray through the pixel
	 * @param yOffset - Offset for y to cast the ray through the pixel 
	 * @return a point in world space coordinates
	 */
	private Point findPointOnImage(double x, double y, double imageWidth, double imageHeight, double xOffset, double yOffset) {
		double xPos = ((((2.0 * (x + xOffset)) / imageWidth) - 1.0) * aspectRatio) * -1.0;
		double yPos = (((-2.0 * (y + yOffset)) / imageHeight) + 1.0); // Reversed formula to compensate for parallel projection
																  // viewing
		return new Point(xPos, yPos, -1);
	}
}
