package at.jku.cis.iVolunteer.model.diagram.xnet.data.badge;

public class XDiagramDisplayBadge {
    private DiagramTypeBadge diagramType;

    public XDiagramDisplayBadge() {
    }

    public XDiagramDisplayBadge(DiagramTypeBadge diagramType) {
        this.diagramType = diagramType;
    }

    public DiagramTypeBadge getDiagramType() {
        return this.diagramType;
    }

    public void setDiagramType(DiagramTypeBadge diagramType) {
        this.diagramType = diagramType;
    }

    public void setDiagramType(String diagramType) {
        this.diagramType = DiagramTypeBadge.valueOf(diagramType);
    }

    public enum DiagramTypeBadge {
        TIMELINE("TIMELINE"), SCHMUCKKAESTCHEN("SCHMUCKKAESTCHEN");

        private String diagramType;

        private DiagramTypeBadge(String diagramType) {
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
