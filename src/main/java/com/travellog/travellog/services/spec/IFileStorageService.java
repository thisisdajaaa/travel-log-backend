package com.travellog.travellog.services.spec;


import org.springframework.web.multipart.MultipartFile;

import com.travellog.travellog.dtos.files.AddFileResponseDto;
import com.travellog.travellog.dtos.files.FileResponseDto;

public interface IFileStorageService {

    AddFileResponseDto addFile(MultipartFile multipartFile, String fileName);

    FileResponseDto getFile(String fileName);

}
