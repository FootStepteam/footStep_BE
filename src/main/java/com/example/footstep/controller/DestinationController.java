package com.example.footstep.controller;

import com.example.footstep.domain.entity.redis.DestinationRedis;
import com.example.footstep.domain.form.DestinationRedisForm;
import com.example.footstep.service.DestinationService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class DestinationController {

    private final DestinationService destinationService;

    @PostMapping("/{shareId}/schedule")
    public ResponseEntity<DestinationRedis> createDestination(
        //        @AuthenticationPrincipal Long memberId,
        @PathVariable("shareId") Long shareId,
        @RequestBody @Valid DestinationRedisForm destinationRedisForm) {

        return ResponseEntity.ok(destinationService.createDestination(
            1L, shareId, destinationRedisForm));
    }
}
