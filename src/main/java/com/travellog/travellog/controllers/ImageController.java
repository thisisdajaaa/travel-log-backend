package com.travellog.travellog.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.travellog.travellog.payload.AddFileResponse;
import com.travellog.travellog.payload.FileResponse;
import com.travellog.travellog.services.spec.IFileStorageService;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import io.swagger.v3.oas.annotations.Operation;



@RestController
@RequestMapping("/api/v1/image")
public class ImageController {

    private final IFileStorageService fileStorageService;
    
    public ImageController(IFileStorageService fileStorageService){
        this.fileStorageService = fileStorageService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Upload a file")
    public ResponseEntity<AddFileResponse> uploadImage(@RequestParam("image") MultipartFile file){
        AddFileResponse response = fileStorageService.addFile(file, file.getOriginalFilename());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{file}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Download a File")
    public ResponseEntity<InputStreamResource> fetchImage(@PathVariable String file){
        FileResponse source = fileStorageService.getFile(file);
        return ResponseEntity
                .ok()
                .contentType(MediaType.parseMediaType(source.getContentType()))
                .contentLength(source.getFileSize())
                .header("Content-disposition", "attachment; filename=" + source.getFilename())
                .body(source.getStream());
    }
}
