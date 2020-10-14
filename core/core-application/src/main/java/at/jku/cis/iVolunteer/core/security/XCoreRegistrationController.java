package at.jku.cis.iVolunteer.core.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer._mappers.xnet.XUserPasswordMapper;
import at.jku.cis.iVolunteer.model._httpresponses.RegisterResponse;
import at.jku.cis.iVolunteer.model._httpresponses.RegisterResponseMessage;
import at.jku.cis.iVolunteer.model.core.user.CoreUser;
import at.jku.cis.iVolunteer.model.registration.AccountType;
import at.jku.cis.iVolunteer.model.user.XUserPassword;

@RestController
@RequestMapping("/auth/signup")
public class XCoreRegistrationController {

    @Autowired
    private CoreRegistrationService coreRegistrationService;
    @Autowired
    private XUserPasswordMapper xUserPasswordMapper;

    @PostMapping
    public ResponseEntity<Object> registerUser(@RequestBody XUserPassword xUser) {
        CoreUser coreUser = xUserPasswordMapper.toSource(xUser);

        RegisterResponseMessage responseMessage = coreRegistrationService.registerUser(coreUser, AccountType.PERSON);

        if (responseMessage.getResponse() == RegisterResponse.OK) {
            return ResponseEntity.ok().build();

        } else {
            return new ResponseEntity<Object>(responseMessage, HttpStatus.BAD_REQUEST);
        }
    }

}
