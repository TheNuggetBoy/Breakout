import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;


public class Run {
	
	public static RenderFrame window = new RenderFrame("Breakout",640, 360, 2);
	public static PixelCanvas foreground = new PixelCanvas(640, 360);
	public static PixelCanvas background = new PixelCanvas(320, 180);

	public static Random r = new Random();
	
	public static int tick;
	
	public static Player player = new Player();
	public static ArrayList<Ball> balls = new ArrayList<Ball>();
	public static Brick[] bricks = new Brick[14];
	
	public static boolean leftKey;
	public static boolean rightKey;
	
	public static void main(String[] args) {

		//Setup
		
		for (int y = 0; y < background.pixelHeight; y++) {
			for (int x = 0; x < background.pixelWidth; x++) {
				background.setPixel(x, y, Utilities.randomDarkColor());
			}
		}
		
		player.width = 100;
		player.height = 10;
		player.x = foreground.pixelWidth / 2 - player.width / 2;
		player.y = foreground.pixelHeight - player.height;
		player.speed = 4;
		
		int brickSpace = 6;
		for (int i = 0; i < 14; i++) {
			Brick brick = new Brick();
			brick.width = 42;
			brick.height = 16;
			brick.y = 6;
			brick.x = i * brick.width + brickSpace;
			brick.destroyed = false;
			bricks[i] = brick;
			brickSpace += 3;
		}
		
		//Update
		
		while (true) {
			
			tick();
			
			render();

		}
	}
	
	public static void tick() {
		
		// Tick
		
		if (tick < 2) {
			tick++;
		}
		else {
			tick = 0;
		}
		
		// Player - Movement
		
		if (window.isKeyPressed(Key.RIGHT) || window.isKeyPressed(Key.D) || window.isKeyPressed(Key.L)) {
			rightKey = true;
		}
		if (window.isKeyPressed(Key.LEFT) || window.isKeyPressed(Key.A) || window.isKeyPressed(Key.J)) {
			leftKey = true;
		}
		if (window.isKeyPressed(Key.SPACE)) {
			Ball ball = new Ball();
			ball.x = player.x + player.width / 2 + 0.5;
			ball.radius = 8;
			ball.y = foreground.pixelHeight - player.height - ball.radius + 0.5;
			ball.dirX = -3;
			ball.dirY = -3;
			balls.add(ball);
		}
		if (rightKey && !leftKey) {
			if (player.x + player.width + player.speed > foreground.pixelWidth) {
				player.x = foreground.pixelWidth - player.width;
			}
			else {
				player.x += player.speed;
			}
		}
		if (leftKey && !rightKey) {
			if (player.x - player.speed < 0) {
				player.x = 0;
			}
			else {
				player.x -= player.speed;		
			}
		}
		rightKey = false;
		leftKey = false;
		
		// Ball - Movement
		
		for (int i = 0; i < balls.size(); i++) {
			
			balls.get(i).x += balls.get(i).dirX;
			balls.get(i).y += balls.get(i).dirY;
			
			// Collision - Bricks
			
			for (int j = 0; j < bricks.length; j++) {				
				if (bricks[j].destroyed != true) {
					
					if (Utilities.intersectsCircleRect(balls.get(i), bricks[j])) {
					
						bricks[j].destroyed = true;
					
					}
				}
			}
				
			// Collision - Wall
			
			if (balls.get(i).x - balls.get(i).radius + balls.get(i).dirX < 0) {
				balls.get(i).dirX *= -1;
			}
			if (balls.get(i).y - balls.get(i).radius + balls.get(i).dirY < 0) {
				balls.get(i).dirY *= -1;
			}
			if (balls.get(i).x + balls.get(i).radius + balls.get(i).dirX > foreground.pixelWidth) {
				balls.get(i).dirX *= -1;
			}
			if (balls.get(i).y + balls.get(i).radius + balls.get(i).dirY > foreground.pixelHeight) {
				balls.remove(i);
			}
			
			
		}
		
	}
	
	public static void render() {
		
		//CleanUp
		
		if (tick > 1) {
			for (int y = 0; y < background.pixelHeight; y++) {
				for (int x = 0; x < background.pixelWidth; x++) {
					background.setPixel(x, y, Utilities.randomDarkColor());
				}
			}
		}
		for (int y = 0; y < foreground.pixelHeight; y++) {
			for (int x = 0; x < foreground.pixelWidth; x++) {
				int z = r.nextInt(6);
				if (z < 2) {
					foreground.setPixel(x, y, Color.TRANSLUCENT);
				}
			}
		}
		
		// Render - Player
		
		foreground.fillRect(player.x, player.y, player.width, player.height, Color.RED);
		
		// Render - Balls
		
		for (int i = 0; i < balls.size(); i++) {
			for (int y = (int) (balls.get(i).y - balls.get(i).radius); y < balls.get(i).y + balls.get(i).radius; y++) {
				for (int x = (int) (balls.get(i).x - balls.get(i).radius); x < balls.get(i).x + balls.get(i).radius; x++) {
					double d = ((balls.get(i).x - x) * (balls.get(i).x - x)) + ((balls.get(i).y - y) * (balls.get(i).y - y));
					if (!(d > balls.get(i).radius * balls.get(i).radius)){
						foreground.setPixel(x, y, Color.WHITE);
					}
				}
			}
		}
		
		// Render - Bricks
		
		for (int i = 0; i < bricks.length; i++) {
			if (bricks[i].destroyed != true) {
				for (int y = bricks[i].y; y < bricks[i].y + bricks[i].height; y++) {
					for (int x = bricks[i].x; x < bricks[i].x + bricks[i].width; x++) {
						foreground.setPixel(x, y, Color.CYAN);
					}
				}
			}
		}
		
		background.renderTo(window);
		foreground.renderTo(window);
		window.show();
		window.sleep(8);
	}
}
