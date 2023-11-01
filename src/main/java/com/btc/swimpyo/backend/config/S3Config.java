package com.btc.swimpyo.backend.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class S3Config {

    /* @Value 어노테이션을 사용하여 application.properties에서 설정된 AWS 계정 정보와 지역을 가져옵니다. */
    @Value("${cloud.aws.credentials.access-key}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secret-key}")
    private String secretKey;

    @Value("${cloud.aws.region.static}")
    private String region;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    @Bean
    public AmazonS3Client amazonS3Client() {
        
        // BasicAWSCredentials => aws accessKey와 secretKey를 포함하는 객체 생성 -> AWS에 액세스하기 위함
        // .withRegion(region) => 'region'을 사용하여 S3 클라이언트가 작동할 AWS 리전 설정
        // .withCredentials(new AWSStaticCredentialsProvider(awsCredentials)) => S3 클라이언트에게 accessKey, secretKey 제공하는 데에 사용할 AWSStaticCredentialsProvider를 설정. 이를 통해 클라이언트가 AWS 서비스에 액세스할 수 있음
        // .build(); => 설정이 완료된 빌더 객체에서 'build()' 메서드를 호출하여 최종적으로 S3 클라이언트 생성.
        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
        return (AmazonS3Client) AmazonS3ClientBuilder.standard()
                .withRegion(region)
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .build();
    }

    /*@Bean
    public String getThumbnailPath(String path) {
        return amazonS3Client().getUrl(bucketName, path).toString();
    }*/

}
