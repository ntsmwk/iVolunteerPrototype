import { HttpClient } from '@angular/common/http';
import { Marketplace } from '../_model/marketplace';
import { StoredChart } from '../_model/stored-chart';
import { isNullOrUndefined } from 'util';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class StoredChartService {

  constructor(private http: HttpClient) {
  }

  findById(marketplace: Marketplace, id: string) {
    return this.http.get(`${marketplace.url}/chart/id/${id}`);
  }

  findByTitle(marketplace: Marketplace, title: string) {
    return this.http.get(`${marketplace.url}/chart/title/${title}`);
  }

  findAll(marketplace: Marketplace) {
    return this.http.get(`${marketplace.url}/chart`);
  }

  save(marketplace: Marketplace, storedChart: StoredChart) {
    return this.http.post(`${marketplace.url}/chart`, storedChart);
  }
}
