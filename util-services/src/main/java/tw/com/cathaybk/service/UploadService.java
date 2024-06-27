package tw.com.cathaybk.service;

import com.google.cloud.storage.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@Slf4j
public class UploadService {

    @Value("${gcs.bucket.name}")
    private String bucketName;

    private Storage storage = StorageOptions.getDefaultInstance().getService();

    public String upload(MultipartFile file, Long id, String loc) throws IOException {
        String[] fileNameParts = file.getOriginalFilename().split("\\.");
        if (fileNameParts.length < 2) {
            throw new RuntimeException("Invalid file format");
        }
        String fileName = loc + "/" + id.toString() + "." + fileNameParts[1];

        Bucket bucket = storage.get(bucketName);
        if (bucket == null) {
            throw new RuntimeException("Bucket not found: " + bucketName);
        }

        BlobId blobId = BlobId.of(bucket.getName(), fileName);
        Blob blob = storage.get(blobId);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();

        Storage.BlobWriteOption precondition;
        if (blob != null && blob.exists()) {
            precondition = Storage.BlobWriteOption.generationMatch(blob.getGeneration());
        } else {
            precondition = Storage.BlobWriteOption.doesNotExist();
        }

        Blob upload = storage.createFrom(blobInfo, file.getInputStream(), precondition);
        storage.createAcl(blobId, Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER));
        log.debug("File: " + fileName + " uploaded to " + bucketName + " as " + fileName);
        return upload.getName();
    }
}
