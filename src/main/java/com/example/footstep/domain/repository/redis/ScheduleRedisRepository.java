package com.example.footstep.domain.repository.redis;

import com.example.footstep.domain.entity.redis.DestinationRedis;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRedisRepository extends CrudRepository<DestinationRedis, Long> {

}

