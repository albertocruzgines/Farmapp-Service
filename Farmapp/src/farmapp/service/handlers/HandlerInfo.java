package farmapp.service.handlers;

public class HandlerInfo {
	private boolean auth;
	private String action;
	private String className;

	public HandlerInfo() {
		super();
	}

	/**
	 * @return the auth
	 */
	public boolean isAuth() {
		return auth;
	}

	/**
	 * @param auth the auth to set
	 */
	public void setAuth(boolean auth) {
		this.auth = auth;
	}

	/**
	 * @return the action
	 */
	public String getAction() {
		return action;
	}

	/**
	 * @param action the action to set
	 */
	public void setAction(String action) {
		this.action = action;
	}

	/**
	 * @return the className
	 */
	public String getClassName() {
		return className;
	}

	/**
	 * @param className the className to set
	 */
	public void setClassName(String className) {
		this.className = className;
	}

}

