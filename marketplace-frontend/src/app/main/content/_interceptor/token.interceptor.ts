import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable} from 'rxjs';
import {isNullOrUndefined} from 'util';

@Injectable()
export class TokenInterceptor implements HttpInterceptor {

  constructor() {
  }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const authorization = localStorage.getItem('token');
    
    if (this.isLoginRequest(request) || isNullOrUndefined(authorization)) {
      return next.handle(request);
    }

    request = request.clone(
      {setHeaders: {'Authorization': authorization}}
    );
    return next.handle(request);
  }

  private isLoginRequest(request: HttpRequest<any>) {
    return request.url.endsWith('/login') && request.method === 'POST';
  }
}
