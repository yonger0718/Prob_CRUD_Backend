package tw.com.cathaybk.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tw.com.cathaybk.service.UploadService;

import java.io.IOException;

@RestController
@RequestMapping("/api/upload")
@Slf4j
@RequiredArgsConstructor
public class UploadController {

    private final UploadService uploadService;


    @PostMapping("/{service}/{id}")
    public ResponseEntity<String> upload(@PathVariable String service, @PathVariable Long id,
                                         @RequestParam("image") MultipartFile file
    ) {
        String imgUrl;
        try {
            imgUrl = uploadService.upload(file, id, service);
        } catch (IOException e) {
            throw new RuntimeException("執行失敗");
        }
        return ResponseEntity.ok(imgUrl);
    }
}
