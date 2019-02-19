package at.jku.cis.iVolunteer.marketplace.comment;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.mapper.comment.CommentMapper;
import at.jku.cis.iVolunteer.model.comment.Comment;
import at.jku.cis.iVolunteer.model.comment.dto.CommentDTO;

@RestController
public class CommentController {
	
	@Autowired
	private CommentMapper commentMapper;
	
	@Autowired
	private CommentRepository commentRepository;
	
	
	@GetMapping("/comment")
	public List<CommentDTO> findAll(){
		
		return commentMapper.toDTOs(commentRepository.findAll());
	}
	
	@PostMapping("/comment")
	public CommentDTO createComment(CommentDTO commentDto) {
		System.out.println("backend call");
		Comment comment = commentMapper.toEntity(commentDto);
		return commentMapper.toDTO(commentRepository.insert(comment));
	}
	
	
	
}
