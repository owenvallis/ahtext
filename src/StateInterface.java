
public interface StateInterface {

	public void touchEventAddCursor(long sessionID, int cursorX, int cursorY);
	
	public void touchEventUpdateCursor(long sessionID, int cursorX, int cursorY);
	
	public void touchEventRemoveCursor(long sessionID);
	
	public void timer();
	
	public void displayGraphics();

	public void reset();
}
