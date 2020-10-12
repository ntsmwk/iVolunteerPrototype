package at.jku.cis.iVolunteer.marketplace._mapper.xnet;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import at.jku.cis.iVolunteer.model._mapper.OneWayMapper;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassInstance;
import at.jku.cis.iVolunteer.model.task.XTaskSerialized;

@Component
public class XClassInstanceToTaskSerializedMapper implements OneWayMapper<ClassInstance, XTaskSerialized> {

	@Override
	public XTaskSerialized toTarget(ClassInstance source) {
		if (source == null) {
			return null;
		}

		// (null / false) === TODO

		XTaskSerialized ts = new XTaskSerialized();
		ts.setId(source.getId());
		ts.setTitle(source.getName());
		ts.setDescription(source.getDescription());
		ts.setStartDate(null);
		ts.setEndDate(null);
		ts.setImagePath(source.getImagePath());
		ts.setClosed(false);
		ts.setGeoInfo(null);

		ts.setDynamicFields(null);

		return ts;
	}

	@Override
	public List<XTaskSerialized> toTargets(List<ClassInstance> sources) {
		if (sources == null) {
			return null;
		}

		List<XTaskSerialized> targets = new ArrayList<>();
		for (ClassInstance source : sources) {
			targets.add(toTarget(source));
		}

		return targets;

	}

}
