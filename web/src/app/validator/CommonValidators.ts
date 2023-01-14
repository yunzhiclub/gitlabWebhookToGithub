import {AbstractControl, AsyncValidatorFn, ValidationErrors} from "@angular/forms";
import {delay, Observable, of} from "rxjs";
import {HttpClient, HttpParams} from "@angular/common/http";
import {map, tap} from "rxjs/operators";
import {Injectable} from "@angular/core";

@Injectable({
  providedIn: 'root'
})
export class CommonValidators {
  constructor(private httpClient: HttpClient,
            ) {
  }

  /**
   * 验证方法，用户名不存在验证通过
   * @param control FormControl
   */
  usernameNotExist(): AsyncValidatorFn {
    return (control: AbstractControl): Observable<ValidationErrors | null> => {
      if (control.value === '') {
        return of(null);
      }
      return this.userService.existByUsername(control.value).pipe(map(exists => {
        return exists ? {usernameExist: true} : null;
      }));
    };
  };

}

