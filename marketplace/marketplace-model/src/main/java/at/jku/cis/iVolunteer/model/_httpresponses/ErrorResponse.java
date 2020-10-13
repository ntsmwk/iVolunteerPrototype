package at.jku.cis.iVolunteer.model._httpresponses;

public class ErrorResponse {
    public ErrorResponse(String error) {
        this.error = error;
    }

    private String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
