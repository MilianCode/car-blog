package com.miliancode.carblog.repo;

import com.miliancode.carblog.models.News;
import org.springframework.data.repository.CrudRepository;

public interface NewsRepository extends CrudRepository<News, Long> {
}
