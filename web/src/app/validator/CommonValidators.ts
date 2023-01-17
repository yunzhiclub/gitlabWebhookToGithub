import { AbstractControl, AsyncValidatorFn, ValidationErrors } from "@angular/forms";
import { delay, Observable, of } from "rxjs";
import { HttpClient, HttpParams } from "@angular/common/http";
import { map, tap } from "rxjs/operators";
import { Injectable } from "@angular/core";
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

  /**
   * token验证器
   */
  token(control: AbstractControl): ValidationErrors | null {
    const token = control.value as string;
    // 验证只包含数字和字母
    for (let i = 0; i < token.length; i++) {
      let ascii = token[i].charCodeAt(0) as number;
      if (!(ascii >= 48 && ascii <= 57 || ascii >= 97 && ascii <= 122 || ascii >= 65 && ascii <= 90)) {
        return {token: ' '}
      }
    }
    return null;
  }

  tokenLength(control: AbstractControl): ValidationErrors | null {
    const token = control.value as string;
    if (token.length) {
      if (token.length === 64) {
        return null;
      } else {
        return {tokenLength: ' '}
      }
    }
    return null;
  }

  secret(control: AbstractControl): ValidationErrors | null {
    const token = control.value as string;
    // 验证只包含数字和字母
    for (let i = 0; i < token.length; i++) {
      let ascii = token[i].charCodeAt(0) as number;
      if (!(ascii >= 48 && ascii <= 57 || ascii >= 97 && ascii <= 122 || ascii >= 65 && ascii <= 90)) {
        return {secret: ' '}
      }
    }
    return null;
  }

  urlValidate(control: AbstractControl): ValidationErrors | null {
    const url = control.value as string;
    if (checkUrl(url)) {
      return null;
    } else if (url.length) {
      return {urlValidate: ' '}
    } else {
      return null;
    }
  }

  nameValidate(control: AbstractControl): ValidationErrors | null {
    const name = control.value as string;
    if(checkSpace(name)){
      return null
    }
    return {space: ' '}
  }


}

function checkUrl(val: string) {
  const myReg = /^(?:http(s)?:\/\/)?[\w.-]+(?:\.[\w\.-]+)+[\w\-\._~:/?#[\]@!\$&'\(\)\*\+,;=.]+[0-9]$/;
  return myReg.test(val);
}

function checkSpace(val: string){
  const myReg = /^\S*$/;
  return myReg.test(val);
}
