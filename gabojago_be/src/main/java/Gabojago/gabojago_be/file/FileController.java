package Gabojago.gabojago_be.file;

import Gabojago.gabojago_be.dto.response.UploadResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "유저 프로필 이미지", description = "업로드, 삭제 기능")
public class FileController {

    private final S3FileService s3FileService;

    @Operation(summary = "파일 업로드", description = "MultipartFile 형식으로 제공한 파일을 S3에 업로드합니다.")
    @PostMapping("/upload")
    public ResponseEntity<UploadResponseDto> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            return ResponseEntity.ok(s3FileService.upload(file));
        } catch (Exception e) {
            log.info(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "파일 삭제", description = "RequestParam에 담긴 fileUrl에 해당하는 정보를 S3에서 삭제합니다.")
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteFile(@RequestParam("fileUrl") String fileUrl) {
        try {
            s3FileService.delete(fileUrl);
            return ResponseEntity.ok("파일 삭제 성공");
        } catch (Exception e) {
            log.info(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("파일 삭제 실패");
        }
    }
}
