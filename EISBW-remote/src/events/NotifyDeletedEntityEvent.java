package events;

import java.util.Collection;

public class NotifyDeletedEntityEvent extends RMIEvent {
	private String entity;
	private Collection<String> agents;
	
	public NotifyDeletedEntityEvent(String entity, Collection<String> agents) {
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
