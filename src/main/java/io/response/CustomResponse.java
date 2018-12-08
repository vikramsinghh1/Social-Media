package io.response;

public class CustomResponse {

	private String description;
	
	private int status_code;
	
	private String social_media;
	
	public CustomResponse() {
		
	}
	
	public CustomResponse(String description, int status_code, String social_media) {
		this.description = description;
		this.status_code = status_code;
		this.social_media = social_media;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getStatus_code() {
		return status_code;
	}

	public void setStatus_code(int status_code) {
		this.status_code = status_code;
	}
	
	public String getSocial_media() {
		return social_media;
	}

	public void setSocial_media(String social_media) {
		this.social_media = social_media;
	}

}
