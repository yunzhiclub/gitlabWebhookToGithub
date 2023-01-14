import { Injectable } from "@angular/core";
import { map, Observable } from "rxjs";
import { Setting } from "../entity/setting";
import { HttpClient, HttpParams } from "@angular/common/http";
import { Router } from "@angular/router";
import { Assert } from "@yunzhi/ng-mock-api";
import { Page } from "@yunzhi/ng-common";

@Injectable({
  providedIn: 'root'
})
export class SettingService {
  protected baseUrl = 'setting';

  constructor(protected httpClient: HttpClient,
              private router: Router) {
  }

  /**
   * 分页方法
   * @param param 查询参数
   */
  public page(param: {
    page: number,
    size: number,
  }): Observable<Page<Setting>> {
    const httpParams = new HttpParams()
      .append('page', param.page)
      .append('size', param.size)
    return this.httpClient.get<Page<Setting>>(`${this.baseUrl}/page`, {params: httpParams})
      .pipe(map(data => new Page<Setting>(data)));
  }

  /**
   * 保存
   * @param setting
   */
  public save(setting: {
    token: string,
    secret: string,
    gitlabUrl: string,
  }): Observable<Setting> {
    setting = setting as Setting;
    return this.httpClient.post<Setting>(`${this.baseUrl}`, setting);
  }

  /**
   * 删除
   */
  delete(settingId: number): Observable<null> {
    return this.httpClient.delete<null>(`${this.baseUrl}/${settingId.toString()}`);
  }

  /**
   * 根据id获取实体
   */
  getById(id: number): Observable<Setting> {
    Assert.isNumber(id, 'id must be number');
    return this.httpClient.get<Setting>(`${this.baseUrl}/${id}`);
  }

  /**
   * 更新
   */
  public update(settingId: number, setting: {
    token: string,
    secret: string,
    gitlabUrl: string,
  }): Observable<Setting> {
    Assert.isNumber(settingId, 'settingId must be number');
    Assert.isNotNullOrUndefined(setting, setting.token, setting.secret, setting.gitlabUrl,
      'some properties must be passed');

    return this.httpClient.put<Setting>(`${this.baseUrl}/${settingId}`, setting);
  }
}
