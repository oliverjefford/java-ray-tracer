/**
 * A node within a KD tree containing a bounding volume, shapes list and left and right
 * children. 
 * 
 * @author Oliver Jefford
 *
 */
public class Node {

	private Box volume;
	private Node left;
	private Node right;
	private Shape shapes[];
	
	/**
	 * Constructs a node with an allocated shapes list and the bounding volume
	 * surrounding the shapes.
	 * 
	 * @param volume - Surrounding bounding box
	 * @param shapes - List of shapes within the volume
	 */
	public Node(Box volume, Shape shapes[]) {
		this.volume = volume;
		this.shapes = shapes;
		left = null;
		right = null;
	}

	/**
	 * Sets the left child of the current node.
	 * 
	 * @param left node
	 */
	public void setLeft(Node left) {
		this.left = left;
	}
	
	/**
	 * Sets the right child of the current node.
	 * 
	 * @param right node
	 */
	public void setRight(Node right) {
		this.right = right;
	}
	
	/**
	 * Retrieves the left child of the current node.
	 * 
	 * @return the left child
	 */
	public Node getLeft() {
		return left;
	}
	
	/**
	 * Retrieves the right child of the current node.
	 * 
	 * @return the right child
	 */
	public Node getRight() {
		return right;
	}
	
	/**
	 * Retrieves the shapes list within the current node.
	 * 
	 * @return all shapes within the bounded volume
	 */
	public Shape[] getShapes() {
		return shapes;
	}
	
	/**
	 * Checks if the node is a leaf. Leaf only if has no children.
	 * 
	 * @return true if a leaf
	 * 	       false otherwise
	 */
	public boolean isLeaf() {
		if (left == null && right == null) {
			return true;
		}
		return false;
	}
	
	/**
	 * Retrieves the volume of the current node surround all shapes.
	 * 
	 * @return the volume
	 */
	public Box getVolume() {
		return volume;
	}
	
	/**
	 * Performs ray-object intersection for all shapes within the current node.
	 * 
	 * @param ray - casted ray to check for intersections
	 * @return the shape at the intersection point
	 */
	public Shape isIntersectedBy(Ray ray) {
		Shape closest = null;
		double closestDistance = Double.MAX_VALUE;
		Image.intersectionCheckCounter += shapes.length;
		for (int i = 0; i < shapes.length; i++) {
			if (shapes[i].isIntersectedBy(ray) != -1) {
				if (ray.getDistance() <= closestDistance) {
					closest = shapes[i];	
					closestDistance = ray.getDistance();
				}
			}
		}
		return closest;
	}
}
