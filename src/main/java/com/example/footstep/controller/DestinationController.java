package com.example.footstep.controller;

import com.example.footstep.domain.dto.schedule.DayScheduleDto;
import com.example.footstep.domain.dto.schedule.ScheduleDto;
import com.example.footstep.domain.form.DestinationForm;
import com.example.footstep.service.DestinationService;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/share-room")
public class DestinationController {

    private final DestinationService destinationService;


    @GetMapping("/{shareId}/destination")
    public ResponseEntity<List<DayScheduleDto>> getAllListDestination(
        @PathVariable("shareId") Long shareId) {

        return ResponseEntity.ok(destinationService.getAllListDestination(shareId));
    }


    @GetMapping("/{shareId}/destination/plan")
    public ResponseEntity<DayScheduleDto> getAllListDestinationDate(
        @PathVariable("shareId") Long shareId, @RequestParam("date") String planDate) {

        return ResponseEntity.ok(destinationService.getAllListDestinationDate(shareId, planDate));
    }


    @PostMapping("/{shareId}/destination")
    public ResponseEntity<ScheduleDto> createDestination(
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
