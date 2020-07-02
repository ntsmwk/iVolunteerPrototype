package at.jku.cis.iVolunteer.marketplace.user;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.model.meta.core.clazz.competence.CompetenceClassDefinition;
import at.jku.cis.iVolunteer.model.user.User;

import javax.ws.rs.BadRequestException;

@RestController
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/volunteer")
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @GetMapping("/volunteer/{id}")
    public User findById(@PathVariable("id") String id) {
        return userRepository.findOne(id);
    }

    @GetMapping("/volunteer/username/{username}")
    public User findByUsername(@PathVariable("username") String username) {
        return userRepository.findByUsername(username);
    }

    @GetMapping("/volunteer/{id}/competencies")
    public List<CompetenceClassDefinition> findCompetencies(@PathVariable("id") String id) {

        // TODO implement ...
        return Collections.emptyList();
    }

    @PostMapping("/volunteer")
    public User registerVolunteer(@RequestBody User volunteer) {
        if (userRepository.findOne(volunteer.getId()) == null) {
            return userRepository.insert(volunteer);
        }
        return null;
    }

    @PostMapping("/helpseeker")
    public User registerHelpSeeker(@RequestBody User helpSeeker) {
        if (userRepository.findOne(helpSeeker.getId()) != null) {
            throw new BadRequestException("HelpSeeker already registed");
        }
        return userRepository.insert(helpSeeker);
    }

    @GetMapping("/recruiter/{id}")
    public User findById2(@PathVariable("id") String id) {
        return userRepository.findOne(id);
    }

    @PostMapping("/recruiter")
    public User registerRecruiter(@RequestBody User recruiter) {
        if (userRepository.findOne(recruiter.getId()) != null) {
            throw new BadRequestException("Recruiter already registed");
        }
        return userRepository.insert(recruiter);
    }

}