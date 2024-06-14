package com.bachar.e_commerce.Config;

import com.bachar.e_commerce.Mapper.OrderMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {
    @Bean
    public OrderMapper orderMapper(){
        return Mappers.getMapper(OrderMapper.class);
    }
}
