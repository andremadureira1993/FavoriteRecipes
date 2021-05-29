package com.recipe.db;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<Login, Long> {
    Login findByUsername(String username);
}
