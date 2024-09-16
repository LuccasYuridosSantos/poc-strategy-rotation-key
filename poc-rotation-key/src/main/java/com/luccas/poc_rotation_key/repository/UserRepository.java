package com.luccas.poc_rotation_key.repository;

import com.luccas.poc_rotation_key.model.UserDTO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<UserDTO, String> {
}
