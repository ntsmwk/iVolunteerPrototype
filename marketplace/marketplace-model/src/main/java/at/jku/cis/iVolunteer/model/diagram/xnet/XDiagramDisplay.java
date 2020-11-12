package at.jku.cis.iVolunteer.model.diagram.xnet;

public class XDiagramDisplay {
    private ValueType valueType;
    private DiagramType diagramType;

    public XDiagramDisplay() {
    }

    public ValueType getValueType() {
        return this.valueType;
    }

    public void setValueType(ValueType valueType) {
        this.valueType = valueType;
    }

    public DiagramType getDiagramType() {
        return this.diagramType;
    }

    public void setDiagramType(DiagramType diagramType) {
        this.diagramType = diagramType;
    }

    private enum ValueType {
        DURATION("duration"), COUNT("count");

        private final String valueType;

        private ValueType(String valueType) {
            this.valueType = valueType;
        }

        @Override
        public String toString() {
            return valueType;
        }

    }

    private enum DiagramType {
        DOMAIN_CATEGORY("DOMAIN_CATEGORY"), CATEGORY_ONLY("CATEGORY_ONLY");

        private final String diagramType;

        private DiagramType(String diagramType) {
            this.diagramType = diagramType;
        }

        @Override
        public String toString() {
            return diagramType;
        }

    }

}
