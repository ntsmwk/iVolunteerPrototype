import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
// import { UserImagePath } from "../_model/user";

// TODO Philipp: wird nicht mehr verwendet, da images jetzt direkt beim tenant, oder user gespeichert sind
// componenten, die diesen service verwenden, darauf umbauen

@Injectable({
  providedIn: "root",
})
export class CoreUserImagePathService {
  constructor(private http: HttpClient) {}

  getAllImagePaths() {
    return this.http.get(`/user/image/all`);
  }

  getImagePathById(userId: string) {
    return this.http.get(`/core/user/image/${userId}`);
  }

  getImagePathsById(userIds: string[]) {
    return this.http.put(`/core/user/image/get-multiple`, userIds);
  }

  // saveImagePath(userImagePath: UserImagePath) {
  //   return this.http.post(`/core/user/image/save`, userImagePath);
  // }

  deleteImagePath(userId: string) {
    return this.http.delete(`/core/user/image/${userId}`);
  }
}
