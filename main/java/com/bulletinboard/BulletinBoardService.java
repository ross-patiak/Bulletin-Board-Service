package com.bulletinboard;

public class BulletinBoardService extends BoardTasksGrpc.BoardTasksImplBase {
	
PostList.Builder masterList = PostList.newBuilder();
	
	
	public void postMessage(com.bulletinboard.Post request,
	        io.grpc.stub.StreamObserver<com.bulletinboard.PostList> responseObserver) {
	     
		
			responseObserver.onNext(masterList.addPosts(request).build());
			System.out.println("Posting new post with title: " + '"'+ request.getTitle()+ '"' +  " and body: " + '"' + request.getBody() + '"' + "...");
			responseObserver.onCompleted();
	    }

	    /**
	     */
	 	public void getPost(com.bulletinboard.Post request,
		        io.grpc.stub.StreamObserver<com.bulletinboard.Post> responseObserver) {
	 		boolean found = false;
		 		for(int i = 0; i < masterList.getPostsCount(); i++) {
		 			if(masterList.getPosts(i).getTitle().equals(request.getTitle())) {
					responseObserver.onNext(masterList.getPosts(i));
					System.out.println("Getting post: " + '"' + request.getTitle() + '"' + "...");
					found = true;
					
					break;
		 			} 
		 		}
		 	
			 	if(found == false) {
			 		System.out.println("Post does not exist.");
			 		responseObserver.onNext(request);
			 	}
			responseObserver.onCompleted();
		    }
	 	
		    /**
		     */
	 	public void listPosts(com.bulletinboard.Void request,
        io.grpc.stub.StreamObserver<com.bulletinboard.PostList> responseObserver) {
	 		System.out.println("Listing posts...");
	 		responseObserver.onNext(masterList.build());
			responseObserver.onCompleted();
	 		
	 	}

		    /**
		     */
		    public void deletePost(com.bulletinboard.Post request,
		        io.grpc.stub.StreamObserver<com.bulletinboard.PostList> responseObserver) {
		    	
		    	PostList.Builder newList = PostList.newBuilder();
		    	Post current = Post.newBuilder().setBody("Post not found.").build();
		    	
		    	boolean found = false;
		    	boolean deleted = false;
		 		for(int i = 0; i < masterList.getPostsCount(); i++) {
		 			current = masterList.getPosts(i);
		 			
		 			if(current.getTitle().equals(request.getTitle())) {
		 				found = true;
		 				deleted = true;
		 				System.out.println("Deleting post: " + '"' + request.getTitle() + '"' + "...");
		 			} 
		 			
		 			if(found == false) {
		 				newList.addPosts(current);
		 			}
		 			
		 			found = false;
		 		}//endoffor
		 		
		 		if(!deleted) {
		 			System.out.println(request.getTitle() + " does not exist.");
		 		}
		 		
		 		
		 		
		 		
		 		masterList = newList;
		 	
			 	
			responseObserver.onNext(masterList.build());
			responseObserver.onCompleted();
		    	
		    }
	
}
