package com.travellog.travellog.services.impl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.apache.commons.compress.utils.FileNameUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.travellog.travellog.payload.AddFileResponse;
import com.travellog.travellog.payload.FileResponse;
import com.travellog.travellog.services.spec.IFileStorageService;

import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.StatObjectArgs;
import io.minio.StatObjectResponse;
import io.minio.errors.ErrorResponseException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class FileStorageService implements IFileStorageService {

    private final String bucketName = "test";
    private final String path = "hello.jpg";
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
    public AddFileResponse addFile(MultipartFile multipartFile, String fileName) {
        try {
            String extension = FileNameUtils.getExtension(fileName);
            String uuid = UUID.randomUUID().toString();
            resizeAndSaveImages(multipartFile.getInputStream(), getClient(), new int[] { 100, 200, 300 }, extension,
                    uuid,
                    multipartFile.getContentType());
            Path fullPath = Paths.get(bucketName, uuid + "." + extension);
            return new AddFileResponse(fullPath.toString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;

    }
;
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

    private void resizeAndSaveImages(InputStream inputStream, MinioClient minioClient, int[] sizes, String extension,
            String imageName,
            String contentType)
            throws IOException {
        try {
            // minioClient.putObject(PutObjectArgs.builder().bucket(bucketName).object(imageName + "." + extension)
            //         .stream(inputStream, inputStream.available(), -1).contentType(contentType).build());
            // save original image
            BufferedImage originalImage = ImageIO.read(inputStream);
            // Resize and save images
            for (int size : sizes) {
                BufferedImage resizedImage = resize(originalImage, size, size);

                // Convert BufferedImage to InputStream
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                ImageIO.write(resizedImage, contentType.split("/")[1], os);
                InputStream resizedInputStream = new ByteArrayInputStream(os.toByteArray());
                // Upload image to MinIO
                minioClient.putObject(
                        PutObjectArgs.builder()
                                .bucket(bucketName)
                                .object(imageName + "_" + size + "." + extension)
                                .stream(resizedInputStream, resizedInputStream.available(), -1)
                                .contentType(contentType)
                                .build());
                resizedInputStream.close();
            }
        } catch (Exception e) {
            System.out.println("error" + e.getMessage());
            throw new RuntimeException();
        }
    }

    private static BufferedImage resize(BufferedImage originalImage, int targetWidth, int targetHeight) {
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        // resizedImage.createGraphics().drawImage(
        //         originalImage.getScaledInstance(targetWidth, targetHeight, java.awt.Image.SCALE_SMOOTH), 0, 0, null);
        return originalImage;
    }

}
