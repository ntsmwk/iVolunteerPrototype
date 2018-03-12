export class AssignmentVolunteer {
  id: string
  username: string;
  isAssigned: boolean;

  constructor(id: string, username: string, isAssigned: boolean) {
    this.id = id;
    this.username = username;
    this.isAssigned = isAssigned;
  }
}
