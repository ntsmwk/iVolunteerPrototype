import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { environment } from "environments/environment";

@Injectable({ providedIn: "root" })
export class FileService {
  constructor(private http: HttpClient) {}

  retrieveFile(filePath) {
    return this.http.get(filePath);
  }

  uploadFile(file) {
    const formData: FormData = new FormData();
    formData.append("file", file, file.name);
    return this.http.post(`/core/file`, formData);
  }

  deleteFile(imageId: string) {
    return this.http.delete(`/core/image/${imageId}`);
  }
}
