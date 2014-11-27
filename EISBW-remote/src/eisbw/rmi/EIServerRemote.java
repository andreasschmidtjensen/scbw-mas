package eisbw.rmi;


import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;

import eis.AgentListener;
import eis.EnvironmentListener;
import eis.exceptions.ActException;
import eis.exceptions.AgentException;
import eis.exceptions.EntityException;
import eis.exceptions.ManagementException;
import eis.exceptions.NoEnvironmentException;
import eis.exceptions.PerceiveException;
import eis.exceptions.RelationException;
import eis.iilang.Action;
import eis.iilang.EnvironmentState;
import eis.iilang.Percept;

public interface EIServerRemote extends Remote {


	void registerAgent(String agent) throws AgentException,RemoteException;

	void unregisterAgent(String agent) throws AgentException,RemoteException;

	LinkedList<String> getAgents() throws RemoteException,RemoteException;

	LinkedList<String> getEntities() throws RemoteException,RemoteException;

	void associateEntity(String agent, String entity) throws RelationException,RemoteException;

	void freeEntity(String entity) throws RelationException,RemoteException,EntityException;

	void freeAgent(String agent) throws RelationException,RemoteException;

	void freePair(String agent, String entity) throws RelationException,RemoteException;

	HashSet<String> getAssociatedEntities(String agent) throws AgentException,RemoteException;

	HashSet<String> getAssociatedAgents(String entity) throws EntityException,RemoteException;

	LinkedList<String> getFreeEntities() throws RemoteException;

	Map<String,Percept> performAction(String agent, Action action,
			String... entities) throws ActException, NoEnvironmentException,RemoteException;

	Map<String,Collection<Percept>>  getAllPercepts(String agent, String... entities)
			throws PerceiveException, NoEnvironmentException,RemoteException;

	String getType(String entity) throws EntityException,RemoteException;
	
	void attachClientListener(EIClientRemote client) throws RemoteException;
	
	void detachClientListener(EIClientRemote client) throws RemoteException;
        
	LinkedList<Percept> getAllPerceptsFromEntity(String entity) throws PerceiveException, NoEnvironmentException,RemoteException;
	
	//TODO: Should possibly be on client side instead of here.
	public void setState(EnvironmentState state) throws RemoteException, ManagementException;

	EnvironmentState getState() throws RemoteException, ManagementException;
}
