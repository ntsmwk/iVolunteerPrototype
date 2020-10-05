package at.jku.cis.iVolunteer.model.task;

public class RequiredTaskBlock {
	String title;
	String id;
	String name;
	TaskField startDate;
	TaskField endDate;
	String image;
	TaskField descripiton;
	boolean expired;
	TaskField place;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public TaskField getStartDate() {
		return startDate;
	}
	public void setStartDate(TaskField startDate) {
		this.startDate = startDate;
	}
	public TaskField getEndDate() {
		return endDate;
	}
	public void setEndDate(TaskField endDate) {
		this.endDate = endDate;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public TaskField getDescripiton() {
		return descripiton;
	}
	public void setDescripiton(TaskField descripiton) {
		this.descripiton = descripiton;
	}
	public boolean isExpired() {
		return expired;
	}
	public void setExpired(boolean expired) {
		this.expired = expired;
	}
	public TaskField getPlace() {
		return place;
	}
	public void setPlace(TaskField place) {
		this.place = place;
	}

}
