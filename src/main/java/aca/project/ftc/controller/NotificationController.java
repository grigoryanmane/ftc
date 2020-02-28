package aca.project.ftc.controller;

import aca.project.ftc.model.dto.request.notification.NotificationEditRequestDto;
import aca.project.ftc.model.dto.request.notification.NotificationRequestDto;
import aca.project.ftc.model.dto.response.notification.NotificationResponseDto;
import aca.project.ftc.repository.NotificationRepository;
import aca.project.ftc.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;

@RestController
@CrossOrigin(value = "*", origins = "*", allowedHeaders = "*")
@RequestMapping(value = "/api/v1/notification")
@PermitAll
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping
    public ResponseEntity<NotificationResponseDto> addNotification(@RequestBody NotificationRequestDto notificationRequestDto) {
        NotificationResponseDto notificationResponseDto = notificationService.addNotification(notificationRequestDto);
        return ResponseEntity.ok(notificationResponseDto);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<NotificationResponseDto> editNotification(@RequestBody NotificationEditRequestDto notificationEditRequestDto, @PathVariable Long id) {
        NotificationResponseDto notificationResponseDto = notificationService.editNotification(notificationEditRequestDto, id);
        return ResponseEntity.ok(notificationResponseDto);
    }
}
