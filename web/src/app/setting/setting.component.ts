import { Component, ElementRef, OnInit, Renderer2, ViewChild } from '@angular/core';
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { Params, Router } from "@angular/router";
import { ThyNotifyService } from 'ngx-tethys/notify';
import { CommonService } from "../../service/common.service";
import { Page } from "@yunzhi/ng-common";
import { Setting } from "../../entity/setting";
import { ThyDialog } from "ngx-tethys/dialog";
import { SettingService } from "../../service/SettingService";

@Component({
  selector: 'app-login',
  templateUrl: './setting.component.html',
  styleUrls: ['./setting.component.css']
})
export class SettingComponent implements OnInit {
  constructor(private router: Router,
              private notifyService: ThyNotifyService,
              private commonService: CommonService,
              private dialog: ThyDialog,
              private settingService: SettingService
  ) {
  }

  /**
   * 所编辑建筑的id，用于传给子组件
   */
  editSettingId!: number;

  currentIndex = 1;

  pageSize = 10;

  component = 'index';

  serviceAreaId!: string;

  setServiceAreaId = false as Boolean;

  params = {} as Params;

  pageData = {} as Page<Setting>;


  // @ViewChild('loginDiv') loginDiv!: ElementRef;

  loginForm: FormGroup = new FormGroup({});
  key = {
    page: 'page',
    size: 'size'
  }
  /** 错误信息 */
  errorInfo: string | undefined;
  year = new Date().getFullYear();


  model = {
    name: '',
    password: ''
  };


  onPageSizeChanged(event: number) {
    this.pageSize = event;
  }

  /**
   * @param $event
   */
  onAddFinish($event: Setting | null) {
    if ($event) {
      this.pageData.content.unshift($event);
    }
    this.component = 'index';
  }

  onEditFinish($event: Setting | null) {
    if ($event) {
      const index = this.pageData.content.map(el => el.id).indexOf($event.id);
      this.pageData.content[index] = $event;
    }
    this.component = 'index';
  }

  query(params: Params) {
    const mergeParams = {...this.params, ...params};
    this.params = mergeParams;
    this.settingService.page({
        page: mergeParams[this.key.page] - 1,
        size: mergeParams[this.key.size],
      })
      .subscribe({
        next: (page) => {
          this.pageData = page;
          this.pageData.number = page.number + 1;
        }
      })
  }

  /**
   * 点击分页
   * @param event 当前页
   */
  onPageChange(event: number): void {
    this.query({page: event})
  }

  /**
   * 点击改变每页大小
   * @param event 每页大小
   */
  onSizeChange(event: number): void {
    this.query({size: event})
  }


  ngOnInit(): void {
    this.errorInfo = '';
    this.query({page: 1, size: 5});
  }

  onDelete(setting: Setting) {
    this.dialog.confirm({
      title: '确认删除',
      content: '确认要删除吗？此操作不可逆</script>',
      footerAlign: 'right',
      okType: 'danger',
      okText: '确认删除',
      cancelText: '取消删除',
      onOk: () => {
        const index = this.pageData.content.indexOf(setting);
        this.settingService.delete(setting.id)
          .subscribe({
            next: () => {
              this.notifyService.success(`删除成功`);
              this.pageData.content.splice(index, 1);
            },
            error: err => {
              this.notifyService.error('删除失败', err);
            }
          })
      }
    });
  }

  onEdit(settingID: number) {
    this.editSettingId = settingID;
    this.component = 'edit';
  }

}
