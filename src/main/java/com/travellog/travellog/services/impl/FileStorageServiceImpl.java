package com.travellog.travellog.services.impl;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.compress.utils.FileNameUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.travellog.travellog.dtos.minio.MinioConfigurationDto;
import com.travellog.travellog.exceptions.EntityException;
import com.travellog.travellog.dtos.files.AddFileResponseDto;
import com.travellog.travellog.dtos.files.FileResponseDto;
import com.travellog.travellog.services.spec.IFileStorageService;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.StatObjectArgs;
import io.minio.StatObjectResponse;
import io.minio.errors.ErrorResponseException;
import jakarta.persistence.EntityNotFoundException;

@Service
public class FileStorageServiceImpl implements IFileStorageService {
    private final MinioClient minioClient;
    private final MinioConfigurationDto minioConfiguration;

    public FileStorageServiceImpl(MinioClient minioClient, MinioConfigurationDto minioConfiguration) {
        this.minioClient = minioClient;
        this.minioConfiguration = minioConfiguration;
    }

    @Override
    public AddFileResponseDto addFile(MultipartFile multipartFile, String fileName) {
        try {
            String extension = FileNameUtils.getExtension(fileName);
            String uuid = UUID.randomUUID().toString();
            resizeAndSaveImages(multipartFile.getInputStream(), minioClient, new int[]{100, 200, 300}, extension,
                    uuid,
                    multipartFile.getContentType());
            Path fullPath = Paths.get(minioConfiguration.getMinioBucket(), uuid + "." + extension);
            return new AddFileResponseDto(fullPath.toString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public List<AddFileResponseDto> addMultipleFiles(MultipartFile[] multipartFiles) {
        List<AddFileResponseDto> responses = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            String fileName = multipartFile.getOriginalFilename();
            AddFileResponseDto responseDto = addFile(multipartFile, fileName);

            if (responseDto != null) responses.add(responseDto);
        }

        return responses;
    }

    @Override
    public AddFileResponseDto updateFile(MultipartFile multipartFile, String existingFileName) {
        try {
            String extension = FileNameUtils.getExtension(existingFileName);
            String uuid = existingFileName.substring(0, existingFileName.lastIndexOf('.'));

            resizeAndSaveImages(multipartFile.getInputStream(), minioClient, new int[]{100, 200, 300}, extension, uuid, multipartFile.getContentType());

            Path fullPath = Paths.get(minioConfiguration.getMinioBucket(), uuid + "." + extension);
            return new AddFileResponseDto(fullPath.toString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public FileResponseDto getFile(String fileName) {
        try {
            Path path = Path.of(fileName);
            StatObjectResponse metadata = getMetaData(path.toString());
            // only get object if it is present.
            GetObjectArgs args = GetObjectArgs.builder()
                    .bucket(minioConfiguration.getMinioBucket())
                    .object(fileName)
                    .build();
            InputStream stream = minioClient.getObject(args);
            InputStreamResource inputStreamResource = new InputStreamResource(stream);
            return FileResponseDto.builder()
                    .filename(fileName)
                    .fileSize(metadata.size())
                    .contentType(metadata.contentType())
                    .stream(inputStreamResource)
                    .build();

        } catch (EntityNotFoundException e) {
            throw new EntityException.NotFoundException("Image");
        } catch (Exception e) {
            throw new InternalError();
        }
    }

    @Override
    public String getFormattedFilePath(String fileName) {
        return String.format("%s/%s", minioConfiguration.getMinioEndpoint(), fileName);
    }

    private StatObjectResponse getMetaData(String path) {
        try {
            StatObjectArgs args = StatObjectArgs.builder()
                    .bucket(this.minioConfiguration.getMinioBucket())
                    .object(path)
                    .build();
            return minioClient.statObject(args);
        } catch (ErrorResponseException e) {
            throw new EntityNotFoundException("Image");
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    private void resizeAndSaveImages(InputStream inputStream, MinioClient minioClient, int[] sizes, String extension,
                                     String imageName,
                                     String contentType)
            throws IOException {
        try {
            minioClient.putObject(PutObjectArgs.builder().bucket(minioConfiguration.getMinioBucket())
                    .object(imageName + "." + extension)
                    .stream(inputStream, inputStream.available(), -1).contentType(contentType).build());

        } catch (Exception e) {
            System.out.println("error" + e.getMessage());
            throw new RuntimeException();
        }
    }
}
