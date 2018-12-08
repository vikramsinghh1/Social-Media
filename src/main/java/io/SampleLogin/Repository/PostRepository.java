package io.SampleLogin.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import io.SampleLogin.Model.Post;

public interface PostRepository extends JpaRepository<Post, Integer> {

	
	public List<Post> findByUserId(int userId);
}
