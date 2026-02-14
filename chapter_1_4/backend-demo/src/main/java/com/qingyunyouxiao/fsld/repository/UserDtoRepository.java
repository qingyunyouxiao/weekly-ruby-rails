package com.qingyunyouxiao.fsld.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.qingyunyouxiao.fsld.dtos.UserDto;
import java.util.Optional;

public interface UserDtoRepository extends MongoRepository<UserDto, String> {
    Optional<UserDto> findByName(String name);
}