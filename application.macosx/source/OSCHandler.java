import oscP5.*;
import processing.core.PApplet;
import netP5.*;

public class OSCHandler {

	OscP5 oscP5;
	NetAddress myRemoteLocation;
	PApplet parent;

	/**
	 * Class Constructor takes in the parent PApplet and passes it to the OSC object to create the listiner 
	 * @param parent
	 */
	public OSCHandler(PApplet parent){

		/* start oscP5, listening for incoming messages at port 12000 */
		oscP5 = new OscP5(parent,3335);

		/* myRemoteLocation is a NetAddress. a NetAddress takes 2 parameters,
		 * an ip address and a port number. myRemoteLocation is used as parameter in
		 * oscP5.send() when sending osc packets to another computer, device, 
		 * application. usage see below. for testing purposes the listening port
		 * and the port of the remote location address are the same, hence you will
		 * send messages back to this sketch.
		 */
		myRemoteLocation = new NetAddress("127.0.0.1",3335);
	}
	
	public void sendOSCMessage(OscMessage myMessage){
		 oscP5.send(myMessage, myRemoteLocation); 
	}

}
