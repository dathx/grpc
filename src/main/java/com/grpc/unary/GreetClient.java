package com.grpc.unary;

import io.grpc.Channel;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.greeting.GreeterGrpc;
import com.greeting.ServerResponse;
import com.greeting.ClientRequest;

public class GreetClient {
    private static final Logger logger = Logger.getLogger(GreetClient.class.getName());
    private final GreeterGrpc.GreeterBlockingStub blockingStub;

    public GreetClient(Channel channel) {
        blockingStub = GreeterGrpc.newBlockingStub(channel);
    }

    public void makeGreeting(String greeting, String username) {
        logger.info("Sending greeting to server: " + greeting + " for name: " + username);
        ClientRequest request = ClientRequest.newBuilder().setName(username).setGreeting(greeting).build();
        logger.info("Sending to server: " + request);
        ServerResponse response;
        try {
            response = blockingStub.greet(request);
        } catch (StatusRuntimeException e) {
            logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
            return;
        }
        logger.info("Got following from the server: " + response.getMessage());
    }

    public static void main(String[] args) throws Exception {
        String greeting = "Chao";
        String username = "Dat";
        String serverAddress = "localhost:50051";
        ManagedChannel channel = ManagedChannelBuilder.forTarget(serverAddress)
                .usePlaintext()
                .build();
        try {
            GreetClient client = new GreetClient(channel);
            client.makeGreeting(greeting, username);
        } finally {
            channel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
        }
    }
}