package at.jku.cis.iVolunteer.core.image;


import org.springframework.data.mongodb.repository.MongoRepository;

import at.jku.cis.iVolunteer.model.image.Image;

public interface ImageRepository extends MongoRepository<Image, String> {

}
