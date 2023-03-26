package vamk.GameProject.Game2048;

public class Sprite {

	public int width, height;
	public int[] pixels;
	
	public Sprite(int width, int height, int color) {
		this.width = width;
		this.height = height;
		this.pixels = new int[width * height];
		
		for(int y = 0; y < height; y++) {
			for(int x= 0; x < width; x++) {
				pixels[x + y * width] = color;
				// the color of border around the object's square.
				if(x % 100 < 3 || x % 100 > 97 || y % 100 < 3 || y % 100 > 97) {
					pixels[x + y * width] = 0xffff00ff;
				}
			}
		}
	}
}
