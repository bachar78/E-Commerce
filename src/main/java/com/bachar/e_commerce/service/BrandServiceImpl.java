package com.bachar.e_commerce.service;

import com.bachar.e_commerce.entity.Brand;
import com.bachar.e_commerce.model.BrandResponse;
import com.bachar.e_commerce.repository.BrandRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;

    public BrandServiceImpl(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }


    @Override
    public List<BrandResponse> getAllBrands() {
        log.info("Fetching all brands!!!!");
        // fetch brands
        List<Brand> brandsList = brandRepository.findAll();
        //Use stream operator to map with response
        List<BrandResponse> brandResponse = brandsList.stream()
                .map(this::converToBrandResponse)
                .collect(Collectors.toList());
        log.info("All brands are fetched!!!!");
        return brandResponse;
    }

    private BrandResponse converToBrandResponse(Brand brand) {
        return BrandResponse.builder().id(brand.getId())
                .name(brand.getName())
                .build();
    }
}
