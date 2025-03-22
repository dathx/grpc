package com.grpc.user;

import io.grpc.Channel;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import java.util.logging.Level;
import com.user.UserRequest;
import com.user.UserResponse;
import com.user.UserServiceGrpc;

public class UserClient {           
    private static final Logger logger = Logger.getLogger(UserClient.class.getName());
    private final UserServiceGrpc.UserServiceBlockingStub blockingStub;

    public UserClient(Channel channel) {
        blockingStub = UserServiceGrpc.newBlockingStub(channel);
    }

    public void findUser(String userId, String userName, String phoneNumber, String email) {
        logger.info("Sending user request to server");
        UserRequest request = UserRequest.newBuilder()
                .setUserId(userId)
                .setUserName(userName)
                .setPhoneNumber(phoneNumber)
                .setEmail(email)
                .build();
        
        logger.info("Sending to server: " + request);
        UserResponse response;
        try {
            response = blockingStub.findUser(request);
        } catch (StatusRuntimeException e) {
            logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
            return;
        }
        logger.info("Got response from server: " + response.getResponse());
    }

    public static void main(String[] args) throws Exception {
        String serverAddress = "localhost:2106";
        ManagedChannel channel = ManagedChannelBuilder.forTarget(serverAddress)
                .usePlaintext()
                .build();
        try {
            UserClient client = new UserClient(channel);
            client.findUser("1", "John", "1234567890", "john@example.com");
        } finally {
            channel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
        }
    }

}
