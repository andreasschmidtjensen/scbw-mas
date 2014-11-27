package eisbw.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

import events.RMIEvent;

public interface ClientEventHandlerRemote extends Remote {
	public void addEvent(String client, RMIEvent event) throws RemoteException;
	public RMIEvent popEvent(String client) throws RemoteException;
}