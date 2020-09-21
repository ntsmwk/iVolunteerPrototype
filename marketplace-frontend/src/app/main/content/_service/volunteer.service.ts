import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Marketplace } from "../_model/marketplace";
import { UserService } from './user.service';

@Injectable({
  providedIn: "root",
})
export class VolunteerService {
  constructor(private http: HttpClient,
    private userService: UserService) { }

  findAll(marketplace: Marketplace) {
    // return this.http.get(`${marketplace.url}/volunteer/`);
    return this.userService.findAll(marketplace);
  }

  findById(marketplace: Marketplace, id: string) {
    // return this.http.get(`${marketplace.url}/volunteer/${id}`);
    return this.userService.findById(marketplace, id);
  }

  findByName(marketplace: Marketplace, name: string) {
    // return this.http.get(`${marketplace.url}/volunteer/username/${name}`);
    return this.findByName(marketplace, name);
  }

}
