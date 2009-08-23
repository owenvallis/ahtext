/**
 * Creates a touchZone object that defines the location and size of a
 * rectangular window.
 * 
 * @author owen_vallis
 * 
 */
public class TouchZone {

	private long zoneName;
	private int x, y;
	private int width, height;

	/**
	 * Class constructor for a new zone
	 * 
	 * @param zoneName
	 *            The string used to uniquely identify the zone
	 * @param x
	 *            The x position on the screen passed in as pixels
	 * @param y
	 *            The y position on the screen passed in as pixels
	 * @param width
	 *            The width of the zone defined in pixels
	 * @param height
	 *            The height of the zone defined in pixels
	 */
	public TouchZone(long zoneName, int x, int y, int width, int height) {
		this.zoneName = zoneName;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	// /////////////////////////////////////////////////////////////
	// SET METHODS//////////////////////////////////////////////////
	// /////////////////////////////////////////////////////////////
	public long getZoneName() {
		return zoneName;
	}

	public void setZoneName(long zoneName) {
		this.zoneName = zoneName;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

}
