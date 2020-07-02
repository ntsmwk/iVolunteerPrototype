package at.jku.cis.iVolunteer.core.user;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.model.core.user.CoreUser;
import at.jku.cis.iVolunteer.model.user.UserRole;

@Service
public class CoreUserService {

    @Autowired
    private CoreUserRepository coreUserRepository;

    public List<CoreUser> getCoreUsersByRole(UserRole role) {
        List<CoreUser> allUsers = this.coreUserRepository.findAll();

        List<CoreUser> users = new ArrayList<>();
        users.addAll(allUsers.stream().filter(u -> u.getSubscribedTenants().size() > 0)
                .filter(u -> u.getSubscribedTenants().stream().allMatch(t -> t.getRole() == role))
                .collect(Collectors.toList()));

        if (role == UserRole.VOLUNTEER) {
            users.addAll(
                    allUsers.stream().filter(u -> u.getSubscribedTenants().size() == 0).collect(Collectors.toList()));
        }

        return users;
    }

    public List<CoreUser> getCoreUsersByRoleAndId(UserRole role, List<String> userIds) {
        // TODO Philipp test
        List<CoreUser> users = this.coreUserRepository.findAll();

        return users.stream().filter(u -> u.getSubscribedTenants().stream().allMatch(t -> t.getRole() == role))
                .filter(u -> userIds.contains(u.getId())).collect(Collectors.toList());
    }

}