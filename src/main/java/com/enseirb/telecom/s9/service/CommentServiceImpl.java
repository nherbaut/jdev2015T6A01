package com.enseirb.telecom.s9.service;

import com.enseirb.telecom.s9.Comment;
import com.enseirb.telecom.s9.db.*;

public abstract class CommentServiceImpl implements CommentService {
	
	CrudRepository <CommentRepositoryObject, String> commentDatabase;
	
	public CommentServiceImpl(CrudRepository <CommentRepositoryObject, String> commentDatabase){
		this.commentDatabase = commentDatabase;
	}
	
	public boolean commentExist(String email){
		return true;
	}
	
	public Comment getComment (String email){
		
		Comment comment = new Comment();
		
		return comment;
	}
	
	public Comment createComment(Comment comment){
		
		Comment comment1 = new Comment();
		
		return comment1;
		
	}
	
	public void saveComment(Comment comment){
		
	}
	
	public void deleteComment(String email){		
		
	}

}
