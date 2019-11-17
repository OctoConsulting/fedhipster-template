package com.octo.app.repository.search;

import com.octo.app.domain.Hero;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Hero} entity.
 */
public interface HeroSearchRepository extends ElasticsearchRepository<Hero, Long> {
}
