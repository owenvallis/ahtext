/**
 * Observable interface for notifying listeners when TUIOevents update
 * 
 * @author owen_vallis
 *
 */
public interface TUIOSubject {
	public void registerObserver(TUIOObserver o);
	public void removeObserver(TUIOObserver o);
	public void removeObservers();
	public void notifyObserverOfAddedCursor();	
	public void notifyObserverOfUpdatedCursor();
	public void notifyObserverOfRemovedCursor();
}
