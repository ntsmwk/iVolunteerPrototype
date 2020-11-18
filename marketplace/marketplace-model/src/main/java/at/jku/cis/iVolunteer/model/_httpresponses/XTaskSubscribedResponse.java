package at.jku.cis.iVolunteer.model._httpresponses;

import at.jku.cis.iVolunteer.model.task.XTask;

public class XTaskSubscribedResponse extends XTask {
    private boolean subscribed;

    public XTaskSubscribedResponse() {
    }

    public boolean isSubscribed() {
        return subscribed;
    }

    public void setSubscribed(boolean subscribed) {
        this.subscribed = subscribed;
    }
}
