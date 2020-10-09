import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { environment } from "environments/environment";

@Injectable({ providedIn: "root" })
export class FileService {
  constructor(private http: HttpClient) {}

  getFullFilePath(fileName: string) {
    return environment.CORE_URL + "/file/" + fileName;
  }

  retrieveFile(fileName) {
    return this.http.get(`/core/file/${fileName}`);
  }

  uploadFile(file) {
    return this.http.post(`/core/file`, file);
  }

  deleteFile(imageId: string) {
    return this.http.delete(`/core/image/${imageId}`);
  }
}
