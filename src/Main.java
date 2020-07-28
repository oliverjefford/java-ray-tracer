import java.awt.Color;
import java.util.ArrayList;

public class Main {

	public static void main(String[] args) throws Exception {
		long startTime = System.currentTimeMillis();
		int imageWidth = 1000;
		int imageHeight = 1000;
		Image image = new Image(imageWidth, imageHeight);		
		
		double ambient, diffuse, specular;
		
		// Colours ----------------------------------------------------------
		Color cyan = Color.CYAN;
		Color green = Color.GREEN;
		Color magenta = Color.MAGENTA;
		Color yellow = Color.YELLOW;
		Color white = Color.WHITE;
		Color red = Color.RED;
		Color gold = new Color(212, 175, 55);
		Color silver = new Color(192,192,192);
		Color orange = Color.ORANGE;
		Color lightGrey = Color.LIGHT_GRAY;
		Color darkRed = new Color(139,0,0);
		Color bronze = new Color(205,127,50);
		Color darkGreen = new Color(0,100,0);

		// Buddha Camera ----------------------------------------------------
//		Point origin = new Point(0,0.2,0.5);
//		Vector up = new Vector(0,1,0);
//		Point lookAt = new Point(0,0.15,0);
		// Bunny Camera -----------------------------------------------------
		Point origin = new Point(0.1,0.2,0.5);
		Vector up = new Vector(0,1,0);
		Point lookAt = new Point(0,0.1,0);
		Scene scene = new Scene();
		Color[] colours = {darkGreen};

		scene.readSceneFile("Dragon_Points_VeryHigh.txt","Dragon_Tris_VeryHigh.txt",colours);
	
		// Normal Camera ----------------------------------------------------
//		Point origin = new Point(0,0,5);
//		Vector up = new Vector(0,1,0);
//		Point lookAt = new Point(0,0,0);
		
		double fov = 15;
		double aspectRatio = image.getAspectRatio();
		Camera camera = new Camera(origin, up, lookAt, fov, aspectRatio);
		// -----------------------------------------------------------------
		
		// Following block used to test scenes -----------------------------
		
		Point floor = new Point(0,-1,0);
		Vector surface = new Vector(0,1,0);
		ambient = 0.4;
		diffuse = 0.5;
		specular = 0;
		Plane plane  = new Plane(floor, surface, red, ambient, diffuse, specular);
		
		Point centre = new Point(0, -0.5, -4);
		double radius = 0.5;
		Sphere s1 = new Sphere(centre, radius, orange, 0.4, 0.6, 0);
		
		Point c2 = new Point(1.4,-0.65, 2);
		double r2 = 0.35;
		Sphere s2 = new Sphere(c2, r2, lightGrey, 0.4, 0.6, 0.4);
		
		
		Point p1 = new Point(-2, 2.5, -8);
		Point p2 = new Point(-2, -1,-8);
		Point p3 = new Point(-2, -1, 6);
		Triangle t1 = new Triangle(p1, p2, p3, cyan, 0.4, 0.4, 0);
		t1.invertNormal();
		
		Point p4 = new Point(-2, 2.5, 6);
		Triangle t2 = new Triangle(p1,p3,p4, cyan, 0.4,0.4,0);
		t2.invertNormal();
		
		Point p5 = new Point(2, -1, -8);
		Triangle t3 = new Triangle(p1,p2,p5, red, 0.4, 0.4,0);

		Point p6 = new Point(2,2.5,-8);
		Triangle t4 = new Triangle(p5,p1,p6, red, 0.4, 0.4,0);
		t4.invertNormal();
		
		Point p7 = new Point(2,-1,6);
		Triangle t5 = new Triangle(p7,p5,p6, lightGrey, 0.4, 0.4,0);
		
		Point p8 = new Point(2,2.5,6);
		Triangle t6 = new Triangle(p7,p6, p8, lightGrey, 0.4,0.4,0);
		
		Triangle t7 = new Triangle(p7,p8,p3, white, 0.4,0.4,0);
		Triangle t8 = new Triangle(p3,p4,p8, white, 0.4,0.4,0);
		t8.invertNormal();
		
		Triangle roof1 = new Triangle(p8,p6,p4, white, 0.4,0.4,0);
		Triangle roof2 = new Triangle(p4,p1,p6,white,0.4,0.4,0);
		roof2.invertNormal();
		
//		Point min = new Point(-1, -1, 0);
//		Point max = new Point(1, 0, 4.5);
//		Box box = new Box(min, max);
//		Scene scene = new Scene(box);
//		Scene scene = new Scene();
		
//		scene.addToShapesList(plane);
//		scene.addToShapesList(t1);
//		scene.addToShapesList(t2);
//		scene.addToShapesList(t3);
//		scene.addToShapesList(t4);
//		scene.addToShapesList(t5);
//		scene.addToShapesList(t6);
//		scene.addToShapesList(t7);
//		scene.addToShapesList(t8);
//		scene.addToShapesList(roof1);
//		scene.addToShapesList(roof2);

		
//		scene.addToShapesList(s1);
//		scene.addToShapesList(s2);



		// ------------------------------------------------------------------

		// JACKS MEGA GAY SCENE  - - - - - - - - - - - - - - - - - - -
		
//		Point p1 = new Point(0,-0.3,-2);
//        Point p2 = new Point(1.5,-0.5,0);
//        Point p3 = new Point(-1.5,-0.5,0);
//        Point p4 = new Point(0,-0.6,2);
//        
//		Color steel = new Color(192,192,192);
//
//        Sphere s1 = new Sphere(p1, 0.7, Color.LIGHT_GRAY, 0.4, 0.4, 1);
//        Sphere s2 = new Sphere(p2, 0.5, Color.MAGENTA, 0.2, 0.5, 0);
//        Sphere s3 = new Sphere(p3, 0.5, Color.ORANGE, 0.2, 0.5, 0);
//        Sphere s4 = new Sphere(p4, 0.4, Color.BLUE, 0.2, 0.5, 0);
//        
//		scene.addToShapesList(s1);
//		scene.addToShapesList(s2);
//		scene.addToShapesList(s3);
//		scene.addToShapesList(s4);
        // ------------------------------------------------------------------
		
		// Light sources ----------------------------------------------------
//		Point position = new Point(3, 4, 10);
//		double intensity = 1;
//		Light light = new Light(position, intensity, white);
		
		Point lightPos = new Point(1,7,7);
		double intensity2 = 1;
		Light light2 = new Light(lightPos, intensity2, white);
		
//		scene.addToLightsList(light);
		scene.addToLightsList(light2);
		// ------------------------------------------------------------------
		


		
		System.out.println("Ray tracing scene now");
		image.rayTrace(camera, scene);
		image.save();
		System.out.println(Image.intersectionCheckCounter + " intersection checks");

		long completionTime = System.currentTimeMillis();
		long time = completionTime - startTime;

		System.out.println("Done");
		System.out.println("Time taken: " + time/1000.0);
	}

}
