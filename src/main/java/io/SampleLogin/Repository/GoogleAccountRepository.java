package io.SampleLogin.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import io.SampleLogin.Model.GoogleAccount;
import io.SampleLogin.Model.Post;


public interface GoogleAccountRepository extends JpaRepository<Post, Integer> {
	
	public GoogleAccount findByUserId(int userId);

	public void save(GoogleAccount gaccount);

}

