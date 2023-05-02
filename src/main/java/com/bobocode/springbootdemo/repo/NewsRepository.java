package com.bobocode.springbootdemo.repo;

import com.bobocode.springbootdemo.models.News;
import org.springframework.data.repository.CrudRepository;

public interface NewsRepository extends CrudRepository<News, Long> {
}
