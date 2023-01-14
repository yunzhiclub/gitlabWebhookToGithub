import { Component, Input, OnInit, Output, EventEmitter} from '@angular/core';

import { FormControl, FormGroup, Validators } from "@angular/forms";
import { SettingService } from "../../../service/SettingService";
import { Assert } from "@yunzhi/utils";
import { Setting } from "../../../entity/setting";
import { ThyNotifyService } from "ngx-tethys/notify";

@Component({
  selector: 'app-edit',
  templateUrl: './edit.component.html',
  styleUrls: ['./edit.component.css']
})
export class EditComponent implements OnInit {

  @Input()
  settingID!: number
  @Output()
  isFinish = new EventEmitter<Setting | null>();
  init= false;
  constructor(private settingService: SettingService,
              private notifyService: ThyNotifyService) { }

  ngOnInit(): void {
    Assert.isInteger(this.settingID, 'id must to int');
    this.settingService.getById(this.settingID)
      .subscribe(
        (setting) => {
          console.log(setting)
          this.setFormGroup(setting)
        }
      )
  }
  formGroup = new FormGroup({});
  formKeys = {
    gitlabUrl: 'gitlabUrl',
    token: 'token',
    secret: 'secret'
  }

  setFormGroup(setting: Setting) {
    this.formGroup.addControl(this.formKeys.token, new FormControl(setting.token, Validators.required));
    this.formGroup.addControl(this.formKeys.secret, new FormControl(setting.secret, Validators.required));
    this.formGroup.addControl(this.formKeys.gitlabUrl, new FormControl(setting.gitlabUrl, Validators.required));
    this.init=true;
  }
  onSubmit(formGroup: FormGroup) {
    const setting = {
      gitlabUrl: formGroup.get(this.formKeys.gitlabUrl)?.value,
      token: formGroup.get(this.formKeys.token)?.value,
      secret: formGroup.get(this.formKeys.secret)?.value
    } as Setting;

    this.settingService.update(this.settingID, setting)
      .subscribe({
        next: data => {
          this.notifyService.success('修改成功！');
          this.isFinish.emit(data);
        },
        error: err => {
          console.log(err)
          this.notifyService.error('创建失败', err);
        }
      })
  }
  /**
   * 返回
   */
  backWard() {
    this.isFinish.emit(null);
  }


}
