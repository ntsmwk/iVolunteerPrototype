package at.jku.cis.iVolunteer._mappers.xnet;

import java.util.List;

import org.springframework.stereotype.Component;

import at.jku.cis.iVolunteer.model._mapper.AbstractMapper;
import at.jku.cis.iVolunteer.model.user.UserRole;
import at.jku.cis.iVolunteer.model.user.XUserRoleRelevance;

@Component
public class XUserRoleRelevanceMapper implements AbstractMapper<UserRole, XUserRoleRelevance> {

    @Override
    public XUserRoleRelevance toTarget(UserRole source) {
        if (source == null) {
            return null;
        }

        XUserRoleRelevance role = new XUserRoleRelevance();

        switch (UserRole.getUserRole(source.getUserRole())) {
            case VOLUNTEER:
                role.setName("Volunteer");
                role.setNameShort("V");
                role.setRelevance(1);
                break;
            case HELP_SEEKER:
                role.setName("Help Seeker");
                role.setNameShort("HS");
                role.setRelevance(2);
                break;
            case TENANT_ADMIN:
                role.setName("Tenant Admin");
                role.setNameShort("TA");
                role.setRelevance(3);
                break;
            case RECRUITER:
                role.setName("Recruiter");
                role.setNameShort("R");
                role.setRelevance(4);
                break;
            case ADMIN:
                role.setName("Admin");
                role.setNameShort("A");
                role.setRelevance(5);
                break;
            case FLEXPROD:
                role.setName("Flexprod");
                role.setNameShort("F");
                role.setRelevance(6);
                break;
            case NONE:
                role.setName("None");
                role.setNameShort("N");
                role.setRelevance(10);
                break;

            default:
                return null;
        }

        return role;
    }

    @Override
    public List<XUserRoleRelevance> toTargets(List<UserRole> sources) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public UserRole toSource(XUserRoleRelevance target) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<UserRole> toSources(List<XUserRoleRelevance> targets) {
        // TODO Auto-generated method stub
        return null;
    }

}
