import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Group } from '../_model/group';

@Injectable({
  providedIn: 'root'
})
export class GroupService {

  constructor(private http: HttpClient) { }


  save(group: Group){
      return this.http.post('marketplace/group', group);
  }


  getGroups() {
    return this.http.get('marketplace/group');
  }


}
