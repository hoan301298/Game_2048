package vamk.GameProject.Game2048;

import java.awt.*;

import java.awt.event.*;

import java.util.List;

import java.util.*;

public class Game {
	// Declare the variables that means the objects showing with 16 pieces.
	public static List<GameObject> objects;
	
	// Declare checking movements.
	public static boolean move = false, Moved = true, somethingIsMoving = false;
	
	public static int direction = 0;
	
	private Random rand = new Random();

	public Game() {
		
		init();
		
	} 
	// Declare the variables for the beginning of the game
	public void init() {
		objects = new ArrayList<GameObject>();
		move = false;
		Moved = true;
		somethingIsMoving = false;
		spawn();	
	}
	private void spawn() {
		// If we had 16 objects showing on screen (that's filled and cannot plus to 
		// any objects around them), then so nothing is added!
		if(objects.size() == 16) return;
		
		boolean Available = false;
		int x = 0, y = 0;
		while(!Available) {
			x = rand.nextInt(4);
			y = rand.nextInt(4);
			boolean IsAvailable = true;
			for (int i = 0; i < objects.size(); i++) {
				if(objects.get(i).x / 100 == x && objects.get(i).y / 100 == y) {
					IsAvailable = false;
				}
			}
			if(IsAvailable) Available = true;
		}
		objects.add(new GameObject(x * 100, y * 100));
	}
	
	public void update() {
		moveLogic();
		for(int i = 0; i < objects.size(); i++) {
			objects.get(i).update();
		}
		checkForValueIncrease();
	}

	private void checkForValueIncrease() {
		// Using loop "for" to find if the objects have the same value 
		for(int i = 0; i < objects.size(); i++) {
			for(int j = 0; j < objects.size(); j++) {
				if(i == j) continue;
				// Checking if the x and y coordinate of both i and j. Also do not make lost of object i but object j is doubled. 
				if(objects.get(i).x == objects.get(j).x && objects.get(i).y == objects.get(j).y && !objects.get(i).remove && !objects.get(j).remove) {
					objects.get(i).remove = true;
					objects.get(j).value *= 2;
					objects.get(j).createSprite();
				}
			}
		}
		for(int i = 0; i < objects.size(); i++) {
			if(objects.get(i).remove) objects.remove(i);
		}
	}

	private void moveLogic() {
		somethingIsMoving = false;
		for(int i= 0; i < objects.size(); i++) {
			if(objects.get(i).move) {
				somethingIsMoving = true;
			}
		}
		if(!somethingIsMoving) {
			move = false;
			for(int i = 0; i < objects.size(); i++) {
				objects.get(i).Moved = false;
			}
		}
		if(!move && Moved) {
			spawn();
			Moved = false;
		}
		if(!move && !Moved) {
			if(Keyboard.keyDown(KeyEvent.VK_LEFT)) {
				Moved = true;
				move = true;
				direction = 0;
			} else if(Keyboard.keyDown(KeyEvent.VK_RIGHT)) {
				Moved = true;
				move = true;
				direction = 1;
			} else if(Keyboard.keyDown(KeyEvent.VK_UP)) {
				Moved = true;
				move = true;
				direction = 2;
			} else if(Keyboard.keyDown(KeyEvent.VK_DOWN)) {
				Moved = true;
				move = true;
				direction = 3;
			}
		}
	}
	public void render() {
		
		Renderer.renderBackground();
		
		// Renderer.renderSprite(new Sprite(100, 100, 0xfff4f4f4), 100, 100);
		
		for(int i = 0; i < objects.size(); i++) {
			objects.get(i).render();
		}
		for(int i = 0; i < Main.pixels.length; i++) {
			Main.pixels[i] = Renderer.pixels[i];
		}
	}
	
	public void renderText(Graphics2D g) {
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setFont(new Font("Verdana", 0, 100));
		g.setColor(Color.black);
		
		for(int i = 0; i < objects.size(); i++) {
			GameObject o = objects.get(i);
			String s = o.value + "";
			int sw = (int) (g.getFontMetrics().stringWidth(s) / 2 / Main.scale);
			g.drawString(s,(int) (o.x + o.width / 2 - sw) * Main.scale,(int) (o.y + o.height / 2 + 18) * Main.scale);
		}
	}
}
