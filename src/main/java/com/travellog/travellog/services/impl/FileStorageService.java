package com.travellog.travellog.services.impl;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.travellog.travellog.payload.AddFileResponse;
import com.travellog.travellog.payload.FileResponse;
import com.travellog.travellog.services.spec.IFileStorageService;

import io.minio.BucketExistsArgs;
import io.minio.GetObjectArgs;
import io.minio.ListObjectsArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.StatObjectArgs;
import io.minio.StatObjectResponse;
import io.minio.errors.ErrorResponseException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class FileStorageService implements IFileStorageService {

    private final String bucketName = "test";
    private final String path = "hello";
    private MinioClient minioClient;

    private MinioClient getClient() {
        if (minioClient == null) {
            // TODO use jlefebure method for client initialization
            this.minioClient = MinioClient.builder()
                    .endpoint("http://localhost:9000")
                    .credentials("l9oczF1WnOly9wj707QJ", "KgVM0P5KqH1dCKlC4phdwCneygd5h0of3td7b44c")
                    .build();
        }
        // TODO make this client get when it is empty
        return minioClient;
    }

    @Override
    public AddFileResponse addFile(MultipartFile multipartFile) {
        try {
            PutObjectArgs args = PutObjectArgs.builder().bucket(bucketName).object(path)
                    .stream(multipartFile.getInputStream(), multipartFile.getInputStream().available(), -1)
                    .contentType(multipartFile.getContentType()).build();
            getClient().putObject(args);
            Path fullPath = Paths.get(bucketName, path);
            return new AddFileResponse(fullPath.toString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;

    }

    @Override
    public FileResponse getFile(String fileName) {
        try {
            Path path = Path.of(fileName);
            StatObjectResponse metadata = getMetaData(path.toString());
            // only get object if it is present.
            GetObjectArgs args = GetObjectArgs.builder()
                    .bucket(bucketName)
                    .object(fileName.toString())
                    .build();
            InputStream stream = minioClient.getObject(args);
            InputStreamResource inputStreamResource = new InputStreamResource(stream);
            return FileResponse.builder()
                    .filename(fileName)
                    .fileSize(metadata.size())
                    .contentType(metadata.contentType())
                    .stream(inputStreamResource)
                    .build();

        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException();
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    private StatObjectResponse getMetaData(String path) {
        try {
            StatObjectArgs args = StatObjectArgs.builder()
                    .bucket(this.bucketName)
                    .object(path)
                    .build();
            return getClient().statObject(args);
        } catch (ErrorResponseException e) {
            throw new EntityNotFoundException("Image");
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

}
