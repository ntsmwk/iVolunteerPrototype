package at.jku.cis.iVolunteer.model.diagram.xnet;

public class XDiagramDisplay {
    private ValueType valueType;
    private DiagramType diagramType;

    public XDiagramDisplay() {
    }

    public XDiagramDisplay(ValueType valueType, DiagramType diagramType) {
        this.valueType = valueType;
        this.diagramType = diagramType;
    }

    public ValueType getValueType() {
        return this.valueType;
    }

    public void setValueType(ValueType valueType) {
        this.valueType = valueType;
    }

    public void setValueType(String valueType) {
        this.valueType = ValueType.valueOf(valueType);
    }

    public DiagramType getDiagramType() {
        return this.diagramType;
    }

    public void setDiagramType(DiagramType diagramType) {
        this.diagramType = diagramType;
    }

    public void setDiagramType(String diagramType) {
        this.diagramType = DiagramType.valueOf(diagramType);
    }

    public enum ValueType {
        DURATION("DURATION"), COUNT("COUNT");

        private String valueType;

        private ValueType(String valueType) {
            this.valueType = valueType;
        }

        public void setValueType(String valueType) {
            this.valueType = valueType;
        }

        public String getValueType() {
            return this.valueType;
        }

    }

    public enum DiagramType {
        DOMAIN_CATEGORY("DOMAIN_CATEGORY"), CATEGORY_ONLY("CATEGORY_ONLY");

        private String diagramType;

        private DiagramType(String diagramType) {
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
