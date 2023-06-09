package com.example.footstep.domain.repository;

import com.example.footstep.domain.entity.Likes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Likes, Long> {

}
