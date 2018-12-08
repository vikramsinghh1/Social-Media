package io.SampleLogin.Provider;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.google.api.Google;

@Configuration
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class BaseProvider {

	private Facebook facebook;
	private Google google;
	private ConnectionRepository connectionRepository;
	
	public  BaseProvider(Facebook facebook,Google google, ConnectionRepository connectionRepository) {
		this.facebook = facebook;
		this.connectionRepository = connectionRepository;
		this.google=google; 
	}

	public Facebook getFacebook() {
		return facebook;
	}

	public void setFacebook(Facebook facebook) {
		this.facebook = facebook;
	}

	public ConnectionRepository getConnectionRepository() {
		return connectionRepository;
	}

	public void setConnectionRepository(ConnectionRepository connectionRepository) {
		this.connectionRepository = connectionRepository;
	}

	public Google getGoogle() {
		return google;
	}

	public void setGoogle(Google google) {
		this.google = google;
	}

}
