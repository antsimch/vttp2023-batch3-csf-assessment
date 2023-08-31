package vttp2023.batch3.csf.assessment.cnserver.repositories;

import java.io.IOException;
import java.util.UUID;

import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Repository
public class ImageRepository {
	
	private AmazonS3 s3;

    public ImageRepository(AmazonS3 s3) {
        this.s3 = s3;
    }

	// TODO: Task 1
    public String saveImage(MultipartFile file) {
        
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());
        
        String id = UUID.randomUUID().toString()
				.substring(0, 8);

        try {
            PutObjectRequest putReq = new PutObjectRequest(
                    "csf-news", 
                    "image/%s".formatted(id), 
                    file.getInputStream(), 
                    metadata);

            putReq = putReq.withCannedAcl(
                    CannedAccessControlList.PublicRead);

            s3.putObject(putReq);    
            System.out.println("\n\n" + id + "\n\n");

        } catch (IOException ex) {
        }
        return id;
    }

	public String getUrl(String id) {

        String key = "image/%s".formatted(id);

        return s3.getUrl("csf-news", key)
                .toExternalForm();
    }
}
