import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';

import {isNullOrUndefined} from 'util';

import {Marketplace} from '../_model/marketplace';

@Injectable({
  providedIn: 'root'
})
export class FakeService {

  constructor(private http: HttpClient) {
  }

  fahrtenspangeFake(marketplace: Marketplace){
    return this.http.post(`${marketplace.url}/fahrtenspange-fake`, {});
  }
}