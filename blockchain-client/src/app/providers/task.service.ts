import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {DataService} from './data.service';
import {Task} from 'app/model/at.jku.cis';

@Injectable()
export class TaskService {

  private NAMESPACE = 'Task';

  constructor(private dataService: DataService<Task>) {
  };

  public getAll(): Observable<Task[]> {
    return this.dataService.getAll(this.NAMESPACE);
  }

  public getAsset(id: any): Observable<Task> {
    return this.dataService.getSingle(this.NAMESPACE, id);
  }

  public getAllCreated(): Observable<Task[]> {
    return this.dataService.getAll('queries/findCreatedTasks');
  }

  public getAllByOrganisation(organisationId: any): Observable<Task[]> {
    return this.dataService.getAll('queries/findTasksByOrganisation?organisation=resource:at.jku.cis.Organisation#' + organisationId);
  }

  public getAllReservedByVolunteer(volunteerId: any): Observable<Task[]> {
    return this.dataService.getAll('queries/findReservedTasksByVolunteer?volunteer=resource:at.jku.cis.Volunteer#' + volunteerId);
  }

  public getAllAssignedByVolunteer(volunteerId: any): Observable<Task[]> {
    return this.dataService.getAll('queries/findAssignedTasksByVolunteer?volunteer=resource:at.jku.cis.Volunteer#' + volunteerId);
  }

  public getAllFinishedByVolunteer(volunteerId: any): Observable<Task[]> {
    return this.dataService.getAll('queries/findFinishedTasksByVolunteer?volunteer=resource:at.jku.cis.Volunteer#' + volunteerId);
  }
}
