package Gabojago.gabojago_be.file;

import Gabojago.gabojago_be.dto.response.UploadResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
@Slf4j
public class FileController {

    private final S3FileService s3FileService;

    @PostMapping("/upload")
    public ResponseEntity<UploadResponseDto> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            return ResponseEntity.ok(s3FileService.upload(file));
        } catch (Exception e) {
            log.info(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteFile(@RequestParam("fileUrl") String fileUrl) {
        try {
            // S3에서 파일 삭제
            s3FileService.delete(fileUrl);
            return ResponseEntity.ok("파일 삭제 성공");
        } catch (Exception e) {
            log.info(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("파일 삭제 실패");
        }
    }
}
