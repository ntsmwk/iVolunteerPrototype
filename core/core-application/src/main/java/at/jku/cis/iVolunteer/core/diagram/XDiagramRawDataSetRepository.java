package at.jku.cis.iVolunteer.core.diagram;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import at.jku.cis.iVolunteer.model.diagram.xnet.data.raw.XDiagramRawDataSet;

public interface XDiagramRawDataSetRepository extends MongoRepository<XDiagramRawDataSet, String> {

    XDiagramRawDataSet findById(String id);

    List<XDiagramRawDataSet> findByUserId(String userId);

}
