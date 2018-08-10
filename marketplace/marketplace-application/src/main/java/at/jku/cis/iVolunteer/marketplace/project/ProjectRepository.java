package at.jku.cis.iVolunteer.marketplace.project;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import at.jku.cis.iVolunteer.model.project.Project;

@Repository
public interface ProjectRepository extends MongoRepository<Project, String> {


}
