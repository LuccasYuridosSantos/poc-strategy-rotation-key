package com.luccas.schedulerpii.repository;

import com.luccas.schedulerpii.model.dto.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepository{

    private final static Logger LOGGER = LoggerFactory.getLogger(UserRepository.class);

    private final MongoTemplate mongoTemplate;

    public UserRepository(final MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public List<UserDTO> findAllByEncryptedVersionNot(final String version) {
        Query query = new Query();
        query.addCriteria(Criteria.where("encrypted.version").ne(version));
        return mongoTemplate.find(query, UserDTO.class);
    }

    public List<UserDTO> findAllByEncryptedTimestamp(final Long timestamp) {
        Query query = new Query();
        query.addCriteria(Criteria.where("encrypted.timestamp").lte(timestamp));
        return mongoTemplate.find(query, UserDTO.class);
    }

    public void updateAll(List<UserDTO> users) {
        users.forEach(user -> {
            LOGGER.info("Updating user {}", user.getId());
            mongoTemplate.save(user);
        });
    }
}
