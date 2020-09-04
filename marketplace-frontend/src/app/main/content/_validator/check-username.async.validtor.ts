import { of, Observable } from 'rxjs';
import { Directive, Injectable } from '@angular/core';
import { NG_ASYNC_VALIDATORS, AsyncValidator, ValidationErrors, AbstractControl, AsyncValidatorFn, ValidatorFn } from '@angular/forms';
import { LoginService } from '../_service/login.service';
import { UserService } from '../_service/user.service';
import { CoreUserService } from '../_service/core-user.serivce';
import { map, catchError } from 'rxjs/operators';
import { isNullOrUndefined } from 'util';

// @Directive({
//     selector: '[usernameAsyncValidator]',
//     providers: [{
//         provide: NG_ASYNC_VALIDATORS, useExisting: UsernameAsyncValidatorDirective, multi:
//             true
//     }]
// })
// export class UsernameAsyncValidatorDirective implements AsyncValidator {
//     validate(control: AbstractControl): Observable<ValidationErrors | null> {
//         return of({ 'custom': true });
//     }


// }

// export function usernameAsyncValidator(userService: CoreUserService): AsyncValidatorFn {
//     return (control: AbstractControl): Observable<ValidationErrors | null> => {
//         userService.findByUsername(control.value);
//         return null;
//     };
// }

// @Injectable({
//     providedIn: "root",
//   })
//   export class AsyncService {
//     constructor(private http: HttpClient) {}

//     usernameValidator(): ValidatorFn {
//       return (
//         control: AbstractControl
//       ): Observable<{ [key: string]: any } | null> => {
//         const isUnique$ = this.http.get("/api/auth/checkUsername", {
//           params: { username: control.value },
//         }); //Fake request for now
//         return isUnique$.pipe(
//           map((isUnique) => (isUnique ? { unique: true } : null))
//         );
//       };
//     }
//   }


@Injectable({ providedIn: 'root' })
export class UsernameAsyncValidator {
    constructor(private userService: CoreUserService) { }

    usernameValidator(): ValidatorFn {
        return (control: AbstractControl): Observable<{ [key: string]: any } | null> => {
            this.userService.findByUsername(control.value).pipe(
                map(value => {
                    console.log('Succeed');
                    console.log(value);
                    return true;
                }),
                catchError(err => {
                    console.log('HTTP ERROR: ', err);
                    return null;
                })
            );
            return null;
            // .pipe(
            //     map(ret => (!isNullOrUndefined(ret) ? { usernameTaken: true } : null)
            //     )
            //     // ,catchError(() => of(null))
            // );

        };


        // validate(control: AbstractControl ): Promise<ValidationErrors | null> | Observable<ValidationErrors | null> {
        //     return this.userService.findByUsername(control.value).pipe(
        //         map(ret => (!isNullOrUndefined(ret) ? { uniqueAlterEgo: true } : null)),
        //         catchError(() => of(null))
        //     );
        // }
    }
}

// export const usernameAsyncValidator =
//     (userService: CoreUserService, time: number = 500) => {
//         return (control: FormControl) => {
//             return timer(time).pipe(
//                 switchMap(() => userService.findByUsername(control.value).toPromise()),
//                 map(res => {
//                     console.log(res);
//                     return res ? null : { loginExist: true };
//                 })
//             );
//         };
//     };