package eisbw.commtest;

import java.util.Collections;

import eis.exceptions.ManagementException;
import eis.iilang.Parameter;
import eisbw.rmi.EIServerDefaultImpl;

public class Server extends EIServerDefaultImpl {

	/**
	 * 
	 */
	private static final long serialVersionUID = -305225724514914974L;

	public static void main(String[] args) throws ManagementException {
		Server s = new Server();
		s.init(Collections.<String, Parameter> emptyMap());
	}

	public Server() {
		super();
	}
}
