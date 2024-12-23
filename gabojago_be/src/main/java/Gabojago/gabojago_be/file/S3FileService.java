package Gabojago.gabojago_be.file;


import Gabojago.gabojago_be.dto.response.UploadResponseDto;
import org.springframework.web.multipart.MultipartFile;

public interface S3FileService {
    UploadResponseDto upload(MultipartFile file);
    void delete(String fileName);
    String getImgUrl(String fileUrl);
}
