package at.jku.cis.iVolunteer.model.diagram.xnet.data.task;

public class XDiagramDisplayTask {
    private ValueTypeTask valueType;
    private DiagramTypeTask diagramType;

    public XDiagramDisplayTask() {
    }

    public XDiagramDisplayTask(ValueTypeTask valueType, DiagramTypeTask diagramType) {
        this.valueType = valueType;
        this.diagramType = diagramType;
    }

    public ValueTypeTask getValueType() {
        return this.valueType;
    }

    public void setValueType(ValueTypeTask valueType) {
        this.valueType = valueType;
    }

    public void setValueType(String valueType) {
        this.valueType = ValueTypeTask.valueOf(valueType);
    }

    public DiagramTypeTask getDiagramType() {
        return this.diagramType;
    }

    public void setDiagramType(DiagramTypeTask diagramType) {
        this.diagramType = diagramType;
    }

    public void setDiagramType(String diagramType) {
        this.diagramType = DiagramTypeTask.valueOf(diagramType);
    }

    public enum ValueTypeTask {
        DURATION("DURATION"), COUNT("COUNT");

        private String valueType;

        private ValueTypeTask(String valueType) {
            this.valueType = valueType;
        }

        public void setValueType(String valueType) {
            this.valueType = valueType;
        }

        public String getValueType() {
            return this.valueType;
        }

    }

    public enum DiagramTypeTask {
        DOMAIN_CATEGORY("DOMAIN_CATEGORY"), CATEGORY_ONLY("CATEGORY_ONLY");

        private String diagramType;

        private DiagramTypeTask(String diagramType) {
            this.diagramType = diagramType;
        }

        public void setDiagramType(String diagramType) {
            this.diagramType = diagramType;
        }

        public String getDiagramType() {
            return this.diagramType;
        }

    }

}
