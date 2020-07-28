import java.util.ArrayList;
/**
 * Constructs a 3D tree using shapes and splits each axis in a rotational manner.
 * 
 * @author Oliver Jefford
 *
 */
public class KDTree {

	private Node root;
	private final int MAX_DEPTH;
	private final int MIN_SHAPES_SIZE = 40;
	int numShapes = 0;
	
	/**
	 * Construct a balanced KD tree using a maximum depth, initial bounding box, and the entire 
	 * list of shapes within the scene.
	 * 
	 * @param shapes - all shapes within the scene
	 * @param maxDepth - the maximum depth of the tree to be constructed
	 * @param volume - the initial bounding box of the tree
	 */
	public KDTree(Shape shapes[], int maxDepth, Box volume) {
		MAX_DEPTH = maxDepth;
		root = constructTree(shapes, 0, volume);
	}
	
	/**
	 * Recursively constructs the tree sorting shapes based on midpoint on a rotating axis for
	 * each depth of the tree. 
	 * 
	 * @param shapes - the shapes to be assigned to the current node
	 * @param depth - the current depth of the tree
	 * @param volume - the current volume assigned to the current node
	 * @return the root of the tree
	 */
	private Node constructTree(Shape shapes[], int depth, Box volume) {
		Node currentNode, left, right;
		currentNode = new Node(volume, shapes);
		if (depth == MAX_DEPTH || shapes.length <= MIN_SHAPES_SIZE) {
			return currentNode;
		} 
			
		int axis = depth % 3;
		Shape sortedShapes[] = quickSort(shapes, 0, shapes.length - 1, axis);
		int median = (int) (sortedShapes.length - 1) / 2;

		Point leftMax = null, rightMin = null;
		Point currentMin = volume.getMin();
		Point currentMax = volume.getMax();
		// Split the bounding box at median and set new left and right boxes
		if (axis == 0) {
			leftMax = new Point(sortedShapes[median].getMidPoint().getX(), currentMax.getY(), currentMax.getZ());
			rightMin = new Point(sortedShapes[median].getMidPoint().getX(), currentMin.getY(), currentMin.getZ());					
		} else if (axis == 1) {
			leftMax = new Point(currentMax.getX(), sortedShapes[median].getMidPoint().getY(), currentMax.getZ());
			rightMin = new Point(currentMin.getX(), sortedShapes[median].getMidPoint().getY(), currentMin.getZ());
		} else if (axis == 2) {
			leftMax = new Point(currentMax.getX(), currentMax.getY(), sortedShapes[median].getMidPoint().getZ());
			rightMin = new Point(currentMin.getX(), currentMin.getY(), sortedShapes[median].getMidPoint().getZ());
		}
		
		ArrayList<Shape> leftList = new ArrayList<Shape>();
		ArrayList<Shape> rightList = new ArrayList<Shape>();
		
		// Loop through sorted shapes array to split the array into left and right
		for(int i = 0; i < sortedShapes.length; i++) {
			if (axis == 0) { // x axis
				// If the min x value is in the left box, add to left array
				if (sortedShapes[i].getMinX() <= leftMax.getX()) {
					leftList.add(sortedShapes[i]);
				}
				// If x max is in right, add right
				if (sortedShapes[i].getMaxX() > rightMin.getX()) { 
					rightList.add(sortedShapes[i]);
				} 
				
			} else if (axis == 1) { // y axis
				// If min y is in left box, add left
				if (sortedShapes[i].getMinY() <= leftMax.getY()) {
					leftList.add(sortedShapes[i]);
				} 
				// If y max is in right, add right
				if (sortedShapes[i].getMaxY() > rightMin.getY()) { 
					rightList.add(sortedShapes[i]);
				}
				
			} else if (axis == 2) { // z axis
				// If min z is in left box, add left
				if (sortedShapes[i].getMinZ() <= leftMax.getZ()) {
					leftList.add(sortedShapes[i]);
				} 
				// If z max is in right box, add right
				if (sortedShapes[i].getMaxZ() > rightMin.getZ()) { 
					rightList.add(sortedShapes[i]);
				}
				
			} 
		}
		
		// Construct fixed arrays and move left and right into arrays
		Shape leftShapes[] = new Shape[leftList.size()];
		Shape rightShapes[] = new Shape[rightList.size()];

		Box leftVolume = new Box(currentMin, leftMax);
		Box rightVolume = new Box(rightMin, currentMax);

		for (int l = 0; l < leftList.size(); l++) {
			leftShapes[l] = leftList.get(l);
		}
		
		for (int r = 0; r < rightList.size(); r++) {
			rightShapes[r] = rightList.get(r);
		}

		left = constructTree(leftShapes, depth + 1, leftVolume);
		currentNode.setLeft(left);
		
		right = constructTree(rightShapes, depth + 1, rightVolume);
		currentNode.setRight(right);
		return currentNode;
	}
	
