package com.noesis.marketo;

public class MarketoAuth {
	String access_token = "";
	String token_type = "";
	
	public MarketoAuth () {
		// empty constructor
	}
	
	public String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	public String getToken_type() {
		return token_type;
	}
	public void setToken_type(String token_type) {
		this.token_type = token_type;
	}
}
