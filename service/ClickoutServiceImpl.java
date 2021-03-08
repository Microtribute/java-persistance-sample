package com.organization.backend.service;

import com.organization.backend.domain.entity.Combination;
import com.organization.backend.domain.entity.CombinationClickout;
import com.organization.backend.domain.entity.Price;
import com.organization.backend.domain.entity.Product;
import com.organization.backend.domain.entity.ProductClickout;
import com.organization.backend.domain.repository.ClickoutRepository;
import com.organization.backend.domain.repository.CombinationRepository;
import com.organization.backend.domain.repository.ProductRepository;
import com.organization.backend.service.exception.ClickoutServiceException;
import com.organization.backend.shop.Shop;
import com.organization.web.dto.ProductDto;
import com.organization.web.helper.PriceUtil;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.stream.Collectors;

import javax.annotation.Nullable;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;


@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ClickoutServiceImpl implements ClickoutService {

    @Autowired
    ClickoutRepository clickoutRepository;

    @Autowired
    CombinationRepository combinationRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ModelMapper modelMapper;

    @Transactional
    @Override
    public ProductClickout generateProductClickOut(long productId, String cookieHash) {

        assert !StringUtils.isEmpty(cookieHash);

        Product product = productRepository.findOne(productId);

        if (product == null) {
            throw new ClickoutServiceException("Can not find a product to given id: " + productId);
        }

        ProductClickout clickout = new ProductClickout(
                product,
                cookieHash,
                PriceUtil.getDisplayPrice(modelMapper.map(product, ProductDto.class)),
                product.getProvider().name());
        clickoutRepository.save(clickout);

        return clickout;
    }

    @Transactional
    @Override
    public CombinationClickout generateCombinationClickOut(long combinationId, String cookieHash, @Nullable Long[] excludeProductIds) {

        Combination combination = combinationRepository.findOne(combinationId);

        if (combination == null) {
            throw new ClickoutServiceException("Can not find a combination to given id: " + combinationId);
        }

        Price transactionTotal = PriceUtil.getSumOfDisplayPrices(
                combination.getProducts().stream().map(
                        product -> modelMapper.map(product, ProductDto.class))
                        .collect(Collectors.toList()));

        CombinationClickout clickout = new CombinationClickout(
                combination,
                cookieHash,
                transactionTotal,
                // TODO now this will always be AMAZON but later on its hard to say which it was
                Shop.Name.AMAZON.name());
        return clickoutRepository.save(clickout);
    }
}

