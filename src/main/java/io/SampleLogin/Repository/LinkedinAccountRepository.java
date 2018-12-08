package io.SampleLogin.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.SampleLogin.Model.LinkedinAccount;



public interface LinkedinAccountRepository extends JpaRepository<LinkedinAccount, Integer> {

	public LinkedinAccount findByLinkedinId(int linkedInId);
}


