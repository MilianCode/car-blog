package com.bobocode.springbootdemo.repo;

import com.bobocode.springbootdemo.models.Post;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<Post, Long> {

}
