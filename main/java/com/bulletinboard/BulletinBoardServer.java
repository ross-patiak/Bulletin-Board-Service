package com.bulletinboard;

import java.io.IOException;

import io.grpc.Server;
import io.grpc.ServerBuilder;

public class BulletinBoardServer {

	public static void main(String[] args) throws IOException, InterruptedException {
	
	
	Server server = ServerBuilder.forPort(12345).addService(new BulletinBoardService()).build();
	server.start();
	System.out.println("Server started. Listening at port " + server.getPort());
	
	
	
	server.awaitTermination();
	
	if(server.isTerminated()) System.out.println("Server terminated.");
		
	}

}
