package eisbw.rmi;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Vector;

import eis.EnvironmentListener;
import eis.iilang.EnvironmentState;
import eis.iilang.Percept;

public interface EIClientRemote extends Remote,Serializable {
	
	void notifyAgent(String agent, Percept percept) throws RemoteException;
	
	void notifyFreeEntity(String entity, Collection<String> agents) throws RemoteException;
	
	void notifyNewEntity(String entity) throws RemoteException;
	
	void notifyDeletedEntity(String entity, Collection<String> agents) throws RemoteException;

	void handleStateChange(EnvironmentState state);
	
	String toString();
		
}
