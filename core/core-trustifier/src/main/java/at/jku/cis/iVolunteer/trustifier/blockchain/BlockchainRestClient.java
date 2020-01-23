
package at.jku.cis.iVolunteer.trustifier.blockchain;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@Service
public class BlockchainRestClient {

	@Value("${spring.data.blockchain.uri}") 
	private String url;

	@Autowired private RestTemplate restTemplate;

	private static Logger logger = LoggerFactory.getLogger(BlockchainRestClient.class);

	public BcPublishedTask getPublishedTaskHash(String hash) {
		String requestUrl = MessageFormat.format("{0}/api/publishedTask/{1}", url, hash);
		BcPublishedTask c = null;
		try {
			c = restTemplate.getForObject(requestUrl, BcPublishedTask.class);
		} catch (Exception e) {
			if (e instanceof HttpStatusCodeException) {
				logger.error(((HttpStatusCodeException) e).getResponseBodyAsString());
			} else {
				logger.error(e.getMessage());

			}
		}
		return c;
	}

	public BcTaskInteraction getTaskInteractionHash(String hash) {
		String requestUrl = MessageFormat.format("{0}/api/taskInteraction/{1}", url, hash);
		BcTaskInteraction c = null;
		try {
			c = restTemplate.getForObject(requestUrl, BcTaskInteraction.class);
		} catch (Exception e) {
			if (e instanceof HttpStatusCodeException) {
				logger.error(((HttpStatusCodeException) e).getResponseBodyAsString());
			} else {
				logger.error(e.getMessage());

			}
		}
		return c;
	}

	public BcFinishedTask getFinishedTaskHash(String hash) {
		String requestUrl = MessageFormat.format("{0}/api/finishedTask/{1}", url, hash);
		BcFinishedTask c = null;
		try {
			c = restTemplate.getForObject(requestUrl, BcFinishedTask.class);
		} catch (Exception e) {
			if (e instanceof HttpStatusCodeException) {
				logger.error(((HttpStatusCodeException) e).getResponseBodyAsString());
			} else {
				logger.error(e.getMessage());

			}
		}
		return c;

	}

	public BcCompetence getCompetenceHash(String hash) {
		String requestUrl = MessageFormat.format("{0}/api/competence/{1}", url, hash);
		BcCompetence c = null;
		try {
			c = restTemplate.getForObject(requestUrl, BcCompetence.class);
		} catch (Exception e) {
			if (e instanceof HttpStatusCodeException) {
				logger.error(((HttpStatusCodeException) e).getResponseBodyAsString());
			} else {
				logger.error(e.getMessage());

			}
		}
		return c;

	}

	public void postPublishedTaskHash(String hash, Date timestamp, String taskId, String marketplaceId) {
		String requestUrl = MessageFormat.format("{0}/api/publishedTask", url);
		BcPublishedTask t = new BcPublishedTask(hash, timestamp, taskId, marketplaceId);

		try {
			restTemplate.postForObject(requestUrl, t, Void.class);

		} catch (Exception e) {
			if (e instanceof HttpStatusCodeException) {
				logger.error(((HttpStatusCodeException) e).getResponseBodyAsString());
			} else {
				logger.error(e.getMessage());

			}
		}

	}

	public void postTaskInteractionHash(String hash, Date timestamp, String taskId, String marketplaceId,
			String taskInteractionType) {
		String requestUrl = MessageFormat.format("{0}/api/taskInteraction", url);
		BcTaskInteraction ti = new BcTaskInteraction(hash, timestamp, taskId, marketplaceId, taskInteractionType);

		try {
			restTemplate.postForObject(requestUrl, ti, Void.class);
		} catch (Exception e) {
			if (e instanceof HttpStatusCodeException) {
				logger.error(((HttpStatusCodeException) e).getResponseBodyAsString());
			} else {
				logger.error(e.getMessage());

			}
		}
	}

	public void postFinishedTaskHash(String hash, Date timestamp, String taskId, String marketplaceId, String volunteerId) {
		String requestUrl = MessageFormat.format("{0}/api/finishedTask", url);
		BcFinishedTask ft = new BcFinishedTask(hash, timestamp, taskId, marketplaceId, volunteerId);

		try {
			restTemplate.postForObject(requestUrl, ft, Void.class);
		} catch (Exception e) {
			if (e instanceof HttpStatusCodeException) {
				logger.error(((HttpStatusCodeException) e).getResponseBodyAsString());
			} else {
				logger.error(e.getMessage());

			}
		}

	}

	public void postCompetenceHash(String hash, Date timestamp, String competenceDefinitionId, String marketplaceId, String volunteerId) {
		String requestUrl = MessageFormat.format("{0}/api/competence", url);
		BcCompetence c = new BcCompetence(hash, timestamp, competenceDefinitionId, marketplaceId, volunteerId);

		try {
			restTemplate.postForObject(requestUrl, c, Void.class);
		} catch (Exception e) {
			if (e instanceof HttpStatusCodeException) {
				logger.error(((HttpStatusCodeException) e).getResponseBodyAsString());
			} else {
				logger.error(e.getMessage());

			}
		}

	}

	public void postClassInstance(String hash, String marketplaceId, String userId) {
		String requestUrl = MessageFormat.format("{0}/api/verificationObject", url);
		BcClassInstance c = new BcClassInstance(hash, userId);
		try {
			restTemplate.postForObject(requestUrl, c, Void.class);
		} catch (Exception e) {
			if (e instanceof HttpStatusCodeException) {
				logger.error(((HttpStatusCodeException) e).getResponseBodyAsString());
			} else {
				logger.error(e.getMessage());

			}
		}	
	}
	
	public void postClassInstanceArray(List<BcClassInstance> classInstances) {
		String requestUrl = MessageFormat.format("{0}/api/StoreVerificationObjects", url);
		try {
			
			BcClassInstances bcClassInstances = new BcClassInstances(classInstances.toArray(new BcClassInstance[0]));
			restTemplate.postForObject(requestUrl, bcClassInstances, Void.class);
		} catch (Exception e) {
			if (e instanceof HttpStatusCodeException) {
				logger.error(((HttpStatusCodeException) e).getResponseBodyAsString());
			} else {
				logger.error(e.getMessage());

			}
		}	
		
	}
}
