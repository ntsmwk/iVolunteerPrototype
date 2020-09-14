import { Injectable } from "@angular/core";
import {
  HttpEvent,
  HttpHandler,
  HttpInterceptor,
  HttpRequest,
  HttpErrorResponse,
} from "@angular/common/http";
import { Observable, throwError, BehaviorSubject } from "rxjs";
import { isNullOrUndefined } from "util";
import { LoginService } from "../_service/login.service";
import { catchError, switchMap, filter, take } from "rxjs/operators";

@Injectable()
export class TokenInterceptor implements HttpInterceptor {
  constructor(private loginService: LoginService) {}

  private isRefreshing = false;
  private refreshTokenSubject: BehaviorSubject<any> = new BehaviorSubject<any>(
    null
  );

  intercept(
    request: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    const accessToken = this.loginService.getAccessToken();

    if (this.isLoginRequest(request) || isNullOrUndefined(accessToken)) {
      return next.handle(request);
    }

    request = this.addToken(request, accessToken);

    return next.handle(request).pipe(
      catchError((error) => {
        if (error instanceof HttpErrorResponse && error.status === 401) {
          return this.handle401Error(request, next);
        } else {
          return throwError(error);
        }
      })
    );
  }

  private isLoginRequest(request: HttpRequest<any>) {
    return request.url.endsWith("/login") && request.method === "POST";
  }

  private addToken(request: HttpRequest<any>, accessToken: string) {
    return request.clone({ setHeaders: { Authorization: accessToken } });
  }

  private handle401Error(request: HttpRequest<any>, next: HttpHandler) {
    if (!this.isRefreshing) {
      this.isRefreshing = true;
      this.refreshTokenSubject.next(null);

      return this.loginService
        .refreshAccessToken(this.loginService.getRefreshToken())
        .pipe(
          switchMap((response: any) => {
            this.isRefreshing = false;
            this.refreshTokenSubject.next(response.accessToken);
            return next.handle(this.addToken(request, response.accessToken));
          })
        );
    } else {
      return this.refreshTokenSubject.pipe(
        filter((accessToken) => accessToken != null),
        take(1),
        switchMap((accessToken) => {
          return next.handle(this.addToken(request, accessToken));
        })
      );
    }
  }
}
