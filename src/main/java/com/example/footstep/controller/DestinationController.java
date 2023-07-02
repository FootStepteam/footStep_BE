package com.example.footstep.controller;

import com.example.footstep.model.dto.schedule.DestinationDto;
import com.example.footstep.model.form.DestinationForm;
import com.example.footstep.service.DestinationService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/share-room/{shareId}")
public class DestinationController {

    private final DestinationService destinationService;


    @PostMapping("/destination")
    public ResponseEntity<DestinationDto> createDestination(
        @PathVariable("shareId") Long shareId,
        @RequestBody @Valid DestinationForm destinationForm) {

        return ResponseEntity.ok(destinationService.createDestination(shareId, destinationForm));
    }


    @DeleteMapping("/destination/plan")
    public void deleteDestinationPlanDate(
        @PathVariable("shareId") Long shareId, @RequestParam("date") String planDate) {

        destinationService.deleteDestinationPlanDate(shareId, planDate);
    }


    @DeleteMapping("/destination/{destinationId}")
    public void deleteDestination(
        @PathVariable("shareId") Long shareId, @PathVariable("destinationId") Long destinationId) {

        destinationService.deleteDestination(shareId, destinationId);
    }
}
