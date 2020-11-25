package at.jku.cis.iVolunteer.model.diagram.xnet.data.badge;

public class XDiagramOrderBadge {
    private OrderTypeBadge orderType;
    private boolean orderAsc;

    public XDiagramOrderBadge() {
    }

    public OrderTypeBadge getOrderType() {
        return this.orderType;
    }

    public void setOrderType(OrderTypeBadge orderType) {
        this.orderType = orderType;
    }

    public boolean isOrderAsc() {
        return this.orderAsc;
    }

    public boolean getOrderAsc() {
        return this.orderAsc;
    }

    public void setOrderAsc(boolean orderAsc) {
        this.orderAsc = orderAsc;
    }

    public enum OrderTypeBadge {
        ORDER_CREATIONDATE("ORDER_CREATIONDATE"), ORDER_TENANT_ID("ORDER_TENANT_ID"),
        ORDER_TENANT_NAME("ORDER_TENANT_NAME"), ORDER_BADGE_NAME("ORDER_BADGE_NAME");

        private String orderType;

        private OrderTypeBadge(String orderType) {
            this.orderType = orderType;
        }

        public void setOrderType(String orderType) {
            this.orderType = orderType;
        }

        public String getOrderType() {
            return this.orderType;
        }
    }
}
