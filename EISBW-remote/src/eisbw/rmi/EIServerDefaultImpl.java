package eisbw.rmi;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Vector;

import eis.exceptions.AgentException;
import eis.exceptions.EntityException;
import eis.exceptions.ManagementException;
import eis.exceptions.NoEnvironmentException;
import eis.exceptions.PerceiveException;
import eis.exceptions.RelationException;
import eis.iilang.EnvironmentState;
import eis.iilang.Percept;
import eisbw.BWAPIBridge;
import events.NotifyDeletedEntityEvent;
import events.NotifyFreeEntityEvent;
import events.NotifyNewEntityEvent;

// TODO remove redundancy between this class and EIDefaultImpl
public abstract class EIServerDefaultImpl extends BWAPIBridge implements EIServerRemote {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2268896537056515734L;

	public static boolean debug = true;

	private Vector<EIClientRemote> remoteClientListeners = new Vector<EIClientRemote>();
	private static Registry registry;
	protected ClientEventHandlerRemote clientEventHandler;

	public EIServerDefaultImpl() {
		super();

		// creating registry
		try {
			debugPrintln("Creating registry...");

			registry = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);

			debugPrintln("Exporting...");
			EIServerDefaultImpl server = this;
			EIServerRemote stub = (EIServerRemote) UnicastRemoteObject.exportObject(server, 0);
			// RemoteServer.setLog( System.out );

			debugPrintln("Rebinding registry...");
			Registry registry = LocateRegistry.getRegistry();
			registry.rebind("EIServer", stub);

			debugPrintln("Server established!");

			registerObject(ClientEventHandlerRemote.class.getSimpleName(), new ClientEventHandler());
			clientEventHandler = (ClientEventHandlerRemote) registry.lookup(ClientEventHandlerRemote.class.getSimpleName());
		} catch (RemoteException | AlreadyBoundException | NotBoundException e) {
			debugPrintln("Caught an exception");
			debugPrintln(e);
		}
	}

	public static void registerObject(String name, Remote remoteObj) throws RemoteException, AlreadyBoundException {
		registry.bind(name, remoteObj);
		System.out.println("Registered: " + name + " -> " + remoteObj.getClass().getName() + "[" + remoteObj + "]");
	}

	public void debugPrintln(Object obj) {
		if (debug) {
			System.out.println("[SERVER]: " + obj);
		}
	}

	public void attachClientListener(EIClientRemote client) throws RemoteException {
		debugPrintln("Registered client: " + client);
		remoteClientListeners.add(client);
	}

	public void detachClientListener(EIClientRemote client) throws RemoteException {
		debugPrintln("Detached client: " + client);
		remoteClientListeners.remove(client);
	}

	protected void notifyDeletedEntity(String entity, Collection<String> agents) {
		for (EIClientRemote client : remoteClientListeners) {
			try {
				// TODO: clientEventHandler might not be necessary
				clientEventHandler.addEvent(client.toString(), new NotifyDeletedEntityEvent(entity, agents));
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void notifyFreeEntity(String entity, Collection<String> agents) {
		for (EIClientRemote client : remoteClientListeners) {
			try {
				// TODO: clientEventHandler might not be necessary
				clientEventHandler.addEvent(client.toString(), new NotifyFreeEntityEvent(entity, agents));
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}

	public LinkedList<Percept> getAllPerceptsFromEntity(String entity) throws PerceiveException, NoEnvironmentException {
		debugPrintln("getAllPerceptsFromEntity(" + entity + ")");
		return super.getAllPerceptsFromEntity(entity);

	}

	protected void notifyNewEntity(String entity) {
		debugPrintln("New entity: " + entity);
		for (EIClientRemote client : remoteClientListeners) {
			try {
				// TODO: clientEventHandler might not be necessary
				clientEventHandler.addEvent(client.toString(), new NotifyNewEntityEvent(entity));
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	/**
	 * Sets the state of the environment-interface. Firstly the state-transition
	 * is tested if it is valid. If so, the state will be changed and all
	 * environment-listeners will be notified.
	 * 
	 * @param state
	 *            the new state
	 * @throws ManagementException
	 *             if thrown if the state transition is not valid
	 */
	public void setState(EnvironmentState state) throws ManagementException {
		debugPrintln("Setting state: " + state);
		// TODO is state transition valid?
		if (isStateTransitionValid(this.state, state) == false)
			throw new ManagementException("Invalid state transition from " + this.state.toString() + " to  "
					+ state.toString());

		// set the state
		this.state = state;

		// notify listeners
		for (EIClientRemote client : remoteClientListeners)
			client.handleStateChange(state);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see eis.EnvironmentInterfaceStandard#freeEntity(java.lang.String)
	 */
	public void freeEntity(String entity) throws RelationException, EntityException {
		super.freeEntity(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * eis.EnvironmentInterfaceStandard#getAssociatedEntities(java.lang.String)
	 */
	public HashSet<String> getAssociatedEntities(String agent) throws AgentException {
		if (registeredAgents.contains(agent) == false)
			throw new AgentException("Agent \"" + agent + "\" has not been registered.");

		HashSet<String> ret = this.agentsToEntities.get(agent);

		if (ret == null)
			ret = new HashSet<String>();

		return ret;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * eis.EnvironmentInterfaceStandard#getAssociatedAgents(java.lang.String)
	 */
	public HashSet<String> getAssociatedAgents(String entity) throws EntityException {
		if (entities.contains(entity) == false)
			throw new EntityException("Entity \"" + entity + "\" has not been registered.");

		HashSet<String> ret = new HashSet<String>();

		for (Entry<String, HashSet<String>> entry : agentsToEntities.entrySet()) {

			if (entry.getValue().contains(entity))
				ret.add(entry.getKey());
		}

		return ret;
	}

	/*
	 * Acting/perceiving functionality.
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see eis.EnvironmentInterfaceStandard#getEntities()
	 */
	@SuppressWarnings("unchecked")
	public LinkedList<String> getEntities() {
		// TODO: Should probably be changed later since each client only needs
		// its own entities.
		return (LinkedList<String>) entities.clone();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see eis.EnvironmentInterfaceStandard#getType(java.lang.String)
	 */
	public String getType(String entity) throws EntityException {

		if (!this.entities.contains(entity))
			throw new EntityException("Entity \"" + entity + "\" does not exist!");

		String type = entitiesToTypes.get(entity);

		if (type == null)
			type = "unknown";

		return type;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see eis.EnvironmentInterfaceStandard#registerAgent(java.lang.String)
	 */
	public void registerAgent(String agent) throws AgentException {
		debugPrintln("Registering agent " + agent);
		if (registeredAgents.contains(agent))
			throw new AgentException("Agent " + agent + " has already registered to the environment.");

		registeredAgents.add(agent);

		debugPrintln("Entities " + entities.toString());

		debugPrintln("agentsToEntities: " + agentsToEntities.toString());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see eis.EnvironmentInterfaceStandard#unregisterAgent(java.lang.String)
	 */
	public void unregisterAgent(String agent) throws AgentException {
		// fail if agents is not registered
		if (!registeredAgents.contains(agent))
			throw new AgentException("Agent " + agent + " has not registered to the environment.");

		// remove from mapping, might be null
		agentsToEntities.remove(agent);

		// finally remove from registered list
		registeredAgents.remove(agent);
	}

	/*
	 * Entity functionality. Adding and removing entities.
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see eis.EnvironmentInterfaceStandard#getAgents()
	 */
	@SuppressWarnings("unchecked")
	public LinkedList<String> getAgents() {
		return (LinkedList<String>) registeredAgents.clone();
	}
}
