import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {Configuration} from './configuration';
import {HttpClient} from '@angular/common/http';

@Injectable()
export class SystemPingService {

  private actionUrl: string;

  constructor(private http: HttpClient, _configuration: Configuration) {
    this.actionUrl = _configuration.ServerWithApiUrl + 'system/ping';
  };

  public getSystemPing(): Observable<SystemPing> {
    return this.http.get(this.actionUrl) as Observable<SystemPing>;
  }
}

export class SystemPing {
  participant?: string;
  version: string;
}
