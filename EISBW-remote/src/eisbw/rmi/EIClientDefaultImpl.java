package eisbw.rmi;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import eis.AgentListener;
import eis.EnvironmentInterfaceStandard;
import eis.EnvironmentListener;
import eis.exceptions.ActException;
import eis.exceptions.AgentException;
import eis.exceptions.EntityException;
import eis.exceptions.EnvironmentInterfaceException;
import eis.exceptions.ManagementException;
import eis.exceptions.NoEnvironmentException;
import eis.exceptions.PerceiveException;
import eis.exceptions.RelationException;
import eis.iilang.Action;
import eis.iilang.EnvironmentState;
import eis.iilang.Percept;
import events.NotifyDeletedEntityEvent;
import events.NotifyFreeEntityEvent;
import events.NotifyNewEntityEvent;
import events.RMIEvent;

// TODO throw exeptions

/**
 * 
 * @author tristanbehrens
 *
 */

/**
 * 
 */
public abstract class EIClientDefaultImpl implements EnvironmentInterfaceStandard, EIClientRemote, Runnable {

	protected EIServerRemote server = null;
	private static final String HOST = "localhost";
	public static boolean debug = true;
	private Registry registry;
	protected ClientEventHandlerRemote clientEventHandler;
	public boolean running = true;

	protected LinkedList<String> registeredAgents = new LinkedList<String>();

	/**
	 * This collection stores the listeners that are used to notify about
	 * certain events.
	 * <p/>
	 * The collection can be changed by invoking the respective methods for
	 * attaching and detaching listeners.
	 */
	public Vector<EnvironmentListener> environmentListeners = null;
	/**
	 * Stores for each agent (represented by a string) a set of listeners.
	 */
	private ConcurrentHashMap<String, HashSet<AgentListener>> agentsToAgentListeners = null;

	public ConcurrentHashMap<String, HashSet<String>> agentsToEntities = null;

