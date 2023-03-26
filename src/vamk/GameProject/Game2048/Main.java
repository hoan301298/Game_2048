package vamk.GameProject.Game2048;

import java.awt.*;
import java.awt.image.*;
import javax.swing.JFrame;

public class Main extends Canvas implements Runnable {
	
	public static final int WIDTH = 400, HEIGHT = 400;
	public static int scale = 2;
	
	public JFrame frame;
	public Keyboard key;
	public Thread thread;
	public Game game;
	public boolean running = false;
	
	// Declare the BufferedImage for creating image calculated by pixels and getting the RGB animation.
	
	public static BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	public static int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
	
	public Main() {
		// Set the preferred size of the game display.
		setPreferredSize(new Dimension((int) (WIDTH * scale), (int) (HEIGHT * scale)));
		
		// Initialize the objects and give them the first value with null.
		frame = new JFrame();
		game = new Game();
		key = new Keyboard();
		addKeyListener(key);
	}
	
	// Thread is used to allow multiple threads of execution running concurrently
	public void start() {
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public void stop() {
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	// Copy from the Internet.
	public void run() {
		long lastTimeInNanoSeconds = System.nanoTime();
		long timer = System.currentTimeMillis();
		double nanoSecondsPerUpdate = 1000000000.0 / 60.0;
		double updatesToPerform = 0.0;
		int frames = 0;
		int updates = 0;
		requestFocus();
		while(running) {
			long currentTimeInNanoSeconds = System.nanoTime();
			updatesToPerform += (currentTimeInNanoSeconds - lastTimeInNanoSeconds) / nanoSecondsPerUpdate;
			if(updatesToPerform >= 1) {
				update();
				updates++;
				updatesToPerform--;
			}
			lastTimeInNanoSeconds = currentTimeInNanoSeconds;
			
			render();
			frames++;
			
			if(System.currentTimeMillis() - timer > 1000) {
				frame.setTitle("2048");
				updates = 0;
				frames = 0;
				timer += 1000;
			}
		}
	}
	
	public void update() {
		game.update();
		key.update();
	}
	
	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if(bs == null) {
			// Creating a triple buffering.
			createBufferStrategy(3);
			return;
		}
		game.render();
		
		// Draw the image with the preferred scale and choose the coordinates.
		Graphics2D g = (Graphics2D) bs.getDrawGraphics();
		g.drawImage(image, 0, 0, (int) (WIDTH * scale), (int) (HEIGHT * scale), null);
		game.renderText(g);
		
		// Disposes of this graphics context.
		g.dispose();
		
		// Show graphic would be displayed and visible on screen.
		bs.show();
	}
	
	public static void main(String[] args) {
		Main m = new Main();
		m.frame.setResizable(false);
		m.frame.setTitle("2048");
		m.frame.add(m);
		m.frame.pack();
		m.frame.setVisible(true);
		m.frame.setLocationRelativeTo(null);
		m.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		m.frame.setAlwaysOnTop(true);
		m.start();
//		m.stop();
	}
}

