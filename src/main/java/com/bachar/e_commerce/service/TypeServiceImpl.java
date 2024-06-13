package com.bachar.e_commerce.service;

import com.bachar.e_commerce.entity.Brand;
import com.bachar.e_commerce.entity.Type;
import com.bachar.e_commerce.model.BrandResponse;
import com.bachar.e_commerce.model.TypeResponse;
import com.bachar.e_commerce.repository.TypeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TypeServiceImpl implements TypeService {

    private final TypeRepository typeRepository;

    public TypeServiceImpl(TypeRepository typeRepository) {
        this.typeRepository = typeRepository;
    }


    @Override
    public List<TypeResponse> getAllTypes() {
        log.info("Fetching all types!!!!");
        // fetch types
        List<Type> typesList = typeRepository.findAll();
        //Use stream operator to map with response
        List<TypeResponse> typeResponseList = typesList.stream()
                .map(this::convertToTypeResponse)
                .collect(Collectors.toList());
        log.info("All types are fetched!!!!");
        return typeResponseList;
    }

    private TypeResponse convertToTypeResponse(Type type) {
        return TypeResponse.builder().id(type.getId())
                .name(type.getName())
                .build();
    }
}
