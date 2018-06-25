package at.jku.cis.iVolunteer.core.task;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.core.marketplace.MarketplaceRepository;
import at.jku.cis.iVolunteer.core.rest.client.MarketplaceRestClient;
import at.jku.cis.iVolunteer.model.exception.BadRequestException;
import at.jku.cis.iVolunteer.model.marketplace.Marketplace;
import at.jku.cis.iVolunteer.model.task.TaskStatus;
import at.jku.cis.iVolunteer.model.task.dto.TaskDTO;

@Service
public class CoreTaskService {

	@Autowired
	private MarketplaceRepository marketplaceRepository;

	@Autowired
	private MarketplaceRestClient marketplaceRestClient;

	public List<TaskDTO> findAllByMarketplace(String marketplaceId, TaskStatus status, String authorization) {
		Marketplace marketplace = marketplaceRepository.findOne(marketplaceId);
		if (marketplace == null) {
			throw new BadRequestException();
		}
		String url = marketplace.getUrl();
		return marketplaceRestClient.getTasks(url, authorization);
	}

	public TaskDTO findByTaskId(String taskId, String marketplaceId, TaskStatus status, String authorization) {
		Marketplace marketplace = marketplaceRepository.findOne(marketplaceId);
		if (marketplace == null) {
			throw new BadRequestException();
		}
		return marketplaceRestClient.getTaskById(taskId, marketplace.getUrl(), authorization);
	}

	public List<TaskDTO> findByVolunteer(String volunteerId, TaskStatus status, String authorization) {
		//TODO
		return null;
	}

	public TaskDTO createTask(String marketplaceId, TaskDTO taskDto, String authorization) {
		Marketplace marketplace = marketplaceRepository.findOne(marketplaceId);
		if (marketplace == null) {
			throw new BadRequestException();
		}
		return marketplaceRestClient.createTask(marketplace.getUrl(), authorization, taskDto);
	}

}