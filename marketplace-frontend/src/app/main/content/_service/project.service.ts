import {Injectable} from '@angular/core';
import {Marketplace} from '../_model/marketplace';
import {Project} from '../_model/project';
import {Observable} from 'rxjs';
import {isUndefined} from 'util';

@Injectable({
  providedIn: 'root'
})
export class ProjectService {

  private projects = new Array<Project>();

  constructor() {
    const project = {
      id: '978465132',
      name: 'Weihnachtsbasar',
      description: 'I love CIS ;)',
      startDate: new Date().getTime(),
      endDate: new Date().getTime() + 600000000,
      marketplaceId: '0eaf3a6281df11e8adc0fa7ae01bbebc'
    } as Project;

    this.projects.push(project);
  }

  findById(marketplace: Marketplace, projectId: string) {
    return new Observable(subscriber => {
      subscriber.next(this.filterProjectsById(projectId));
      subscriber.complete();
    });
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

  private filterProjectsById(projectId: string): Project {
    const filteredProjects = this.projects.filter((current: Project) => current.id === projectId);
    return isUndefined(filteredProjects) ? null : filteredProjects[0];
  }


}
