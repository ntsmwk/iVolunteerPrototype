package at.jku.cis.iVolunteer.core.dashboard;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import at.jku.cis.iVolunteer.model.core.dashboard.Dashboard;
import at.jku.cis.iVolunteer.model.core.user.CoreUser;

@Repository
public interface DashboardRepository extends MongoRepository<Dashboard, String> {

	Dashboard findByUser(CoreUser user);
}