package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.service.ImageService;

@Slf4j
@RequiredArgsConstructor
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping(path = "/image")
public class ImageController {

    private  final ImageService imageService;

    @Operation(summary = "getAdsImage",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_OCTET_STREAM_VALUE,
                                    schema = @Schema(implementation = byte[].class)
                            )
                    ),
                    @ApiResponse(responseCode = "404", content = @Content)
            })
    @GetMapping(value = "{id}", produces = {MediaType.IMAGE_PNG_VALUE})
    public ResponseEntity<byte[]> getAdsImage(@PathVariable Long id) {
        return ResponseEntity.ok(imageService.getAdsImage(id));
    }
}
