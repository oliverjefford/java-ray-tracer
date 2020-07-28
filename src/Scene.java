import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;
/**
 * A scene containing a list of objects either programmatically defined, or read by 
 * a model file containing triangles.
 * 
 * @author Oliver Jefford
 *
 */
public class Scene {

	private ArrayList<Shape> shapesList = new ArrayList<>();
	private ArrayList<Point> pointList = new ArrayList<>();
	private ArrayList<Light> lightsList = new ArrayList<>();
	private Shape shapes[];
	private Box volume;
	private KDTree tree;
	
	/**
	 * Empty constructor to create a scene object.
	 */
	public Scene() {}
	
	/**
	 * Create a scene object with an initial bounding volume.
	 * @param volume - Initial bounding box surround object(s)
	 */
	public Scene(Box volume) {
		this.volume = volume;
	}
	
	/**
	 * Adds a shape to the shape list.
	 * @param shape - The shape to be added to the list
	 */
	public void addToShapesList(Shape shape) {
		shapesList.add(shape);
	}
	
	/**
	 * Adds a point to the point list.
	 * @param point - The point to be added to the list
	 */
	public void addToPointList(Point point) {
		pointList.add(point);
	}
	
	/**
	 * Adds a light to the light list.
	 * @param light - The light to be added to the list
	 */
	public void addToLightsList(Light light) {
		lightsList.add(light);
	}
	
	/**
	 * Retrieves the shape from the list from the specified index.
	 * @param position - index of the shape
	 * @return the shape at the position index
	 */
	public Shape getShapesList(int position) {
		return shapesList.get(position);
	}
	
	/**
	 * Returns the length of the shape list.
	 * @return size of the list
	 */
	public int getShapesListLength() { 
		return shapesList.size();
	}
	
	/**
	 * Retrieves the entire shapes list within the scene.
	 * @return all shapes from the scene
	 */
	public Shape[] getShapes() {
		return shapes;
	}

	/**
	 * Retrieves the light at the specified index.
	 * @param position - index of the light
	 * @return the light at the positioned index
	 */
	public Light getLightsList(int position) {
		return lightsList.get(position);
	}
	
	/**
	 * Gets the size of the list of lights.
	 * @return the number of lights in the scene
	 */
	public int getLightsListLength() {
		return lightsList.size();
	}
	
	/**
	 * Checks for any intersections with a singular bounding box.
	 * @param ray - The ray checking for intersections
	 * @return true if intersected
	 * 		   false if did not intersect
	 */
	public boolean intersectsBoundingBox(Ray ray) {
		return volume.isIntersectedBy(ray);
	}
	
	/**
	 * Checks through all shapes within the scene for intersection.
	 * Performed by ray-object intersection.
	 * 
	 * @param ray - casted ray into the scene
	 * @return The shape at the point of the nearest intersection
	 */
	public Shape isIntersectedBy(Ray ray) {
		Shape closest = null;
		double smallestDistance = Double.MAX_VALUE;
		for (int i = 0; i < shapesList.size(); i++) {
			if (shapesList.get(i).isIntersectedBy(ray) != -1) {
				if (ray.getDistance() < smallestDistance) {
					smallestDistance = ray.getDistance();
					closest = shapesList.get(i);
				}
			}
		}
		return closest;
	}
	
	/**
	 * Constructs a tree using the shapes from the scene.
	 * 
	 * @param maxDepth - The maximum depth of the tree
	 * @return the constructed balanced tree 
	 */
	public KDTree buildTree(int maxDepth) {
		tree = new KDTree(shapes, maxDepth, volume);
		return tree;
	}
	
	/**
	 * Return the initial bounding box of the scene.
	 * @return initial bounding box in the scene
	 */
	public Box getVolume() {
		return volume;
	}
	
	/**
	 * Read model files in and construct shapes from points and indexes given.
	 * 
	 * @param pointsFilename - the points file to read
	 * @param trianglesFilename - indexed points file to construct the triangles
	 * @param colours - Colours to render each model
	 * @throws Exception - Unable to find the correct file
	 * 
	 */
	public void readSceneFile(String pointsFilename, String trianglesFilename, Color[] colours) throws Exception {
		File pointsFile = new File(pointsFilename);
		BufferedReader pointsFileReader = new BufferedReader(new FileReader(pointsFile));
		double ambient = 0.4;
		double diffuse = 0.7;
		double specular = 0.9;
		
		String line;
		Scanner scanner = null;
		Point point;
		double xMin = Double.MAX_VALUE, yMin = Double.MAX_VALUE, zMin = Double.MAX_VALUE;
		double xMax = Double.MIN_VALUE, yMax = Double.MIN_VALUE, zMax = Double.MIN_VALUE;

		while ((line = pointsFileReader.readLine()) != null) {
			scanner = new Scanner(line);
			double x = scanner.nextDouble();
			if (x < xMin) {
				xMin = x;
			} else if (x > xMax) {
				xMax = x;
			}
			double y = scanner.nextDouble();
			if (y < yMin) {
				yMin = y;
			} else if (y > yMax) {
				yMax = y;
			}
			double z = scanner.nextDouble();
			if (z < zMin) {
				zMin = z;
			} else if (z > zMax) {
				zMax = z;
			}
			point = new Point(x,y,z);
			addToPointList(point);
		}
		
		Point minimum = new Point(xMin,yMin,zMin);
		Point maximum = new Point(xMax,yMax,zMax);
		
		Box volume = new Box(minimum, maximum);
		this.volume = volume;
		pointsFileReader.close();
		 
		File trianglesFile = new File(trianglesFilename);
		BufferedReader trianglesFileReader = new BufferedReader(new FileReader(trianglesFile));
		
		Triangle triangle;
		while ((line = trianglesFileReader.readLine()) != null) {
			scanner = new Scanner(line);
			scanner.next();
			int index0 = scanner.nextInt();
			int index1 = scanner.nextInt();
			int index2 = scanner.nextInt();

			Point v0 = pointList.get(index0);
			Point v1 = pointList.get(index1);
			Point v2 = pointList.get(index2);

			triangle = new Triangle(v0,v1,v2,colours[0], ambient, diffuse, specular);
			addToShapesList(triangle);
		}
		shapes = new Shape[shapesList.size()];
		for (int i = 0; i < shapesList.size(); i++) {
			shapes[i] = shapesList.get(i);
		}
		scanner.close();
		trianglesFileReader.close();
	}

	
}
