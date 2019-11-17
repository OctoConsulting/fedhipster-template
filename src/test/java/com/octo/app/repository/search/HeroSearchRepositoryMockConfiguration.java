package com.octo.app.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link HeroSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class HeroSearchRepositoryMockConfiguration {

    @MockBean
    private HeroSearchRepository mockHeroSearchRepository;

}
