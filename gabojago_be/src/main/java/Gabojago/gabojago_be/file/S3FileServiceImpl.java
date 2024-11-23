package Gabojago.gabojago_be.file;

import Gabojago.gabojago_be.dto.response.UploadResponseDto;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor

public class S3FileServiceImpl implements S3FileService {
    private final AmazonS3Client amazonS3Client;
    private final S3FileServiceUtil s3FileServiceUtil;

    @Value("${s3.bucketName}")
    private String bucketName;

    @Override
    public UploadResponseDto upload(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());
        UploadResponseDto response = new UploadResponseDto();

        try {
            // 파일을 S3에 업로드
            amazonS3Client.putObject(bucketName, fileName, file.getInputStream(), metadata);
            response.setUrl(amazonS3Client.getUrl(bucketName, fileName).toString());
            return response;
        } catch (IOException e) {
            throw new RuntimeException("파일 업로드 중 오류가 발생했습니다.", e);
        }
    }

    @Override
    public void delete(String fileUrl) {
        try {
            String key = s3FileServiceUtil.extractKeyFromUrl(fileUrl);
            if (!(key != null && !key.isEmpty())) {
                throw new IllegalArgumentException("잘못된 URL입니다.");
            }
            amazonS3Client.deleteObject(new DeleteObjectRequest(bucketName, key));
            System.out.println("삭제 완료: " + key);

        } catch (Exception e) {
            throw new RuntimeException("파일 삭제 실패 : " + e.getMessage());
        }
    }

    @Override
    public String getImgUrl(String fileUrl) {
        try {
            String key = s3FileServiceUtil.extractKeyFromUrl(fileUrl);
            if (!(key != null && !key.isEmpty())) {
                throw new IllegalArgumentException("잘못된 URL입니다.");
            }
            return amazonS3Client.getUrl(bucketName, key).toString();
        } catch (Exception e) {
            throw new RuntimeException("파일 조회 실패 : " + e.getMessage());
        }
    }

}
