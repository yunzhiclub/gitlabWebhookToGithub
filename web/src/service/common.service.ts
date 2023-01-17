import {Injectable} from "@angular/core";
import { SweetAlertResult } from "sweetalert2";
import Swal from 'sweetalert2';
@Injectable({
  providedIn: 'root'
})
export class CommonService{
  /**
   * 操作失败提示框
   * @param callback    回调
   * @param description 描述
   * @param title       标题
   */
  error(callback?: () => void, description: string = '', title: string = '操作失败'): void {
    Swal.fire({
      titleText: title,
      text: description,
      icon: 'error',
      background: '#F7F8FA',
      allowOutsideClick: false,
      confirmButtonText: '确定',
      confirmButtonColor: '#007BFF',
      showCancelButton: false
    }).then((result: SweetAlertResult) => {
      if (result.value) {
        // 执行回调
        if (callback) {
          callback();
        }
      }
    });
  }

  /**
   * 普通提示框
   * @param callback    回调
   * @param description 描述
   * @param title       标题
   */
  info(callback?: () => void, description: string = '', title: string = ''): void {
    Swal.fire({
      titleText: title,
      html: description,
      background: '#F7F8FA',
      allowOutsideClick: false,
      confirmButtonText: '确定',
      confirmButtonColor: '#007BFF',
      showCancelButton: false
    }).then((result: SweetAlertResult) => {
      if (result.value) {
        // 执行回调
        if (callback) {
          callback();
        }
      }
    });
  }

  public randomString(length: number) {
    return hex(length);
  }
}

function hex(n: number) {
  n = n || 20;
  var result = '';
  while (n--) {
    result += Math.floor(Math.random() * 16).toString(16).toUpperCase();
  }
  return result;
}
