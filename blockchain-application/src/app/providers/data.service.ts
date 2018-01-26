import {Injectable} from '@angular/core';
import {Response} from '@angular/http';
import {Observable} from 'rxjs/Observable';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import {Configuration} from './configuration';
import {HttpClient, HttpHeaders} from '@angular/common/http';

@Injectable()
export class DataService<Type> {
  private resolveSuffix = '?resolve=true';
  private actionUrl: string;
  private headers: HttpHeaders;

  constructor(private http: HttpClient, private _configuration: Configuration) {
    this.actionUrl = _configuration.ServerWithApiUrl;
    this.headers = new HttpHeaders();
    this.headers.append('Accept', 'application/json');
    this.headers.append('Content-Type', 'application/json');
  }

  public getAll(ns: string): Observable<Type[]> {
    console.log('GetAll ' + ns + ' to ' + this.actionUrl + ns);

    return this.http.get(`${this.actionUrl}${ns}`, {'headers': this.headers})
      .map(this.extractData)
      .catch(this.handleError);
  }

  public getSingle(ns: string, id: string): Observable<Type> {
    console.log('GetSingle ' + ns);

    return this.http.get(this.actionUrl + ns + '/' + id + this.resolveSuffix)
      .map(this.extractData)
      .catch(this.handleError);
  }

  public add(ns: string, asset: Type): Observable<Type> {
    console.log('Entered DataService add');
    console.log('Add ' + ns);
    console.log('asset', asset);

    return this.http.post(this.actionUrl + ns, asset)
      .map(this.extractData)
      .catch(this.handleError);
  }

  public update(ns: string, id: string, itemToUpdate: Type): Observable<Type> {
    console.log('Update ' + ns);
    console.log('what is the id?', id);
    console.log('what is the updated item?', itemToUpdate);
    console.log('what is the updated item?', JSON.stringify(itemToUpdate));
    return this.http.put(`${this.actionUrl}${ns}/${id}`, itemToUpdate)
      .map(this.extractData)
      .catch(this.handleError);
  }

  public delete(ns: string, id: string): Observable<Type> {
    console.log('Delete ' + ns);

    return this.http.delete(this.actionUrl + ns + '/' + id)
      .map(this.extractData)
      .catch(this.handleError);
  }

  private handleError(error: any): Observable<string> {
    // In a real world app, we might use a remote logging infrastructure
    // We'd also dig deeper into the error to get a better message
    const errMsg = (error.message) ? error.message : error.status ? `${error.status} - ${error.statusText}` : 'Server error';
    console.error(errMsg); // log to console instead
    return Observable.throw(errMsg);
  }

  private extractData(result: Type): any {
    return result;
  }

}
