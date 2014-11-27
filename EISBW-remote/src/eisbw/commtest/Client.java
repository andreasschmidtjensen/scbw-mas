package eisbw.commtest;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.Map;

import eis.exceptions.ManagementException;
import eis.exceptions.QueryException;
import eis.iilang.EnvironmentState;
import eis.iilang.Parameter;
import eisbw.rmi.EIClientDefaultImpl;
import events.NotifyDeletedEntityEvent;
import events.NotifyFreeEntityEvent;
import events.NotifyNewEntityEvent;
import events.RMIEvent;

public class Client extends EIClientDefaultImpl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7906889291488033573L;
	protected EnvironmentState state;

	public static void main(String[] args) {
		new Client();
	}

	public Client() {
		super("EIServer");

		new Thread(this).start();
	}

	@Override
	public String requiredVersion() {
		return "0.3";
	}

	/**
	 * Resets the environment(-interface) with a set of key-value-pairs.
	 * 
	 * @param parameters
	 * @throws ManagementException
	 *             is thrown either when the initializing is not supported or
	 *             the parameters are wrong.
	 */
	public void reset(Map<String, Parameter> parameters) throws ManagementException {
		debugPrintln("reset() called");
	}

	@Override
	public void start() throws ManagementException {
		debugPrintln("start() called");
	}

	@Override
	public void pause() throws ManagementException {
		debugPrintln("pause() called");
	}

	@Override
	public boolean isInitSupported() {
		return true;
	}

	@Override
	public boolean isStartSupported() {
		return true;
	}

	@Override
	public boolean isPauseSupported() {
		return true;
	}

	@Override
	public boolean isKillSupported() {
		return running;
	}

	@Override
	public String queryProperty(String string) throws QueryException {
		return null;
	}

	@Override
	public String queryEntityProperty(String string, String string1) throws QueryException {
		return null;
	}

	@Override
	public void handleStateChange(EnvironmentState state) {
		// TODO: Handle state changed
		debugPrintln("EnvironmentState changed to: " + state.toString());
	}

	@Override
	public String toString() {
		return "RMIBWAPI";
	}

	@Override
	public void init(Map<String, Parameter> arg0) throws ManagementException {
		// TODO Auto-generated method stub
		
	}
}
