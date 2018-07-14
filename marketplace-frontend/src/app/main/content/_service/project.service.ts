import {Injectable} from '@angular/core';
import {Marketplace} from '../_model/marketplace';
import {Project} from '../_model/project';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ProjectService {

  private projects = new Array<Project>();

  constructor() {
    this.projects.push({id: '978465132', name: 'Weihnachtsbasar', description: 'I love CIS ;)', marketplaceId: undefined} as Project);

  }

  findCurrentProjects(marketplace: Marketplace, volunteerId: string) {
    return new Observable(subscriber => {
      subscriber.next(this.projects);
      subscriber.complete();
    });
  }

  findRecentVisistedProjects(marketplace: Marketplace, volunteerId: string) {
    return this.findCurrentProjects(marketplace, volunteerId);
  }
}
