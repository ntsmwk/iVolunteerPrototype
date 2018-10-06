import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';

import {isNullOrUndefined} from 'util';

import {Project} from '../_model/project';
import {Marketplace} from '../_model/marketplace';

@Injectable({
  providedIn: 'root'
})
export class ProjectService {

  constructor(private http: HttpClient) {
  }

  findAll(marketplace: Marketplace) {
    return this.http.get(`${marketplace.url}/project`);
  }

  findAvailable(marketplace: Marketplace) {
    return this.http.get(`${marketplace.url}/project?state=AVAILABLE`);
  }

  findEngaged(marketplace: Marketplace) {
    return this.http.get(`${marketplace.url}/project?state=ENGAGED`);
  }

  findFinished(marketplace: Marketplace) {
    return this.http.get(`${marketplace.url}/project?state=FINISHED`);
  }

  findById(marketplace: Marketplace, projectId: string) {
    return this.http.get(`${marketplace.url}/project/${projectId}`);
  }

  save(marketplace: Marketplace, project: Project) {
    if (isNullOrUndefined(project.id)) {
      return this.http.post(`${marketplace.url}/project`, project);
    }
    return this.http.put(`${marketplace.url}/project/${project.id}`, project);
  }


}
