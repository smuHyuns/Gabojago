package Gabojago.gabojago_be.file;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class S3FileServiceUtil {

    @Value("${s3.bucketName}")
    private String bucketName;

    public String extractKeyFromUrl(String fileUrl) {
        String bucketUrl = String.format("https://%s.s3.amazonaws.com/", bucketName);
        if (fileUrl.startsWith(bucketUrl)) {
            return fileUrl.substring(bucketUrl.length());
        }

        String bucketUrlWithRegion = String.format("https://%s.s3.%s.amazonaws.com/", bucketName, "your-region");
        if (fileUrl.startsWith(bucketUrlWithRegion)) {
            return fileUrl.substring(bucketUrlWithRegion.length());
        }
        return null;
    }
}
