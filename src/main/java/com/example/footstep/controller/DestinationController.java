package com.example.footstep.controller;

import com.example.footstep.domain.dto.schedule.DestinationDto;
import com.example.footstep.domain.form.DestinationForm;
import com.example.footstep.service.DestinationService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/share-room")
public class DestinationController {

    private final DestinationService destinationService;


    @PostMapping("/{shareId}/destination")
    public ResponseEntity<DestinationDto> createDestination(
        @PathVariable("shareId") Long shareId,
        @RequestBody @Valid DestinationForm destinationForm) {

        return ResponseEntity.ok(destinationService.createDestination(shareId, destinationForm));
    }


    @DeleteMapping("/{shareId}/destination/{destinationId}")
    public ResponseEntity<String> deleteDestination(
        @PathVariable("shareId") Long shareId, @PathVariable("destinationId") Long destinationId) {

        return ResponseEntity.ok(destinationService.deleteDestination(shareId, destinationId));
    }
}
