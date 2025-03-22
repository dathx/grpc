package com.grpc.user;

import com.user.UserRequest;
import com.user.UserResponse;
import com.user.UserServiceGrpc;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class UserServer {
	Server server;

	private void start() throws IOException {
		int port = 2106;
		System.out.println("Starting server on port: " + port);
		server = ServerBuilder.forPort(port).addService(new UserServiceImpl()).build().start();
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				System.err.println("Shutting down gRPC server since JVM is shutting down");
				try{
					server.shutdown().awaitTermination(10, TimeUnit.SECONDS);
				} catch (InterruptedException e) {
					e.printStackTrace(System.err);
				}
				System.err.println("Server shut down");
			}
		});
	}

	private class UserServiceImpl extends UserServiceGrpc.UserServiceImplBase {
		@Override
		public void findUser(UserRequest req, StreamObserver<UserResponse> responseObserver) {
			System.out.println("Got request from client: " + req);
			UserResponse response = UserResponse.newBuilder().setResponse("Response".concat(req.getUserName()).concat(" Email: ").concat(req.getEmail())).build();
			responseObserver.onNext(response);
			responseObserver.onCompleted();
		}
	}

	public static void main(String[] args) {
		UserServer userServer = new UserServer();
		try {
			userServer.start();
			userServer.server.awaitTermination();
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}

}