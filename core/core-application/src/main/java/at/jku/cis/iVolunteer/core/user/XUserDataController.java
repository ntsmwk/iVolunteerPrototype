package at.jku.cis.iVolunteer.core.user;

import java.text.Collator;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer._mappers.xnet.XUserMapper;
import at.jku.cis.iVolunteer.core.security.CoreLoginService;
import at.jku.cis.iVolunteer.core.tenant.TenantService;
import at.jku.cis.iVolunteer.model._httpresponses.ErrorResponse;
import at.jku.cis.iVolunteer.model.core.tenant.Tenant;
import at.jku.cis.iVolunteer.model.core.user.CoreUser;
import at.jku.cis.iVolunteer.model.user.XUser;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@RestController
public class XUserDataController {
    @Autowired
    private CoreLoginService loginService;
    @Autowired
    private TenantService tenantService;
    @Autowired
    private XUserDataService userDataService;
    @Autowired
    private CoreUserService coreUserService;
    @Autowired
    private XUserMapper xUserMapper;

    @GetMapping("/userInfo")
    public ResponseEntity<Object> getUserInfo() {
        CoreUser user = loginService.getLoggedInUser();

        if (user == null) {
            return new ResponseEntity<Object>(new ErrorResponse("User does not exist"), HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok(xUserMapper.toTarget(user));
    }

    @PostMapping("/userInfo/update")
    public ResponseEntity<Object> upadteUserInfo(@RequestBody XUser xUser,
            @RequestHeader("Authorization") String authorization) {
        CoreUser existingUser = loginService.getLoggedInUser();
        CoreUser updatingUser = xUserMapper.toSource(xUser);

        if (existingUser == null) {
            return new ResponseEntity<Object>(new ErrorResponse("User does not exist"), HttpStatus.BAD_REQUEST);
        }

        existingUser = existingUser.updateCoreUser(updatingUser);
        coreUserService.updateUser(existingUser, authorization, true);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/userInfo/tenantRole")
    public ResponseEntity<Object> getTenantRole() {
        // CoreUser user = loginService.getLoggedInUser();
        CoreUser user = coreUserService.getByUserName("mweixlbaumer");

        if (user == null) {
            return new ResponseEntity<Object>(new ErrorResponse("User does not exist"), HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok(userDataService.toTenantRoles(user.getSubscribedTenants()));

    }

    @GetMapping("/userInfo/tenantSubscription")
    public ResponseEntity<Object> getTenantSubscription() {
        // CoreUser user = loginService.getLoggedInUser();
        CoreUser user = coreUserService.getByUserName("mweixlbaumer");

        if (user == null) {
            return new ResponseEntity<Object>(new ErrorResponse("User does not exist"), HttpStatus.BAD_REQUEST);
        }

        List<Tenant> tenants = new ArrayList<>();
        tenants = user.getSubscribedTenants().stream().map(s -> s.getTenantId())
                .map(id -> tenantService.getTenantById(id)).collect(Collectors.toList());

        return ResponseEntity.ok(tenants);
    }

    // @GetMapping("/userInfo/badges")
    // public ResponseEntity<Object> getBadges() {
    // CoreUser user = loginService.getLoggedInUser();

    // if (user == null) {
    // return new ResponseEntity<Object>(new ErrorResponse("User does not exist"),
    // HttpStatus.BAD_REQUEST);
    // }

    // // TODO return user's badges
    // return null;
    // }

    // @GetMapping("/userInfo/{userId}/badges")
    // public ResponseEntity<Object> getBadgesOfOtherUser(@PathVariable("userId")
    // String userId) {
    // CoreUser user = coreUserService.getByUserId(userId);

    // if (user == null) {
    // return new ResponseEntity<Object>(new ErrorResponse("User does not exist"),
    // HttpStatus.BAD_REQUEST);
    // }

    // // TODO return user's badges
    // return null;
    // }

}