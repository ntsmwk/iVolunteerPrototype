import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import { UserImagePath } from '../_model/participant';

@Injectable({
  providedIn: 'root'
})
export class CoreUserImagePathService {

  constructor(private http: HttpClient) {
  }

  getAllImagePaths() {
    return this.http.get(`/user/image/all`);
  }

  getImagePathById(userId: string) {
    return this.http.get(`/user/image/${userId}`);
  }

  getImagePathsById(userIds: string[]) {
    return this.http.put(`/user/image/get-multiple`, userIds);
  }

  saveImagePath(userImagePath: UserImagePath) {
    return this.http.post(`user/image/save`, userImagePath);
  }

  deleteImagePath(userId: string) {
    return this.http.delete(`/user/image/${userId}`);
  }




}
