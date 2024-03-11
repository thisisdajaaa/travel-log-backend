package com.travellog.travellog.services.spec;


import org.springframework.web.multipart.MultipartFile;

import com.travellog.travellog.dtos.files.AddFileResponseDto;
import com.travellog.travellog.dtos.files.FileResponseDto;

import java.util.List;

public interface IFileStorageService {

    AddFileResponseDto addFile(MultipartFile multipartFile, String fileName);

    FileResponseDto getFile(String fileName);

    String getFormattedFilePath(String fileName);

    List<AddFileResponseDto> addMultipleFiles(MultipartFile[] multipartFiles);

   AddFileResponseDto updateFile(MultipartFile multipartFile, String existingFileName);
}
