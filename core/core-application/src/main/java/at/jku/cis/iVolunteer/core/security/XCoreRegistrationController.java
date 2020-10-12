package at.jku.cis.iVolunteer.core.security;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer._mappers.xnet.XUserPasswordMapper;
import at.jku.cis.iVolunteer.core.security.model.TokenResponse;
import at.jku.cis.iVolunteer.core.service.JWTTokenProvider;
import at.jku.cis.iVolunteer.model._httpresponses.ErrorResponse;
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
    private JWTTokenProvider tokenProvider = new JWTTokenProvider();

    @PostMapping
    public ResponseEntity<Object> registerUser(@RequestBody XUserPassword xUser) {
        CoreUser coreUser = xUserPasswordMapper.toSource(xUser);

        User user = new User(coreUser.getUsername(), coreUser.getPassword(), Collections.emptyList());
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null,
                user.getAuthorities());

        RegisterResponseMessage responseMessage = coreRegistrationService.registerUser(coreUser, AccountType.PERSON);

        if (responseMessage.getResponse() == RegisterResponse.OK) {
            try {
                String accessToken = this.tokenProvider.generateAccessToken(authentication);
                String refreshToken = this.tokenProvider.generateRefreshToken(authentication);
                return ResponseEntity.ok(new TokenResponse(accessToken, refreshToken).toString());

            } catch (Exception ex) {
                return new ResponseEntity<Object>(new ErrorResponse(ex.getMessage()), HttpStatus.BAD_REQUEST);
            }

        } else {
            return new ResponseEntity<Object>(responseMessage, HttpStatus.BAD_REQUEST);
        }
    }

}
