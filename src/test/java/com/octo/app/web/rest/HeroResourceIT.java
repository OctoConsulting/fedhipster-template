package com.octo.app.web.rest;

import com.octo.app.AppApp;
import com.octo.app.config.TestSecurityConfiguration;
import com.octo.app.domain.Hero;
import com.octo.app.repository.HeroRepository;
import com.octo.app.repository.search.HeroSearchRepository;
import com.octo.app.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;

import static com.octo.app.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link HeroResource} REST controller.
 */
@SpringBootTest(classes = {AppApp.class, TestSecurityConfiguration.class})
public class HeroResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_AFFILITION = "AAAAAAAAAA";
    private static final String UPDATED_AFFILITION = "BBBBBBBBBB";

    private static final String DEFAULT_EYE_COLOR = "AAAAAAAAAA";
    private static final String UPDATED_EYE_COLOR = "BBBBBBBBBB";

    private static final String DEFAULT_HAIR_COLOR = "AAAAAAAAAA";
    private static final String UPDATED_HAIR_COLOR = "BBBBBBBBBB";

    private static final String DEFAULT_GENDER = "AAAAAAAAAA";
    private static final String UPDATED_GENDER = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final Long DEFAULT_YEAR = 1L;
    private static final Long UPDATED_YEAR = 2L;

    private static final String DEFAULT_FIRST_APPEARANCE = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_APPEARANCE = "BBBBBBBBBB";

    private static final String DEFAULT_UNIVERSE = "AAAAAAAAAA";
    private static final String UPDATED_UNIVERSE = "BBBBBBBBBB";

    private static final Long DEFAULT_INTELLIGENCE = 1L;
    private static final Long UPDATED_INTELLIGENCE = 2L;

    private static final Long DEFAULT_STRENGTH = 1L;
    private static final Long UPDATED_STRENGTH = 2L;

    private static final Long DEFAULT_SPEED = 1L;
    private static final Long UPDATED_SPEED = 2L;

    private static final Long DEFAULT_DURABILITY = 1L;
    private static final Long UPDATED_DURABILITY = 2L;

    private static final Long DEFAULT_POWER = 1L;
    private static final Long UPDATED_POWER = 2L;

    private static final Long DEFAULT_COMBAT = 1L;
    private static final Long UPDATED_COMBAT = 2L;

    private static final Long DEFAULT_TOTAL = 1L;
    private static final Long UPDATED_TOTAL = 2L;

    @Autowired
    private HeroRepository heroRepository;

    /**
     * This repository is mocked in the com.octo.app.repository.search test package.
     *
     * @see com.octo.app.repository.search.HeroSearchRepositoryMockConfiguration
     */
    @Autowired
    private HeroSearchRepository mockHeroSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restHeroMockMvc;

    private Hero hero;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final HeroResource heroResource = new HeroResource(heroRepository, mockHeroSearchRepository);
        this.restHeroMockMvc = MockMvcBuilders.standaloneSetup(heroResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Hero createEntity(EntityManager em) {
        Hero hero = new Hero()
            .name(DEFAULT_NAME)
            .affilition(DEFAULT_AFFILITION)
            .eyeColor(DEFAULT_EYE_COLOR)
            .hairColor(DEFAULT_HAIR_COLOR)
            .gender(DEFAULT_GENDER)
            .status(DEFAULT_STATUS)
            .year(DEFAULT_YEAR)
            .firstAppearance(DEFAULT_FIRST_APPEARANCE)
            .universe(DEFAULT_UNIVERSE)
            .intelligence(DEFAULT_INTELLIGENCE)
            .strength(DEFAULT_STRENGTH)
            .speed(DEFAULT_SPEED)
            .durability(DEFAULT_DURABILITY)
            .power(DEFAULT_POWER)
            .combat(DEFAULT_COMBAT)
            .total(DEFAULT_TOTAL);
        return hero;
    }

    @BeforeEach
    public void initTest() {
        hero = createEntity(em);
    }

    @Test
    @Transactional
    public void createHero() throws Exception {
        int databaseSizeBeforeCreate = heroRepository.findAll().size();

        // Create the Hero
        restHeroMockMvc.perform(post("/api/heroes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hero)))
            .andExpect(status().isCreated());

        // Validate the Hero in the database
        List<Hero> heroList = heroRepository.findAll();
        assertThat(heroList).hasSize(databaseSizeBeforeCreate + 1);
        Hero testHero = heroList.get(heroList.size() - 1);
        assertThat(testHero.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testHero.getAffilition()).isEqualTo(DEFAULT_AFFILITION);
        assertThat(testHero.getEyeColor()).isEqualTo(DEFAULT_EYE_COLOR);
        assertThat(testHero.getHairColor()).isEqualTo(DEFAULT_HAIR_COLOR);
        assertThat(testHero.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testHero.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testHero.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testHero.getFirstAppearance()).isEqualTo(DEFAULT_FIRST_APPEARANCE);
        assertThat(testHero.getUniverse()).isEqualTo(DEFAULT_UNIVERSE);
        assertThat(testHero.getIntelligence()).isEqualTo(DEFAULT_INTELLIGENCE);
        assertThat(testHero.getStrength()).isEqualTo(DEFAULT_STRENGTH);
        assertThat(testHero.getSpeed()).isEqualTo(DEFAULT_SPEED);
        assertThat(testHero.getDurability()).isEqualTo(DEFAULT_DURABILITY);
        assertThat(testHero.getPower()).isEqualTo(DEFAULT_POWER);
        assertThat(testHero.getCombat()).isEqualTo(DEFAULT_COMBAT);
        assertThat(testHero.getTotal()).isEqualTo(DEFAULT_TOTAL);

        // Validate the Hero in Elasticsearch
        verify(mockHeroSearchRepository, times(1)).save(testHero);
    }

    @Test
    @Transactional
    public void createHeroWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = heroRepository.findAll().size();

        // Create the Hero with an existing ID
        hero.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHeroMockMvc.perform(post("/api/heroes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hero)))
            .andExpect(status().isBadRequest());

        // Validate the Hero in the database
        List<Hero> heroList = heroRepository.findAll();
        assertThat(heroList).hasSize(databaseSizeBeforeCreate);

        // Validate the Hero in Elasticsearch
        verify(mockHeroSearchRepository, times(0)).save(hero);
    }


    @Test
    @Transactional
    public void getAllHeroes() throws Exception {
        // Initialize the database
        heroRepository.saveAndFlush(hero);

        // Get all the heroList
        restHeroMockMvc.perform(get("/api/heroes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hero.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].affilition").value(hasItem(DEFAULT_AFFILITION.toString())))
            .andExpect(jsonPath("$.[*].eyeColor").value(hasItem(DEFAULT_EYE_COLOR.toString())))
            .andExpect(jsonPath("$.[*].hairColor").value(hasItem(DEFAULT_HAIR_COLOR.toString())))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR.intValue())))
            .andExpect(jsonPath("$.[*].firstAppearance").value(hasItem(DEFAULT_FIRST_APPEARANCE.toString())))
            .andExpect(jsonPath("$.[*].universe").value(hasItem(DEFAULT_UNIVERSE.toString())))
            .andExpect(jsonPath("$.[*].intelligence").value(hasItem(DEFAULT_INTELLIGENCE.intValue())))
            .andExpect(jsonPath("$.[*].strength").value(hasItem(DEFAULT_STRENGTH.intValue())))
            .andExpect(jsonPath("$.[*].speed").value(hasItem(DEFAULT_SPEED.intValue())))
            .andExpect(jsonPath("$.[*].durability").value(hasItem(DEFAULT_DURABILITY.intValue())))
            .andExpect(jsonPath("$.[*].power").value(hasItem(DEFAULT_POWER.intValue())))
            .andExpect(jsonPath("$.[*].combat").value(hasItem(DEFAULT_COMBAT.intValue())))
            .andExpect(jsonPath("$.[*].total").value(hasItem(DEFAULT_TOTAL.intValue())));
    }
    
    @Test
    @Transactional
    public void getHero() throws Exception {
        // Initialize the database
        heroRepository.saveAndFlush(hero);

        // Get the hero
        restHeroMockMvc.perform(get("/api/heroes/{id}", hero.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(hero.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.affilition").value(DEFAULT_AFFILITION.toString()))
            .andExpect(jsonPath("$.eyeColor").value(DEFAULT_EYE_COLOR.toString()))
            .andExpect(jsonPath("$.hairColor").value(DEFAULT_HAIR_COLOR.toString()))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR.intValue()))
            .andExpect(jsonPath("$.firstAppearance").value(DEFAULT_FIRST_APPEARANCE.toString()))
            .andExpect(jsonPath("$.universe").value(DEFAULT_UNIVERSE.toString()))
            .andExpect(jsonPath("$.intelligence").value(DEFAULT_INTELLIGENCE.intValue()))
            .andExpect(jsonPath("$.strength").value(DEFAULT_STRENGTH.intValue()))
            .andExpect(jsonPath("$.speed").value(DEFAULT_SPEED.intValue()))
            .andExpect(jsonPath("$.durability").value(DEFAULT_DURABILITY.intValue()))
            .andExpect(jsonPath("$.power").value(DEFAULT_POWER.intValue()))
            .andExpect(jsonPath("$.combat").value(DEFAULT_COMBAT.intValue()))
            .andExpect(jsonPath("$.total").value(DEFAULT_TOTAL.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingHero() throws Exception {
        // Get the hero
        restHeroMockMvc.perform(get("/api/heroes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHero() throws Exception {
        // Initialize the database
        heroRepository.saveAndFlush(hero);

        int databaseSizeBeforeUpdate = heroRepository.findAll().size();

        // Update the hero
        Hero updatedHero = heroRepository.findById(hero.getId()).get();
        // Disconnect from session so that the updates on updatedHero are not directly saved in db
        em.detach(updatedHero);
        updatedHero
            .name(UPDATED_NAME)
            .affilition(UPDATED_AFFILITION)
            .eyeColor(UPDATED_EYE_COLOR)
            .hairColor(UPDATED_HAIR_COLOR)
            .gender(UPDATED_GENDER)
            .status(UPDATED_STATUS)
            .year(UPDATED_YEAR)
            .firstAppearance(UPDATED_FIRST_APPEARANCE)
            .universe(UPDATED_UNIVERSE)
            .intelligence(UPDATED_INTELLIGENCE)
            .strength(UPDATED_STRENGTH)
            .speed(UPDATED_SPEED)
            .durability(UPDATED_DURABILITY)
            .power(UPDATED_POWER)
            .combat(UPDATED_COMBAT)
            .total(UPDATED_TOTAL);

        restHeroMockMvc.perform(put("/api/heroes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedHero)))
            .andExpect(status().isOk());

        // Validate the Hero in the database
        List<Hero> heroList = heroRepository.findAll();
        assertThat(heroList).hasSize(databaseSizeBeforeUpdate);
        Hero testHero = heroList.get(heroList.size() - 1);
        assertThat(testHero.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testHero.getAffilition()).isEqualTo(UPDATED_AFFILITION);
        assertThat(testHero.getEyeColor()).isEqualTo(UPDATED_EYE_COLOR);
        assertThat(testHero.getHairColor()).isEqualTo(UPDATED_HAIR_COLOR);
        assertThat(testHero.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testHero.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testHero.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testHero.getFirstAppearance()).isEqualTo(UPDATED_FIRST_APPEARANCE);
        assertThat(testHero.getUniverse()).isEqualTo(UPDATED_UNIVERSE);
        assertThat(testHero.getIntelligence()).isEqualTo(UPDATED_INTELLIGENCE);
        assertThat(testHero.getStrength()).isEqualTo(UPDATED_STRENGTH);
        assertThat(testHero.getSpeed()).isEqualTo(UPDATED_SPEED);
        assertThat(testHero.getDurability()).isEqualTo(UPDATED_DURABILITY);
        assertThat(testHero.getPower()).isEqualTo(UPDATED_POWER);
        assertThat(testHero.getCombat()).isEqualTo(UPDATED_COMBAT);
        assertThat(testHero.getTotal()).isEqualTo(UPDATED_TOTAL);

        // Validate the Hero in Elasticsearch
        verify(mockHeroSearchRepository, times(1)).save(testHero);
    }

    @Test
    @Transactional
    public void updateNonExistingHero() throws Exception {
        int databaseSizeBeforeUpdate = heroRepository.findAll().size();

        // Create the Hero

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHeroMockMvc.perform(put("/api/heroes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hero)))
            .andExpect(status().isBadRequest());

        // Validate the Hero in the database
        List<Hero> heroList = heroRepository.findAll();
        assertThat(heroList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Hero in Elasticsearch
        verify(mockHeroSearchRepository, times(0)).save(hero);
    }

    @Test
    @Transactional
    public void deleteHero() throws Exception {
        // Initialize the database
        heroRepository.saveAndFlush(hero);

        int databaseSizeBeforeDelete = heroRepository.findAll().size();

        // Delete the hero
        restHeroMockMvc.perform(delete("/api/heroes/{id}", hero.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Hero> heroList = heroRepository.findAll();
        assertThat(heroList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Hero in Elasticsearch
        verify(mockHeroSearchRepository, times(1)).deleteById(hero.getId());
    }

    @Test
    @Transactional
    public void searchHero() throws Exception {
        // Initialize the database
        heroRepository.saveAndFlush(hero);
        when(mockHeroSearchRepository.search(queryStringQuery("id:" + hero.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(hero), PageRequest.of(0, 1), 1));
        // Search the hero
        restHeroMockMvc.perform(get("/api/_search/heroes?query=id:" + hero.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hero.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].affilition").value(hasItem(DEFAULT_AFFILITION)))
            .andExpect(jsonPath("$.[*].eyeColor").value(hasItem(DEFAULT_EYE_COLOR)))
            .andExpect(jsonPath("$.[*].hairColor").value(hasItem(DEFAULT_HAIR_COLOR)))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR.intValue())))
            .andExpect(jsonPath("$.[*].firstAppearance").value(hasItem(DEFAULT_FIRST_APPEARANCE)))
            .andExpect(jsonPath("$.[*].universe").value(hasItem(DEFAULT_UNIVERSE)))
            .andExpect(jsonPath("$.[*].intelligence").value(hasItem(DEFAULT_INTELLIGENCE.intValue())))
            .andExpect(jsonPath("$.[*].strength").value(hasItem(DEFAULT_STRENGTH.intValue())))
            .andExpect(jsonPath("$.[*].speed").value(hasItem(DEFAULT_SPEED.intValue())))
            .andExpect(jsonPath("$.[*].durability").value(hasItem(DEFAULT_DURABILITY.intValue())))
            .andExpect(jsonPath("$.[*].power").value(hasItem(DEFAULT_POWER.intValue())))
            .andExpect(jsonPath("$.[*].combat").value(hasItem(DEFAULT_COMBAT.intValue())))
            .andExpect(jsonPath("$.[*].total").value(hasItem(DEFAULT_TOTAL.intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Hero.class);
        Hero hero1 = new Hero();
        hero1.setId(1L);
        Hero hero2 = new Hero();
        hero2.setId(hero1.getId());
        assertThat(hero1).isEqualTo(hero2);
        hero2.setId(2L);
        assertThat(hero1).isNotEqualTo(hero2);
        hero1.setId(null);
        assertThat(hero1).isNotEqualTo(hero2);
    }
}
