package io.SampleLogin.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.SampleLogin.Model.TwitterAccount;

public interface TwitterAccountRepository extends JpaRepository<TwitterAccount, Integer> {

	public TwitterAccount findBytwitterId(int twitterId);
	
	
}
