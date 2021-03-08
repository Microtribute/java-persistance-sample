package com.organization.backend.domain.entity;

import com.organization.web.helper.NiceUrlHelper;

import org.hibernate.annotations.Formula;
import org.springframework.cache.annotation.Cacheable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;


@Entity
@NoArgsConstructor
@Getter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@Cacheable("combination")
public class Combination implements Serializable {

    @Id
    @GeneratedValue
    Long id;

    @Setter
    String title;

    @Setter
    String url;

    @Setter
    boolean generatedByAmazon;

    @Setter
    int upVotes = 0;

    @Setter
    int downVotes = 0;

    @Formula("up_votes - down_votes")
    int ratingPoints = 0;

    @Setter
    @ManyToOne
    InternalCategory internalCategory;

    @ManyToMany(cascade = CascadeType.ALL)
    List<Product> products = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    List<Comment> comments = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL)
    List<Tag> tags = new ArrayList<>();

    public Combination(final String title, boolean generatedByAmazon, Product... products) {
        this.title = title;
        this.generatedByAmazon = generatedByAmazon;
        this.url = NiceUrlHelper.replaceSpaces(NiceUrlHelper.normalizeUrl(title));
        addProducts(Arrays.asList(products));
    }

    public Combination(final String title, boolean generatedByAmazon, List<Product> products) {
        this.title = title;
        this.generatedByAmazon = generatedByAmazon;
        this.url = NiceUrlHelper.replaceSpaces(NiceUrlHelper.normalizeUrl(title));
        this.addProducts(products);
    }

    public Combination addProduct(Product product) {
        this.products.add(product);
        product.setCombination(this);
        return this;
    }

    public Combination addProducts(Collection<Product> products) {
        this.products.addAll(products);
        for (Product product : products) {
            product.setCombination(this);
        }
        return this;
    }

    public Combination addComment(Comment comment) {
        this.comments.add(comment);
        comment.addCombinition(this);
        return this;
    }

    public Combination addTag(Tag tag) {
        this.tags.add(tag);
        tag.addCombination(this);
        return this;
    }
}
