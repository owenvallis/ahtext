/**
 * Observaber interface for implementing methods when TUIOevents update
 * 
 * @author owen_vallis
 *
 */
public interface TuioObserver {
	public void tuioCursorAdded(long sessionID, int cursorX, int cursorY);
	public void tuioCursorUpdate(long sessionID, int cursorX, int cursorY);
	public void tuioCursorRemove(long sessionID);
}
