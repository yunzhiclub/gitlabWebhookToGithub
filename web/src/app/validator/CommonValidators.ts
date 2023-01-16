import {AbstractControl, AsyncValidatorFn, ValidationErrors} from "@angular/forms";
import {delay, Observable, of} from "rxjs";
import {HttpClient, HttpParams} from "@angular/common/http";
import {map, tap} from "rxjs/operators";
import {Injectable} from "@angular/core";
import { SettingService } from "../../service/SettingService";

@Injectable({
  providedIn: 'root'
})
export class CommonValidators {
  constructor(private httpClient: HttpClient,
              private settingService: SettingService
            ) {
  }

  /**
   * 验证方法，secret不存在验证通过
   */
  secretNotExist(): AsyncValidatorFn {
    return (control: AbstractControl): Observable<ValidationErrors | null> => {
      if (control.value === '') {
        return of(null);
      }
      return this.settingService.existBySecret(control.value).pipe(map(exists => {
        return exists ? {secretExist: true} : null;
      }));
    };
  };

}

