package com.travellog.travellog.services.spec;


import org.springframework.web.multipart.MultipartFile;

import com.travellog.travellog.payload.AddFileResponse;
import com.travellog.travellog.payload.FileResponse;

public interface IFileStorageService {

    AddFileResponse addFile(MultipartFile multipartFile);

    FileResponse getFile(String fileName);

}
