package at.jku.cis.iVolunteer.marketplace.badge;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import at.jku.cis.iVolunteer.model.badge.XBadgeTemplate;

@Repository
public interface XBadgeTemplateRepository extends MongoRepository<XBadgeTemplate, String>{

}
