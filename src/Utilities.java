
import java.awt.Color;
import java.util.Random;

public class Utilities {

	static Random r = new Random();

	public static void wait(int millis) {
		try { Thread.sleep(millis); }
		catch (InterruptedException e) { e.printStackTrace(); }
	}

	public static Color randomColor() {
		return new Color( r.nextInt(255), r.nextInt(255), r.nextInt(255) );
	}

	public static Color randomDarkColor() {
		return new Color( r.nextInt(45), r.nextInt(45), r.nextInt(45) );
	}

	public static boolean probability(int prozent) {
		if (prozent == 0) { return false; }
		return r.nextInt(100) <= prozent;
	}
	public static boolean intersectsCircleRect(Ball ball, Rectangle rectangle) {
        
        double kreisX = ball.x; // CenterX!
        double kreisY = ball.y; // CenterY!
        double kreisRadius = ball.radius;
        double p1_x = rectangle.x,                  p1_y = rectangle.y;
        double p2_x = rectangle.x + rectangle.width, p2_y = rectangle.y;
        double p3_x = rectangle.x,                  p3_y = rectangle.y + rectangle.height;
        double p4_x = rectangle.x + rectangle.width, p4_y = rectangle.y + rectangle.height;
        
        return intersectsPointRectangle(kreisX, kreisY, rectangle)
            || intersectsPointCircle(p1_x, p1_y, ball)
            || intersectsPointCircle(p2_x, p2_y, ball)
            || intersectsPointCircle(p3_x, p3_y, ball)
            || intersectsPointCircle(p4_x, p4_y, ball);
            
	}

	public static boolean intersectsPointRectangle(double x, double y, Rectangle rechteck) {
		return (x > rechteck.x && x < rechteck.x + rechteck.width) && (y > rechteck.y && y < rechteck.y + rechteck.height);
	}

	public static boolean intersectsPointCircle(double x, double y, Ball ball) {
		double kreisX = ball.x; // CenterX!
		double kreisY = ball.y; // CenterY!
		double distX = Math.abs(x * kreisX);
		double distY = Math.abs(y * kreisY);
		double distSquared = Math.pow(distX, 2) + Math.pow(distY, 2);
		double radiusSquared = Math.pow(ball.radius, 2);
		return distSquared < radiusSquared;
	}
}