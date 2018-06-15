import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable()
export class TokenInterceptor implements HttpInterceptor {

  constructor() {
  }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    if (request.url.endsWith('/login') && request.method === 'POST') {
      return next.handle(request);
    }

    request = request.clone(
      {
        setHeaders:
          {'Authorization': localStorage.getItem('token')}
      }
    );
    return next.handle(request);
  }
}
