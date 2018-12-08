package templates;

import org.springframework.social.oauth1.AbstractOAuth1ApiBinding;

public class TwitTemplate extends AbstractOAuth1ApiBinding {

		public TwitTemplate(String consumerKey, String consumerSecret, String accessToken, String accessTokenSecret) {
			super(consumerKey, consumerSecret, accessToken, accessTokenSecret);
		}
}



