package at.jku.cis.iVolunteer.mapper.comment;

import org.mapstruct.Mapper;

import at.jku.cis.iVolunteer.mapper.AbstractMapper;
import at.jku.cis.iVolunteer.model.comment.Comment;
import at.jku.cis.iVolunteer.model.comment.dto.CommentDTO;

@Mapper
public abstract class CommentMapper  implements AbstractMapper<Comment, CommentDTO> {

}
