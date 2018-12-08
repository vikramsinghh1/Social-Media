package templates;

import org.springframework.social.oauth1.AbstractOAuth1ApiBinding;

public class TumblrTemplate extends AbstractOAuth1ApiBinding {

		public TumblrTemplate(String consumerKey, String consumerSecret, String accessToken, String accessTokenSecret) {
			super(consumerKey, consumerSecret, accessToken, accessTokenSecret);
		}
	}


