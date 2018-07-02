package at.jku.cis.iVolunteer;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestTemplate;

import at.jku.cis.iVolunteer.marketplace.competence.CompetenceRepository;
import at.jku.cis.iVolunteer.marketplace.task.TaskRepository;
import at.jku.cis.iVolunteer.model.competence.Competence;
import at.jku.cis.iVolunteer.model.task.Task;
import at.jku.cis.iVolunteer.model.task.TaskStatus;

@ComponentScan("at.jku.cis.iVolunteer")
@SpringBootApplication
public class MarketplaceApplication implements CommandLineRunner {

	@Autowired
	private CompetenceRepository competenceRepository;
	@Autowired
	private TaskRepository taskRepository;

	@Bean
	@Primary
	public RestTemplate produceRestTemplate() {
		return new RestTemplate();
	}

	public static void main(String[] args) {
		SpringApplication.run(MarketplaceApplication.class, args);
	}

	@Override
	public void run(String... arg0) throws Exception {
		createCompetence("Planning");
		createCompetence("Leadership");
		createCompetence("Creativity");
		createCompetence("Flexability");
		createCompetence("Motivation");

	}

	private Competence createCompetence(String competenceName) {
		Competence competence = competenceRepository.findByName(competenceName);
		if (competence == null) {
			competence = new Competence();
			competence.setName(competenceName);
			competence = competenceRepository.insert(competence);
		}
		return competence;
	}

	private void createDemoTasks() {
		Task t1 = new Task();
		t1.setName("A");
		t1.setDescription("A");
		t1.setParent(null);
		t1.setStartDate(new Date());
		t1.setStatus(TaskStatus.CREATED);
		t1.setMarketplaceId("mId");
		taskRepository.insert(t1);

		Task t2 = new Task();
		t2.setName("B");
		t2.setDescription("B");
		t2.setParent(t1);
		t2.setStartDate(new Date());
		t2.setStatus(TaskStatus.CREATED);
		t1.setMarketplaceId("mId");
		taskRepository.insert(t2);

		Task t3 = new Task();
		t3.setName("C");
		t3.setDescription("C");
		t3.setParent(t1);
		t3.setStartDate(new Date());
		t3.setStatus(TaskStatus.CREATED);
		t1.setMarketplaceId("mId");
		taskRepository.insert(t3);

		Task t4 = new Task();
		t4.setName("D");
		t4.setDescription("D");
		t4.setParent(t2);
		t4.setStartDate(new Date());
		t4.setStatus(TaskStatus.CREATED);
		t1.setMarketplaceId("mId");
		taskRepository.insert(t4);

		Task t5 = new Task();
		t5.setName("E");
		t5.setDescription("E");
		t5.setParent(t2);
		t5.setStartDate(new Date());
		t5.setStatus(TaskStatus.CREATED);
		t1.setMarketplaceId("mId");
		taskRepository.insert(t5);
	}
}
