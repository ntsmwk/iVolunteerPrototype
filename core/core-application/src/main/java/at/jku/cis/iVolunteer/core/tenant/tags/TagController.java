package at.jku.cis.iVolunteer.core.tenant.tags;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.model.core.tenant.Tag;

@RestController
public class TagController {

	@Autowired
	private TagRepository tagRepository;

	@GetMapping("/tags/all")
	private List<Tag> getAllTags() {
		return tagRepository.findAll();
	}
	
	@GetMapping("/tags/all/string")
	private List<String> getAllTagsAsStrings() {
		List<Tag> tags = getAllTags();
		return tags.stream().map(t -> t.getLabel()).collect(Collectors.toList());
	}
}