package com.recipe.db;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface LoginRepository extends MongoRepository<Login, Long> {
    Login findByUsername(String username);
}