	/**
	 * Retrieves the root of the tree.
	 * 
	 * @return the root
	 */
	public Node getRoot() {
		return root;
	}
	
	/**
	 * Finds intersections with any shapes within the tree by traversing the necessary nodes.
	 * Only checks for intersections if the node is a leaf within the tree.
	 * 
	 * @param node - the current node to check for intersections
	 * @param ray - the casted ray to check for intersections
	 * @return the shape at the point of intersection 
	 */
	public Shape findShapeWith(Node node, Ray ray) {
		Box volume = node.getVolume();
		Image.intersectionCheckCounter += 1;
		if (!volume.isIntersectedBy(ray)) {
			return null;
		} 
		
		Shape left = null, right = null;
		if (node.isLeaf()) {
			return node.isIntersectedBy(ray);
		} 
		
		double leftDistance = 0;
		double rightDistance = 0;

		left = findShapeWith(node.getLeft(), ray);
		leftDistance = ray.getDistance();
		
		right = findShapeWith(node.getRight(), ray);
		rightDistance = ray.getDistance();
		
		if (left == null && right == null) {
			return null;
		} else if (left != null && right != null) {
			if (leftDistance <= rightDistance) {
				return left;
			} else {
				return right;
			}
		} else if (left == null) {
			return right;
		} else {
			return left;
		}
	}
	
	/**
	 * Shuffles numbers depending on sorting axis and the midpoint of each shape.
	 * 
	 * @param shapes - The list of shapes containing midpoints for checking
	 * @param low - the beginning index
	 * @param high - the end index
	 * @param axis - axis to sort the list of shapes on
	 * @return the new index to sort
	 */
	private int pivotShuffle(Shape shapes[], int low, int high, int axis) {
		double pivot = 0;
		if (axis == 0) {
			pivot = shapes[high].getMidPoint().getX();
		} else if (axis == 1) {
			pivot = shapes[high].getMidPoint().getY();
		} else if (axis == 2) {
			pivot = shapes[high].getMidPoint().getZ();
		}
		
		int i = low-1;
		double jValue = 0;
		for (int j = low; j < high; j++) {
			// Orders list based on axis
			if (axis == 0) {
				jValue = shapes[j].getMidPoint().getX();
			} else if (axis == 1) {
				jValue = shapes[j].getMidPoint().getY();
			} else if (axis == 2) {
				jValue = shapes[j].getMidPoint().getZ();
			}
			
			// Performs a swap
			if (jValue < pivot) {
				i++;
				Shape temp = shapes[i];
				shapes[i] = shapes[j];
				shapes[j] = temp;
			}
		}
		
		Shape temp = shapes[i+1];
		shapes[i+1] = shapes[high];
		shapes[high] = temp;
		return i+1;
	}
	
	/**
	 * Recursively sorts the shapes list based on the axis being split in the tree.
	 * 
	 * @param shapes - list of shapes to sort
	 * @param low - beginning index
	 * @param high - ending index
	 * @param axis - splitting axis 
	 * @return sorted list of shapes based on midpoints
	 */
	private Shape[] quickSort(Shape shapes[], int low, int high, int axis) {
		if (low < high) {
			int index = pivotShuffle(shapes, low, high, axis);
			
			// Split lists in half and sort
			quickSort(shapes, low, index - 1, axis);
			quickSort(shapes, index + 1, high, axis);
		}

		return shapes;
	}

}

