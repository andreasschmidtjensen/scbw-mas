package events;

public class NotifyNewEntityEvent extends RMIEvent{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String entity;
	
	public NotifyNewEntityEvent(String entity) {
		super();
		this.entity = entity;
	}


	public String getEntity() {
		return entity;
	}

	public void setEntity(String entity) {
		this.entity = entity;
	}
}
