
import {tap} from 'rxjs/operators';
import {Injectable} from '@angular/core';
import {HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Router} from '@angular/router';
import {Observable} from 'rxjs';


@Injectable()
export class Http401Interceptor implements HttpInterceptor {

  constructor(private router: Router) {
  }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(request).pipe(tap((event: HttpEvent<any>) => {}, (error: any) => {
        if (error instanceof HttpErrorResponse) {
          if (error.status === 401) {
            localStorage.removeItem('token');
            this.router.navigate(['login']);
          }
        }
      }));
  }
}
