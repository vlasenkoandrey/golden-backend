package ru.vlasenko;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sns.AmazonSNSAsync;
import com.amazonaws.services.sns.AmazonSNSAsyncClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by andrew on 08/12/16.
 */
@Configuration
public class AwsConfiguration {

    @Value("${aws.sns.endpoint}")
    private String snsEndpoint;

    @Value("${aws.sns.key}")
    private String snsAccessKey;

    @Value("${aws.sns.secret}")
    private String snsAccessSecret;

    @Bean
    public AmazonSNSAsync snsClient() {
        AmazonSNSAsync amazonSNS = new AmazonSNSAsyncClient(new BasicAWSCredentials(snsAccessKey, snsAccessSecret));
        amazonSNS.setEndpoint(snsEndpoint);
        return amazonSNS;
    }
}
