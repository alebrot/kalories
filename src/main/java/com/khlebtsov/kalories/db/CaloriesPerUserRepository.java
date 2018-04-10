package com.khlebtsov.kalories.db;

import com.khlebtsov.kalories.db.entity.CaloriesPerUserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CaloriesPerUserRepository extends CrudRepository<CaloriesPerUserEntity, Long> {
    Optional<CaloriesPerUserEntity> findByUserId(Long userId);
}
