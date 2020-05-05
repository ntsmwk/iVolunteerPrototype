import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ObjectIdService {

  constructor() {
  }

  public getNewObjectId() {
    return this.toHex(Date.now() / 1000) +
      ' '.repeat(16).replace(/./g, () => this.toHex(Math.random() * 16));
  }

  private toHex(value: number) {
    return Math.floor(value).toString(16);
  }
}
