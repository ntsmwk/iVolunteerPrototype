package at.jku.cis.iVolunteer.marketplace.task;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import at.jku.cis.iVolunteer.model.project.Project;
import at.jku.cis.iVolunteer.model.task.Task;
import at.jku.cis.iVolunteer.model.task.TaskStatus;

@Repository
public interface TaskRepository extends MongoRepository<Task, String> {

	List<Task> findByStatus(TaskStatus status);
	List<Task> findByProject(Project project);
	List<Task> findByProjectAndStatus(Project project, TaskStatus status);
}
