package org.limbusnoe.controller;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@RestController
@RequestMapping("api/cloud")
@RequiredArgsConstructor
public class YandexCloudController {
    @Value("${s3.bucket}")
    private String bucket;
    private final AmazonS3 s3Client;

    @GetMapping("download/{file}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable String file) throws IOException {
        GetObjectRequest objectRequest = new GetObjectRequest(bucket, file);
        var inputStream = s3Client.getObject(objectRequest);
        byte[] data = inputStream.getObjectContent().readAllBytes();
        var headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline");
        headers.add(HttpHeaders.CONTENT_TYPE, inputStream.getObjectMetadata().getContentType());
        return ResponseEntity.ok()
                .headers(headers)
                .body(data);
    }
    @PutMapping(value = "upload", consumes = MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadFile(@RequestParam MultipartFile file) throws IOException{
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentDisposition("inline");
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());
        String key = file.getOriginalFilename();
        PutObjectRequest objectRequest = new PutObjectRequest(bucket, key, file.getInputStream(), metadata);
        s3Client.putObject(objectRequest);
        return ResponseEntity.ok(key);
    }
}
