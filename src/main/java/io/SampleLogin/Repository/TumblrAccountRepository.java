package io.SampleLogin.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.SampleLogin.Model.TumblrAccount;


public interface TumblrAccountRepository extends JpaRepository<TumblrAccount, Integer> {

	public TumblrAccount findBytumblrId(int tumblrId);
	
	
}
