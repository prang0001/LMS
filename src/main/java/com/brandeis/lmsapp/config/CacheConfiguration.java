package com.brandeis.lmsapp.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import io.github.jhipster.config.jcache.BeanClassLoaderAwareJCacheRegionFactory;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        BeanClassLoaderAwareJCacheRegionFactory.setBeanClassLoader(this.getClass().getClassLoader());
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(com.brandeis.lmsapp.repository.UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
            cm.createCache(com.brandeis.lmsapp.repository.UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
            cm.createCache(com.brandeis.lmsapp.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(com.brandeis.lmsapp.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(com.brandeis.lmsapp.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(com.brandeis.lmsapp.domain.LibraryResource.class.getName(), jcacheConfiguration);
            cm.createCache(com.brandeis.lmsapp.domain.Author.class.getName(), jcacheConfiguration);
            cm.createCache(com.brandeis.lmsapp.domain.Subject.class.getName(), jcacheConfiguration);
            cm.createCache(com.brandeis.lmsapp.domain.Patron.class.getName(), jcacheConfiguration);
            cm.createCache(com.brandeis.lmsapp.domain.LibraryResource.class.getName() + ".statuses", jcacheConfiguration);
            cm.createCache(com.brandeis.lmsapp.domain.LibraryResource.class.getName() + ".rentalTransactions", jcacheConfiguration);
            cm.createCache(com.brandeis.lmsapp.domain.LibraryResource.class.getName() + ".waitingLists", jcacheConfiguration);
            cm.createCache(com.brandeis.lmsapp.domain.Patron.class.getName() + ".rentalTransactions", jcacheConfiguration);
            cm.createCache(com.brandeis.lmsapp.domain.Patron.class.getName() + ".waitingLists", jcacheConfiguration);
            cm.createCache(com.brandeis.lmsapp.domain.Author.class.getName() + ".libraryResources", jcacheConfiguration);
            cm.createCache(com.brandeis.lmsapp.domain.Subject.class.getName() + ".libraryResources", jcacheConfiguration);
            cm.createCache(com.brandeis.lmsapp.domain.ResourceStatus.class.getName(), jcacheConfiguration);
            cm.createCache(com.brandeis.lmsapp.domain.ResourceStatus.class.getName() + ".libraryResources", jcacheConfiguration);
            cm.createCache(com.brandeis.lmsapp.domain.ResourceType.class.getName(), jcacheConfiguration);
            cm.createCache(com.brandeis.lmsapp.domain.ResourceType.class.getName() + ".libraryResources", jcacheConfiguration);
            cm.createCache(com.brandeis.lmsapp.domain.RentalTransaction.class.getName(), jcacheConfiguration);
            cm.createCache(com.brandeis.lmsapp.domain.RentalTransaction.class.getName() + ".libraryResources", jcacheConfiguration);
            cm.createCache(com.brandeis.lmsapp.domain.RentalTransaction.class.getName() + ".patrons", jcacheConfiguration);
            cm.createCache(com.brandeis.lmsapp.domain.WaitingList.class.getName(), jcacheConfiguration);
            cm.createCache(com.brandeis.lmsapp.domain.WaitingList.class.getName() + ".libraryResources", jcacheConfiguration);
            cm.createCache(com.brandeis.lmsapp.domain.WaitingList.class.getName() + ".patrons", jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
