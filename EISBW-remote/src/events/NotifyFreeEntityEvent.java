package events;

import java.util.Collection;


public class NotifyFreeEntityEvent extends RMIEvent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String entity;
	private Collection<String> agents;
	
	public NotifyFreeEntityEvent(String entity, Collection<String> agents) {
		super();
		this.entity = entity;
		this.agents = agents;
	}
	public String getEntity() {
		return entity;
	}
	public void setEntity(String entity) {
		this.entity = entity;
	}
	public Collection<String> getAgents() {
		return agents;
	}
	public void setAgents(Collection<String> agents) {
		this.agents = agents;
	}
}
