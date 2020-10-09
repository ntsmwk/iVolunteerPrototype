package at.jku.cis.iVolunteer.core.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.core.security.CoreLoginService;
import at.jku.cis.iVolunteer.model._httpresponses.ErrorResponse;
import at.jku.cis.iVolunteer.model.core.user.CoreUser;
import at.jku.cis.iVolunteer.model.user.XUserInfo;
import at.jku.cis.iVolunteer.model.user.XUserInfoMapper;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@RestController
public class XUserDataController {
    @Autowired
    private CoreLoginService loginService;
    @Autowired
    private XUserDataService userDataService;
    @Autowired
    private CoreUserService coreUserService;
    @Autowired
    private XUserInfoMapper userInfoMapper;

    @GetMapping("/userInfo")
    public ResponseEntity<Object> getUserInfo() {
        CoreUser user = loginService.getLoggedInUser();

        if (user == null) {
            return new ResponseEntity<Object>(new ErrorResponse("User does not exist"), HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok((new XUserInfo(user)));
    }

    @PutMapping("/userInfo/update")
    public ResponseEntity<Object> upadteUserInfo(@RequestBody XUserInfo userInfo,
            @RequestHeader("Authorization") String authorization) {
        CoreUser existingUser = loginService.getLoggedInUser();
        CoreUser updatingUser = userInfoMapper.mapToUser(userInfo);

        if (existingUser == null) {
            return new ResponseEntity<Object>(new ErrorResponse("User does not exist"), HttpStatus.BAD_REQUEST);
        }

        existingUser = CoreUser.updateCoreUser(existingUser, updatingUser);
        coreUserService.updateUser(existingUser, authorization, true);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/user/tenantRole")
    public ResponseEntity<Object> getTenantRole() {
        CoreUser user = loginService.getLoggedInUser();

        if (user == null) {
            return new ResponseEntity<Object>(new ErrorResponse("User does not exist"), HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok(userDataService.toTenantRoles(user.getSubscribedTenants()));

    }

    @GetMapping("/user/tenantSubscription")
    public ResponseEntity<Object> getTenantSubscription() {
        CoreUser user = loginService.getLoggedInUser();

        if (user == null) {
            return new ResponseEntity<Object>(new ErrorResponse("User does not exist"), HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok((user.getSubscribedTenants()));
    }

    @GetMapping("/user/badges")
    public ResponseEntity<Object> getBadges() {
        CoreUser user = loginService.getLoggedInUser();

        if (user == null) {
            return new ResponseEntity<Object>(new ErrorResponse("User does not exist"), HttpStatus.BAD_REQUEST);
        }

        // TODO return user's badges
        return null;
    }

    @GetMapping("/user/{userId}/badges")
    public ResponseEntity<Object> getBadgesOfOtherUser(@PathVariable("userId") String userId) {
        CoreUser user = coreUserService.getByUserId(userId);

        if (user == null) {
            return new ResponseEntity<Object>(new ErrorResponse("User does not exist"), HttpStatus.BAD_REQUEST);
        }

        // TODO return user's badges
        return null;
    }

}