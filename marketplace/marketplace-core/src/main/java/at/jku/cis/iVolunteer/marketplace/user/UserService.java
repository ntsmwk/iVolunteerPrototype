package at.jku.cis.iVolunteer.marketplace.user;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.model.user.User;
import at.jku.cis.iVolunteer.model.user.UserRole;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> getUsers(List<String> ids) {
        List<User> users = new ArrayList<User>();
        userRepository.findAll(ids).forEach(users::add);
        return users;
    }

    public User getUserByName(String name) {
        return userRepository.findByUsername(name);
    }

    public List<User> getUsersByRole(UserRole role) {
        List<User> allUsers = this.userRepository.findAll();

        List<User> users = new ArrayList<>();
        users.addAll(allUsers.stream().filter(u -> u.getSubscribedTenants().size() > 0)
                .filter(u -> u.getSubscribedTenants().stream().anyMatch(t -> t.getRole() == role))
                .collect(Collectors.toList()));

        if (role == UserRole.VOLUNTEER) {
            users.addAll(
                    allUsers.stream().filter(u -> u.getSubscribedTenants().size() == 0).collect(Collectors.toList()));
        }

        return users;
    }

    public int currentAge(User volunteer) {
        LocalDate birthDay = volunteer.getBirthday().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Period diff = Period.between(birthDay, LocalDate.now());
        return diff.getYears();
    }

    public String typeOfService(User volunteer, String tenantId) {
        // XXX to do --> function
        return "EA";
    }

    public String currentRank(User volunteer, String tenantId) {
        // XXX to do --> function
        return " ";
    }

}