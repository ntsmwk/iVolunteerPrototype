package at.jku.cis.iVolunteer._mappers.xnet;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import at.jku.cis.iVolunteer.model._mapper.AbstractMapper;
import at.jku.cis.iVolunteer.model.user.UserRole;
import at.jku.cis.iVolunteer.model.user.XUserRole;
import at.jku.cis.iVolunteer.model.user.XUserRoleRelevance;

@Component
public class XUserRoleMapper implements AbstractMapper<UserRole, XUserRole> {
    final String VOLUNTEER_NAME = "Volunteer";
    final String VOLUNTEER_NAME_SHORT = "V";
    final int VOLUNTEER_RELEVANCE = 1;

    final String HELPSEEKER_NAME = "Help Seeker";
    final String HELPSEEKER_NAME_SHORT = "HS";
    final int HELPSEEKER_RELEVANCE = 2;

    final String TENANTADMIN_NAME = "Tenant Admin";
    final String TENANTADMIN_NAME_SHORT = "TA";
    final int TENANTADMIN_RELEVANCE = 3;

    final String RECRUITER_NAME = "Recruiter";
    final String RECRUITER_NAME_SHORT = "R";
    final int RECRUITER_RELEVANCE = 4;

    final String ADMIN_NAME = "Admin";
    final String ADMIN_NAME_SHORT = "A";
    final int ADMIN_RELEVANCE = 5;

    final String FLEXPROD_NAME = "Flexprod";
    final String FLEXPROD_NAME_SHORT = "F";
    final int FLEXPROD_RELEVANCE = 6;

    final String NONE_NAME = "None";
    final String NONE_NAME_SHORT = "N";
    final int NONE_RELEVANCE = 10;

    @Override
    public XUserRole toTarget(UserRole source) {
        XUserRoleRelevance xUserRoleRelevance = this.toTargetRelevance(source);
        return new XUserRole(xUserRoleRelevance.getName(), xUserRoleRelevance.getNameShort());
    }

    @Override
    public List<XUserRole> toTargets(List<UserRole> sources) {
        if (sources == null) {
            return null;
        }

        List<XUserRoleRelevance> targetsRelevance = new LinkedList<>();
        for (UserRole source : sources) {
            targetsRelevance.add(toTargetRelevance(source));
        }

        // sort by relevance
        targetsRelevance.stream().sorted(Comparator.comparing(XUserRoleRelevance::getRelevance))
                .collect(Collectors.toList());

        List<XUserRole> targets = new LinkedList<>();
        targetsRelevance.stream().map(r -> new XUserRole(r.getName(), r.getNameShort())).forEach(targets::add);

        return targets;
    }

    private XUserRoleRelevance toTargetRelevance(UserRole source) {
        if (source == null) {
            return null;
        }

        XUserRoleRelevance roleRelevance = new XUserRoleRelevance();

        switch (UserRole.getUserRole(source.getUserRole())) {
            case VOLUNTEER:
                roleRelevance.setName(VOLUNTEER_NAME);
                roleRelevance.setNameShort(VOLUNTEER_NAME_SHORT);
                roleRelevance.setRelevance(VOLUNTEER_RELEVANCE);
                break;
            case HELP_SEEKER:
                roleRelevance.setName(HELPSEEKER_NAME);
                roleRelevance.setNameShort(HELPSEEKER_NAME_SHORT);
                roleRelevance.setRelevance(HELPSEEKER_RELEVANCE);
                break;
            case TENANT_ADMIN:
                roleRelevance.setName(TENANTADMIN_NAME);
                roleRelevance.setNameShort(TENANTADMIN_NAME_SHORT);
                roleRelevance.setRelevance(TENANTADMIN_RELEVANCE);
                break;
            case RECRUITER:
                roleRelevance.setName(RECRUITER_NAME);
                roleRelevance.setNameShort(RECRUITER_NAME_SHORT);
                roleRelevance.setRelevance(RECRUITER_RELEVANCE);
                break;
            case ADMIN:
                roleRelevance.setName(ADMIN_NAME);
                roleRelevance.setNameShort(ADMIN_NAME_SHORT);
                roleRelevance.setRelevance(ADMIN_RELEVANCE);
                break;
            case FLEXPROD:
                roleRelevance.setName(FLEXPROD_NAME);
                roleRelevance.setNameShort(FLEXPROD_NAME_SHORT);
                roleRelevance.setRelevance(FLEXPROD_RELEVANCE);
                break;
            case NONE:
                roleRelevance.setName(NONE_NAME);
                roleRelevance.setNameShort(NONE_NAME_SHORT);
                roleRelevance.setRelevance(NONE_RELEVANCE);
                break;

            default:
                return null;
        }

        return roleRelevance;

    }

    @Override
    public UserRole toSource(XUserRole target) {
        if (target == null) {
            return null;
        }

        UserRole role;

        switch (target.getName()) {
            case VOLUNTEER_NAME:
                role = UserRole.VOLUNTEER;
                break;
            case HELPSEEKER_NAME:
                role = UserRole.HELP_SEEKER;
                break;
            case TENANTADMIN_NAME:
                role = UserRole.TENANT_ADMIN;
                break;
            case RECRUITER_NAME:
                role = UserRole.RECRUITER;
                break;
            case ADMIN_NAME:
                role = UserRole.ADMIN;
                break;
            case FLEXPROD_NAME:
                role = UserRole.FLEXPROD;
                break;
            case NONE_NAME:
                role = UserRole.NONE;
                break;
            default:
                return null;
        }

        return role;
    }

    @Override
    public List<UserRole> toSources(List<XUserRole> targets) {
        if (targets == null) {
            return null;
        }

        List<UserRole> sources = new LinkedList<>();
        for (XUserRole target : targets) {
            sources.add(toSource(target));
        }
        return sources;
    }

}
