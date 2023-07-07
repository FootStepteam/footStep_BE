package com.example.footstep.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.example.footstep.model.entity.Member;
import com.example.footstep.model.entity.ShareRoom;
import com.example.footstep.model.repository.MemberRepository;
import com.example.footstep.model.repository.ShareRoomRepository;
import java.io.IOException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class UploadService {

    private final AmazonS3Client amazonS3Client;
    private final ShareRoomRepository shareRoomRepository;
    private final MemberRepository memberRepository;
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;


    public String uploadS3File(MultipartFile file, Long shareRoomId) {

        try {

            ShareRoom shareRoom = shareRoomRepository.getShareById(shareRoomId);
            String fileName = file.getOriginalFilename() + UUID.randomUUID().toString();

            String fileUrl = "https://" + bucket + "/test" + fileName;

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(file.getContentType());
            metadata.setContentLength(file.getSize());

            amazonS3Client.putObject(bucket, fileName, file.getInputStream(), metadata);
            shareRoom.setS3Url(amazonS3Client.getUrl(bucket, fileName).toString());
            shareRoomRepository.save(shareRoom);

            return fileUrl;

        } catch (IOException e) {

            return String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public String uploadProfile(MultipartFile file, Long memberId) {

        try {

            Member member = memberRepository.getMemberById(memberId);
            String fileName = file.getOriginalFilename() + UUID.randomUUID().toString();

            String fileUrl = "https://" + bucket + "/test" + fileName;

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(file.getContentType());
            metadata.setContentLength(file.getSize());

            amazonS3Client.putObject(bucket, fileName, file.getInputStream(), metadata);
//            shareRoom.setS3Url(amazonS3Client.getUrl(bucket, fileName).toString());
//            shareRoomRepository.save(shareRoom);
            return amazonS3Client.getUrl(bucket, fileName).toString();

        } catch (IOException e) {

            return String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
