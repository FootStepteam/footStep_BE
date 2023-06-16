package com.example.footstep.controller;

import com.example.footstep.domain.dto.destination.redis.DestinationRedisListDto;
import com.example.footstep.domain.entity.redis.DestinationRedis;
import com.example.footstep.domain.form.DestinationRedisForm;
import com.example.footstep.service.DestinationService;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/room")
public class DestinationController {

    private final DestinationService destinationService;


    @GetMapping("/{shareId}/schedule")
    public ResponseEntity<List<DestinationRedisListDto>> getAllListDestination(
        @PathVariable("shareId") Long shareId) {

        return ResponseEntity.ok(destinationService.getAllListDestination(shareId));
    }


    @GetMapping("/{shareId}/schedule/plan")
    public ResponseEntity<DestinationRedisListDto> getAllListDestinationDate(
        @PathVariable("shareId") Long shareId, @RequestParam("date") String planDate) {

        return ResponseEntity.ok(destinationService.getAllListDestinationDate(shareId, planDate));
    }


    @PostMapping("/{shareId}/schedule")
    public ResponseEntity<DestinationRedis> createDestination(
        @PathVariable("shareId") Long shareId,
        @RequestBody @Valid DestinationRedisForm destinationRedisForm) {

        return ResponseEntity.ok(destinationService.createDestination(
            shareId, destinationRedisForm));
    }
}
