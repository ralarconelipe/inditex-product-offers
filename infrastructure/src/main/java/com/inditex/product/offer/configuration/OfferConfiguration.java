package com.inditex.product.offer.configuration;

import com.inditex.product.offer.port.dao.OfferDao;
import com.inditex.product.offer.port.repository.OfferRepository;
import com.inditex.product.offer.service.OfferCreateService;
import com.inditex.product.offer.service.OfferDeleteService;
import com.inditex.product.offer.service.OfferGetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

/**
 * Configuration class for defining beans related to offer services.
 * <p>
 * This class provides Spring-managed beans for the service layer, including {@link OfferGetService},
 * {@link OfferCreateService}, and {@link OfferDeleteService}. It ensures the proper wiring of dependencies such as
 * {@link OfferDao} and {@link OfferRepository}.
 * </p>
 *
 * @author [product-offers@inditex.es]
 * @since 1.0.0
 */
@Slf4j
@Configuration
public class OfferConfiguration {

    /**
     * Provides a bean for {@link OfferGetService}.
     *
     * @param offerDao the DAO used for retrieving offers
     * @return an instance of {@link OfferGetService}
     */
    @Bean
    public OfferGetService offerGetService(@Lazy OfferDao offerDao) {
        LOGGER.info("[offerGetService] Creating OfferGetService bean");
        return new OfferGetService(offerDao);
    }

    /**
     * Provides a bean for {@link OfferCreateService}.
     *
     * @param offerRepository the repository used for persisting offers
     * @return an instance of {@link OfferCreateService}
     */
    @Bean
    public OfferCreateService offerCreateService(@Lazy OfferRepository offerRepository) {
        LOGGER.info("[offerCreateService] Creating OfferCreateService bean");
        return new OfferCreateService(offerRepository);
    }

    /**
     * Provides a bean for {@link OfferDeleteService}.
     *
     * @param offerRepository the repository used for deleting offers
     * @return an instance of {@link OfferDeleteService}
     */
    @Bean
    public OfferDeleteService offerDeleteService(@Lazy OfferRepository offerRepository) {
        LOGGER.info("[offerDeleteService] Creating OfferDeleteService bean");
        return new OfferDeleteService(offerRepository);
    }

}