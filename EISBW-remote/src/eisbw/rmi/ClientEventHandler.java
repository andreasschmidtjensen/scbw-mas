package eisbw.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import events.RMIEvent;

public class ClientEventHandler extends UnicastRemoteObject implements ClientEventHandlerRemote {
	private static final long serialVersionUID = 1L;
	private HashMap<String, ConcurrentLinkedQueue<RMIEvent>> events = new HashMap<String, ConcurrentLinkedQueue<RMIEvent>>();

	public ClientEventHandler() throws RemoteException {
		super();
	}

	@Override
	public synchronized void addEvent(String client, RMIEvent event) throws RemoteException {
		if(!events.containsKey(client)){
			events.put(client, new ConcurrentLinkedQueue<RMIEvent>());
		}
		events.get(client).add(event);
	}

	@Override
	public synchronized RMIEvent popEvent(String client) throws RemoteException {
		if (events.containsKey(client)) {
			return events.get(client).poll();
		} else{
			return null;
		}
	}
}