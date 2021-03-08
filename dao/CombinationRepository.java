package com.organization.backend.domain.repository;

import com.organization.backend.domain.entity.Combination;
import com.organization.backend.domain.entity.InternalCategory;
import com.organization.backend.domain.entity.Product;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface CombinationRepository extends CrudRepository<Combination, Long> {

    @Cacheable("product_id_combination")
    @Query("SELECT c FROM Combination c LEFT JOIN c.products p WHERE p.id = :productId")
    List<Combination> findByProductId(@Param("productId") long productId);

    @Query("SELECT distinct (c) FROM Combination c LEFT JOIN c.products p WHERE p in (:products)")
    List<Combination> findByProducts(@Param("products") List<Product> products, Pageable pageable);

    Combination findOneByTitle(String title);

    Combination findOneByUrl(String url);

    List<Combination> findAllByInternalCategory(InternalCategory internalCategory);

    // method-name looks kind of strange but has to be that way to avoid problems on startup
    @Cacheable("rating_combination")
    List<Combination> findAllByOrderByRatingPointsDesc();
}
