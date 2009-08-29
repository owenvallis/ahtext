/**
 * Observable interface for notifying listeners when TUIOevents update
 * 
 * @author owen_vallis
 *
 */
public interface TuioSubject {
	public void registerObserver(TuioObserver o);
	public void removeObserver(TuioObserver o);
	public void removeAllObservers();
	public void notifyObserverOfAddedCursor();	
	public void notifyObserverOfUpdatedCursor();
	public void notifyObserverOfRemovedCursor();
}
