package com.travellog.travellog.controllers;

import com.travellog.travellog.helpers.ResponseHelper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.travellog.travellog.dtos.files.AddFileResponseDto;
import com.travellog.travellog.dtos.files.FileResponseDto;
import com.travellog.travellog.services.spec.IFileStorageService;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;


@RestController
@RequestMapping("/api/v1/images")
public class ImageController {
    private final IFileStorageService fileStorageService;

    public ImageController(IFileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Upload a file")
    public ResponseEntity<ResponseHelper.CustomResponse<AddFileResponseDto>> uploadImage(@RequestParam("image") MultipartFile file) {
        AddFileResponseDto response = fileStorageService.addFile(file, file.getOriginalFilename());

        return new ResponseEntity<>(
                new ResponseHelper.CustomResponse<>(true, "Successfully created country!",
                        response),
                HttpStatus.CREATED);
    }

    @PostMapping(value = "/multiple", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Upload multiple images")
    public ResponseEntity<ResponseHelper.CustomResponse<List<AddFileResponseDto>>> uploadMultipleImages(@RequestParam("images") MultipartFile[] files) {
        List<AddFileResponseDto> responses = fileStorageService.addMultipleFiles(files);

        return new ResponseEntity<>(
                new ResponseHelper.CustomResponse<>(true, "Successfully uploaded multiple images!", responses),
                HttpStatus.CREATED);
    }

    @GetMapping("/{file}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Download a File")
    public ResponseEntity<InputStreamResource> fetchImage(@PathVariable @NotEmpty String file) {
        FileResponseDto source = fileStorageService.getFile(file);

        return ResponseEntity
                .ok()
                .contentType(MediaType.parseMediaType(source.getContentType()))
                .contentLength(source.getFileSize())
                .header("Content-disposition", "attachment; filename=" + source.getFilename())
                .body(source.getStream());
    }

    @PostMapping(value = "/update/{filename}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update an image")
    public ResponseEntity<ResponseHelper.CustomResponse<AddFileResponseDto>> updateImage(@PathVariable String filename, @RequestParam("image") MultipartFile file) {
        AddFileResponseDto response = fileStorageService.updateFile(file, filename);

        return new ResponseEntity<>(
                new ResponseHelper.CustomResponse<>(true, "Successfully updated image!", response),
                HttpStatus.OK);
    }
}