	public EIClientDefaultImpl(String serverName) {

		// set up data structures
		environmentListeners = new Vector<EnvironmentListener>();
		agentsToAgentListeners = new ConcurrentHashMap<String, HashSet<AgentListener>>();
		agentsToEntities = new ConcurrentHashMap<>();

		// connect to server, if this fails instantiate one
		try {
			connect("EIServer");
		} catch (RemoteException | NotBoundException e) {
			debugPrintln("Could not establish a connection.");
			e.printStackTrace();
		}
	}
	@Override
	public void run() {
		while (running = true) {
			try {
				Thread.sleep(200);
				//TODO: clientEventHandler might not be necessary
				RMIEvent event = clientEventHandler.popEvent(this.toString());
				if (null != event) {
					if (event instanceof NotifyNewEntityEvent) {
						debugPrintln("NotifyNewEntityEvent");
						NotifyNewEntityEvent evt = ((NotifyNewEntityEvent) event);
						notifyNewEntity(evt.getEntity());
					} else if (event instanceof NotifyFreeEntityEvent) {
						debugPrintln("NotifyFreeEntityEvent");
						NotifyFreeEntityEvent evt = ((NotifyFreeEntityEvent) event);
						notifyFreeEntity(evt.getEntity(), evt.getAgents());
					} else if (event instanceof NotifyDeletedEntityEvent) {
						debugPrintln("NotifyFreeEntityEvent");
						NotifyDeletedEntityEvent evt = ((NotifyDeletedEntityEvent) event);
						notifyDeletedEntity(evt.getEntity(), evt.getAgents());
					}
				}
			} catch (InterruptedException | RemoteException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void debugPrintln(Object obj) {

		if (debug)
			System.out.println("[CLIENT]: " + obj);

	}

	public void connect(String serverName) throws RemoteException, NotBoundException {

		debugPrintln("Getting registry...");
		registry = LocateRegistry.getRegistry(HOST, Registry.REGISTRY_PORT);

		debugPrintln("Getting interface...");
		EIServerRemote s = (EIServerRemote) registry.lookup(serverName);

		debugPrintln("Connected to server");

		server = s;

		// add as listener
		s.attachClientListener((EIClientRemote) this);
		// UnicastRemoteObject.exportObject(this);

		debugPrintln("Added as listener on server");

		clientEventHandler = (ClientEventHandlerRemote) registry.lookup(ClientEventHandlerRemote.class.getSimpleName());
	}
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see eis.EnvironmentInterfaceStandard#isStateTransitionValid(eis.iilang.
	 * EnvironmentState, eis.iilang.EnvironmentState)
	 */
	@Override
	public boolean isStateTransitionValid(EnvironmentState oldState, EnvironmentState newState) {
		if (null == oldState) {
			return true;
		} else if (oldState == EnvironmentState.INITIALIZING) {
			if (newState == EnvironmentState.INITIALIZING || newState == EnvironmentState.PAUSED
					|| newState == EnvironmentState.KILLED) {
				return true;
			}
		} else if (oldState == EnvironmentState.PAUSED) {
			if (newState == EnvironmentState.KILLED || newState == EnvironmentState.RUNNING) {
				return true;
			}
		} else if (oldState == EnvironmentState.RUNNING) {
			if (newState == EnvironmentState.PAUSED || newState == EnvironmentState.KILLED) {
				return true;
			}
		}

		return false;
	}

	public void associateEntity(String agent, String entity) throws RelationException {
		debugPrintln("associateEntity(" + agent + "," + entity + ")");

		// associate
		HashSet<String> ens = agentsToEntities.get(agent);
		if (ens == null) {

			ens = new HashSet<String>();
		}
		ens.add(entity);
		agentsToEntities.put(agent, ens);

		try {
			server.associateEntity(agent, entity);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void attachAgentListener(String agent, AgentListener listener) {
		debugPrintln("Attaching AgentListener: " + listener);

		if (registeredAgents.contains(agent) == false)
			return;

		HashSet<AgentListener> listeners = agentsToAgentListeners.get(agent);

		if (listeners == null)
			listeners = new HashSet<AgentListener>();

		listeners.add(listener);

		agentsToAgentListeners.put(agent, listeners);

	}

	public void attachEnvironmentListener(EnvironmentListener listener) {
		debugPrintln("Attaching EnvironmentListiner: " + listener);

		if (!environmentListeners.contains(listener)) {
			environmentListeners.add(listener);
		}

		debugPrintln("EnvironmentListener size: " + environmentListeners.size());
	}

	public void detachAgentListener(String agent, AgentListener listener) {
		debugPrintln("Detaching AgentListener: " + listener);
		if (registeredAgents.contains(agent) == false)
			return;

		HashSet<AgentListener> listeners = agentsToAgentListeners.get(agent);

		if (listeners == null || listeners.contains(agent) == false)
			return;

		listeners.remove(listener);

	}

	public void detachEnvironmentListener(EnvironmentListener listener) {
		debugPrintln("Detaching EnvironmentListener: " + listener);

		if (environmentListeners.contains(listener)) {
			environmentListeners.remove(listener);
			// remoteApi.removeEnvironmentListener(new Data(listener));
		}

	}

	public void freeAgent(String agent) throws RelationException {

		try {
			server.freeAgent(agent);
		} catch (RemoteException e) {
			e.printStackTrace();
		}

	}

	public void freeEntity(String entity) throws RelationException {

		try {
			server.freeEntity(entity);
		} catch (RemoteException | EntityException e) {
			e.printStackTrace();
		}

	}

	public void freePair(String agent, String entity) throws RelationException {

		try {
			server.freePair(agent, entity);
		} catch (RemoteException e) {
			e.printStackTrace();
		}

	}

	public LinkedList<String> getAgents() {

		try {
			return server.getAgents();
		} catch (RemoteException e) {
			e.printStackTrace();
			return null;
		}

	}

	public Map<String, Collection<Percept>> getAllPercepts(String agent, String... entities) throws PerceiveException,
			NoEnvironmentException {

		Map<String, Collection<Percept>> ret = new HashMap<String, Collection<Percept>>();

		// fail if the environment does not run
		// if (state != EnvironmentState.RUNNING)
		// throw new PerceiveException("Environment does not run but is "
		// + state);

		// fail if ther agent is not registered
		if (registeredAgents.contains(agent) == false)
			throw new PerceiveException("Agent \"" + agent + "\" is not registered.");

		// get the associated entities
		HashSet<String> associatedEntities = agentsToEntities.get(agent);

		// fail if there are no associated entities
		if (associatedEntities == null || associatedEntities.size() == 0)
			throw new PerceiveException("Agent \"" + agent + "\" has no associated entities.");


		// gather all percepts
		if (entities.length == 0) {

			for (String entity : associatedEntities) {

				// get all percepts
				LinkedList<Percept> all = (LinkedList<Percept>) getAllPerceptsFromEntity(entity);

				// add annonation
				for (Percept p : all)
					p.setSource(entity);

				// done
				ret.put(entity, all);

			}

		}
		// only from specified entities
		else {
			for (String entity : entities) {

				if (associatedEntities.contains(entity) == false)
					throw new PerceiveException("Entity \"" + entity
							+ "\" has not been associated with the agent \""
							+ agent + "\".");

				// get all percepts
				LinkedList<Percept> all = (LinkedList<Percept>) getAllPerceptsFromEntity(entity);

				// add annonation
				for (Percept p : all)
					p.setSource(entity);

				// done
				ret.put(entity, all);

			}
		}
		debugPrintln("getAllPercepts( " + agent + "," + entities + ")");
		return ret;
	}

	/**
	 * This method calls an extra function on the server due to permission
	 * problems from parent classes/interfaces.
	 * 
	 * @param entity
	 * @return
	 * @throws NoEnvironmentException
	 * @throws PerceiveException
	 */
	private Collection<Percept> getAllPerceptsFromEntity(String entity) throws PerceiveException,
			NoEnvironmentException {

		try {
			LinkedList<Percept> ret = server.getAllPerceptsFromEntity(entity);
			return ret;
		} catch (RemoteException e) {
			e.printStackTrace();
			return null;
		}
	}

	public HashSet<String> getAssociatedAgents(String entity) throws EntityException {

		try {
			return server.getAssociatedAgents(entity);
		} catch (RemoteException e) {
			e.printStackTrace();
			return null;
		}

	}

	public HashSet<String> getAssociatedEntities(String agent) throws AgentException {

		try {
			return server.getAssociatedEntities(agent);
		} catch (RemoteException e) {
			e.printStackTrace();
			return null;
		}

	}

	public LinkedList<String> getEntities() {

		try {
			return server.getEntities();
		} catch (RemoteException e) {
			e.printStackTrace();
			return null;
		}

	}

	public LinkedList<String> getFreeEntities() {
		try {
			return server.getFreeEntities();
		} catch (RemoteException e) {
			e.printStackTrace();
			return null;
		}

	}

	public String getType(String entity) throws EntityException {
		try {
			return server.getType(entity);
		} catch (RemoteException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public void notifyNewEntity(String entity) throws RemoteException {

		// debugPrintln("New entity: " + entity + " environmentListeners: " +
		// remoteApi.getEnvironmentListeners().getEnvironmentListeners().toString());
		// for (EnvironmentListener listener :
		// remoteApi.getEnvironmentListeners().getEnvironmentListeners()) {
		// listener.handleNewEntity(entity);
		// }

		debugPrintln("New entity: " + entity + " environmentListeners: " + environmentListeners.size());
		for (EnvironmentListener listener : environmentListeners) {
			listener.handleNewEntity(entity);
		}
		
	}

	@Override
	public void notifyFreeEntity(String entity, Collection<String> agents) throws RemoteException {

		debugPrintln("Free entity " + entity);

		// for (EnvironmentListener listener :
		// remoteApi.getEnvironmentListeners().getEnvironmentListeners()) {
		for (EnvironmentListener listener : environmentListeners) {
			listener.handleFreeEntity(entity, agents);

		}
	}

	@Override
	public void kill() throws ManagementException {
		debugPrintln("kill() called");
		if (isKillSupported() == false) {
			throw new ManagementException("kill is not supported");
		}
		running = false;
	}

	@Override
	public EnvironmentState getState() {
		try {
			return server.getState();
		} catch (RemoteException | ManagementException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public void notifyDeletedEntity(String entity, Collection<String> agents) throws RemoteException {
		debugPrintln("Deleted entity " + entity);

		// for (EnvironmentListener listener :
		// remoteApi.getEnvironmentListeners().getEnvironmentListeners()) {
		for (EnvironmentListener listener : environmentListeners) {
			listener.handleDeletedEntity(entity, agents);
		}
	}

	public Percept performEntityAction(String entity, Action action) throws ActException {
		debugPrintln("Entity " + entity + " is performing action: " + action);

		return null;
	}
	
	public Map<String, Percept> performAction(String agent, Action action, String... entities) throws ActException,
			NoEnvironmentException {
		debugPrintln("Agent " + agent + " is performing action: " + action);
		try {
			return server.performAction(agent, action, entities);
		} catch (RemoteException e) {
			e.printStackTrace();
			return null;
		}

	}

	public void registerAgent(String agent) throws AgentException {

		debugPrintln("Registering agent " + agent);

		try {

			server.registerAgent(agent);

			registeredAgents.add(agent);

		} catch (RemoteException e) {
			e.printStackTrace();
		}

	}

	public void release() {

		server = null;

	}

	public void unregisterAgent(String agent) throws AgentException {

		debugPrintln("Unregistering agent " + agent);

		try {

			server.registerAgent(agent);

			registeredAgents.remove(agent);

		} catch (RemoteException e) {
			e.printStackTrace();
		}

	}

	public void notifyAgent(String agent, Percept percept) throws RemoteException {
		debugPrintln("Notified agent " + agent + " percept " + percept);

	}

	public void notifyAgents(Percept percept, String... agents) throws EnvironmentInterfaceException, RemoteException {
		if (agents == null) {

			for (String agent : registeredAgents) {

				HashSet<AgentListener> agentListeners = agentsToAgentListeners.get(agent);

				if (agentListeners == null)
					continue;

				for (AgentListener listener : agentListeners) {

					listener.handlePercept(agent, percept);

				}

			}

			return;
		}

		// send to specified agents
		for (String agent : agents) {

			if (!registeredAgents.contains(agent))
				throw new EnvironmentInterfaceException("Agent " + agent + " has not registered to the environment.");

			HashSet<AgentListener> agentListeners = agentsToAgentListeners.get(agent);

			if (agentListeners == null)
				continue;

			for (AgentListener listener : agentListeners) {

				listener.handlePercept(agent, percept);

			}

		}

	}
}
