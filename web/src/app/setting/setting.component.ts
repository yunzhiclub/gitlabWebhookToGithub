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
    // 有变化时重置错误信息
    // this.loginForm.valueChanges
    //   .subscribe(() => {
    //     this.errorInfo = '';
    //   });
  }

  // ngAfterViewInit() {
  //   fromEvent(window, 'resize')
  //     .subscribe((event) => {
  //       //网页可见区域宽：
  //       const width = document.documentElement.clientWidth;
  //       //网页可见区域高：
  //       const height = document.documentElement.clientHeight;
  //       // 本元素高度
  //       let parentHeight = this.loginDiv.nativeElement.clientHeight + 100;
  //       // 父元素宽度
  //       let parentWidth = this.loginDiv.nativeElement.offsetParent.clientWidth;
  //
  //       this.left = (width - parentWidth)/7 + "px";
  //       this.top = (height - parentHeight)/2 + "px";
  //     });
  // }

  // login() {
  //   const setting = {
  //     username: this.loginForm.get(this.key.username)!.value as string,
  //     password: this.loginForm.get(this.key.password)!.value as string,
  //   };
  //   this.userService.login(setting)
  //     .subscribe({
  //       next: (user) => {
  //         this.userService.initCurrentLoginUser(() => {
  //           this.notifyService.success('登录成功!');
  //           if (user.identity == ROLE_TYPE.systemAdmin.value) {
  //             this.router.navigateByUrl('serviceArea').then();
  //           } else {
  //             this.router.navigateByUrl('divisionalWorks/process').then();
  //           }
  //         }).subscribe();
  //       },
  //         error: () => {
  //           this.errorInfo = '登录失败，请检查您填写的信息是否正确';
  //         }
  //       }
  //     )
  // }

  // info() {
  //   const html =  '<p>开发人员: 韦卫毅、李明傲、陈世航、何攀、张瑞</p>' +
  //     '<p >联系我们(13920618851微信同号)</p> ' ;
  //   this.commonService.info(() => {}, html, '技术支持')
  // }

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
