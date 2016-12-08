package ru.vlasenko.service;

import com.amazonaws.services.sns.AmazonSNSAsync;
import com.amazonaws.services.sns.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.vlasenko.model.Device;
import ru.vlasenko.model.User;
import ru.vlasenko.repo.UserRepo;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by andrew on 08/12/16.
 */
@Service
public class PushNotificationService {

    @Autowired
    private AmazonSNSAsync amazonSNS;

    @Value("${aws.sns.android}")
    private String androidEndpointArn;

    @Autowired
    private UserRepo userRepo;

    public void push(User user, String message) {
        if (user.getDevice() == null) {
            return;
        }
        Device dev = user.getDevice();
        if (dev.getArn() == null) {
            dev.setArn(registerWithSNS(dev.getToken()));
            userRepo.save(user);
        }
        PublishRequest publishRequest = new PublishRequest();
        publishRequest.setTargetArn(dev.getArn());
        publishRequest.setMessage(message);
        try {
            amazonSNS.publish(publishRequest);
        } catch (Exception e) {
            //
        }

    }

    private String registerWithSNS(String token) {

        boolean updateNeeded = false;

        String arnEndpoint = createArnEndpoint(token);

        // Look up the endpoint and make sure the data in it is current, even if
        // it was just created
        try {
            GetEndpointAttributesRequest request =
                    new GetEndpointAttributesRequest()
                            .withEndpointArn(arnEndpoint);
            GetEndpointAttributesResult result =
                    amazonSNS.getEndpointAttributes(request);

            updateNeeded = !result.getAttributes().get("Token").equals(token)
                    || !result.getAttributes().get("Enabled").equalsIgnoreCase("true");

        } catch (NotFoundException nfe) {
            // we had a stored ARN, but the endpoint associated with it
            // disappeared. Recreate it.
            arnEndpoint = createArnEndpoint(token);
        }


        if (updateNeeded) {
            // endpoint is out of sync with the current data;
            // update the token and enable it.
            Map<String, String> attribs = new HashMap<>();
            attribs.put("Token", token);
            attribs.put("Enabled", "true");
            SetEndpointAttributesRequest saeReq =
                    new SetEndpointAttributesRequest()
                            .withEndpointArn(arnEndpoint).withAttributes(attribs);
            amazonSNS.setEndpointAttributes(saeReq);
        }

        return arnEndpoint;
    }

    /**
     * @return never null
     * */
    private String createArnEndpoint(String token) {
        String arnEndpoint;
        try {
            CreatePlatformEndpointRequest request = new CreatePlatformEndpointRequest()
                    .withPlatformApplicationArn(androidEndpointArn)
                    .withToken(token);
            CreatePlatformEndpointResult result = amazonSNS
                    .createPlatformEndpoint(request);

            arnEndpoint = result.getEndpointArn();
        } catch (InvalidParameterException ipe) {
            String message = ipe.getErrorMessage();
            Pattern p = Pattern
                    .compile(".*Endpoint (arn:aws:sns[^ ]+) already exists " +
                            "with the same Token.*");
            Matcher m = p.matcher(message);
            if (m.matches()) {
                // the endpoint already exists for this token, but with
                // additional custom data that
                // CreateEndpoint doesn't want to overwrite. Just use the
                // existing endpoint.
                arnEndpoint = m.group(1);
            } else {
                // rethrow exception, the input is actually bad
                throw ipe;
            }
        }
        return arnEndpoint;
    }

}
