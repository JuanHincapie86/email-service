package co.vivaeventos.emailservice.controller;

import co.vivaeventos.emailservice.dto.EmailRequest;
import co.vivaeventos.emailservice.service.EmailSenderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/emails")
@RequiredArgsConstructor
@Slf4j
public class EmailController {

    private final EmailSenderService emailSenderService;

    @PostMapping("/send")
    public ResponseEntity<Void> triggerEmailDistribution(@RequestBody EmailRequest request) {
        log.info("📥 Solicitud de envío recibida para: {}", request.getTo());
        
        // Ejecución en un hilo separado para que responda inmediatamente (no bloqueante)
        new Thread(() -> {
            emailSenderService.sendSimpleEmail(request.getTo(), request.getSubject(), request.getBody());
        }).start();

        return ResponseEntity.ok().build();
    }
}