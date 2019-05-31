package com.bulletinboard;
import java.io.*;
import java.net.*;

import com.bulletinboard.BoardTasksGrpc.BoardTasksBlockingStub;
import com.bulletinboard.BoardTasksGrpc.BoardTasksStub;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class BulletinBoardClient {

	public static void main(String[] args) throws InterruptedException, IOException {
		
		
		ManagedChannel channel = ManagedChannelBuilder.forTarget("localhost:12345").usePlaintext(true).build();
		BoardTasksGrpc.BoardTasksBlockingStub stub = BoardTasksGrpc.newBlockingStub(channel);
		
		System.out.println("Welcome to this Bulletin Board!");
		System.out.println("-------------------------------------");
		System.out.println("Commands: \n" + "\t1.post -\"title\" -\"body\"\n"
		+ "\t2.get -\"title\"\n" + "\t3.list\n" + "\t4.delete -\"title\"\n" + "\t5.help");
		System.out.println("--------------------------------------");
		boolean loop = true;
		
		while(loop) {
			
			
			BufferedReader command = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Input a command:");
			String task = command.readLine();
			System.out.println();
						
			
			String[] commandArry = task.split("\"");
			char ch = 'a';
			
			try {
				
				ch = commandArry[0].trim().charAt(0);
			} catch (Exception e) {
				System.out.println("Incorrect format.");
			}
			
			switch(ch) {
			
				case 'p': {
					
					if(commandArry.length != 4) {
						System.out.println("Command formatted incorrectly");
						break;
					}
					
					String title = commandArry[1]; 
					String body = commandArry[3];
				    
				    Post newPost = Post.newBuilder().setTitle(title).setBody(body).build(); //creates post object
				    
				    stub.postMessage(newPost);
				    System.out.println("Confirmation: " + "\"" + title + "\"" + " is posted!");
				    System.out.println("--------------------------------------");
				  
				    
				    
				    break;
				}//case1
				
				case 'g': {
					if(commandArry.length != 2) {
						System.out.println("Command formatted incorrectly");
						break;
					}
					
					String title = commandArry[1];
				   
				    Post daBigPost = Post.newBuilder().setTitle(title).setBody("Post not found.").build();
				    
				    Post response = stub.getPost(daBigPost);
				  
				    
				   System.out.println(response.getBody());
				   System.out.println("--------------------------------------");
				    
				    
				    break;
				}//case 2
			
				
				case 'l': {
					if(commandArry.length != 1) {
						System.out.println("Command formatted incorrectly");
						break;
					}
					
					PostList bigList = stub.listPosts(Void.newBuilder().build());
					System.out.println("Current Posts:");
					
					for(int i = 0; i < bigList.getPostsCount(); i++) {
						System.out.println((i+1) + ". " + bigList.getPosts(i).getTitle());
					}
					
					System.out.println("--------------------------------------");
					break;
				}
				
				case 'd': {
					if(commandArry.length != 2) {
						System.out.println("Command formatted incorrectly");
						break;
					}
					
					String title = commandArry[1];
				
				    
					Post deletedPost = Post.newBuilder().setTitle(title).build();
					
					PostList startList = stub.listPosts(Void.newBuilder().build());
					
					PostList endList = stub.deletePost(deletedPost);
					
					if(startList.equals(endList)) {
						System.out.println("Post does not exist.");
					} else {
						System.out.println("Confirmation: " + "\"" + title + "\"" + " deleted!");
					}
					
					System.out.println("--------------------------------------");
					
					break;
				}
				
				case 'h': {
					System.out.println("Commands: \n" + "\t1.post -\"title\" -\"body\"\n"
							+ "\t2.get -\"title\"\n" + "\t3.list\n" + "\t4.delete -\"title\"\n" + "\t5.help");
					System.out.println("--------------------------------------");
					
					break;
				}
				
				default: {
					System.out.println("Invalid command. Try again.");
					System.out.println("--------------------------------------");
				}
			}//endofswitch
			
//			if(commandArry[0].equalsIgnoreCase("end")) {
//				loop = false;
//			}
		}//endofwhile
	    
	  
	    
			
			
	}

}
