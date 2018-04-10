package com.khlebtsov.kalories;

import com.khlebtsov.kalories.entity.CaloriesPerUserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CaloriesPerUserRepository extends CrudRepository<CaloriesPerUserEntity, Long> {
    Optional<CaloriesPerUserEntity> findByUserId(Long userId);
}
