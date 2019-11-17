package com.octo.app.web.rest;

import com.octo.app.domain.Hero;
import com.octo.app.repository.HeroRepository;
import com.octo.app.repository.search.HeroSearchRepository;
import com.octo.app.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link com.octo.app.domain.Hero}.
 */
@RestController
@RequestMapping("/api")
public class HeroResource {

    private final Logger log = LoggerFactory.getLogger(HeroResource.class);

    private static final String ENTITY_NAME = "hero";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HeroRepository heroRepository;

    private final HeroSearchRepository heroSearchRepository;

    public HeroResource(HeroRepository heroRepository, HeroSearchRepository heroSearchRepository) {
        this.heroRepository = heroRepository;
        this.heroSearchRepository = heroSearchRepository;
    }

    /**
     * {@code POST  /heroes} : Create a new hero.
     *
     * @param hero the hero to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new hero, or with status {@code 400 (Bad Request)} if the hero has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/heroes")
    public ResponseEntity<Hero> createHero(@RequestBody Hero hero) throws URISyntaxException {
        log.debug("REST request to save Hero : {}", hero);
        if (hero.getId() != null) {
            throw new BadRequestAlertException("A new hero cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Hero result = heroRepository.save(hero);
        heroSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/heroes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /heroes} : Updates an existing hero.
     *
     * @param hero the hero to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated hero,
     * or with status {@code 400 (Bad Request)} if the hero is not valid,
     * or with status {@code 500 (Internal Server Error)} if the hero couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/heroes")
    public ResponseEntity<Hero> updateHero(@RequestBody Hero hero) throws URISyntaxException {
        log.debug("REST request to update Hero : {}", hero);
        if (hero.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Hero result = heroRepository.save(hero);
        heroSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, hero.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /heroes} : get all the heroes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of heroes in body.
     */
    @GetMapping("/heroes")
    public List<Hero> getAllHeroes() {
        log.debug("REST request to get all Heroes");
        return heroRepository.findAll();
    }

    /**
     * {@code GET  /heroes/:id} : get the "id" hero.
     *
     * @param id the id of the hero to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the hero, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/heroes/{id}")
    public ResponseEntity<Hero> getHero(@PathVariable Long id) {
        log.debug("REST request to get Hero : {}", id);
        Optional<Hero> hero = heroRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(hero);
    }

    /**
     * {@code DELETE  /heroes/:id} : delete the "id" hero.
     *
     * @param id the id of the hero to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/heroes/{id}")
    public ResponseEntity<Void> deleteHero(@PathVariable Long id) {
        log.debug("REST request to delete Hero : {}", id);
        heroRepository.deleteById(id);
        heroSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/heroes?query=:query} : search for the hero corresponding
     * to the query.
     *
     * @param query the query of the hero search.
     * @return the result of the search.
     */
    @GetMapping("/_search/heroes")
    public List<Hero> searchHeroes(@RequestParam String query) {
        log.debug("REST request to search Heroes for query {}", query);
        return StreamSupport
            .stream(heroSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
