import { Injectable } from "@angular/core";
import { CanActivate } from "@angular/router";

@Injectable({ providedIn: "root" })
export class AnonymGuard implements CanActivate {
  constructor() {}

  canActivate() {
    return true;
  }
}
