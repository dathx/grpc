package com.grpc.unary;

import com.greeting.ClientRequest;
import com.greeting.ServerResponse;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import com.greeting.GreeterGrpc;

public class GreetServer {
    private static final Logger logger = Logger.getLogger(GreetServer.class.getName());
    private Server server;
    private void start() throws IOException {
        int port = 50051;
        server = ServerBuilder.forPort(port).addService(new GreeterImpl()).build().start();

        logger.info("Server started, listening on " + port);

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                System.err.println("Shutting down gRPC server");
                try {
                    server.shutdown().awaitTermination(30, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace(System.err);
                }
            }
        });
    }
    static class GreeterImpl extends GreeterGrpc.GreeterImplBase {
        @Override
        public void greet(ClientRequest req, StreamObserver<ServerResponse> responseObserver) {
            logger.info("Got request from client: " + req);
            ServerResponse reply = ServerResponse.newBuilder().setMessage(
                    "Server says " + "\"" + req.getGreeting() + " " + req.getName() + "\""
            ).build();
            responseObserver.onNext(reply);
            responseObserver.onCompleted();
        }
    }
    public static void main(String[] args) throws IOException, InterruptedException {
        final GreetServer greetServer = new GreetServer();
        greetServer.start();
        greetServer.server.awaitTermination();
    }
}

