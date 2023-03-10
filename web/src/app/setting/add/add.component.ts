import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { Setting } from "../../../entity/setting";
import { SettingService } from "../../../service/SettingService";
import { CommonValidators } from "../../validator/CommonValidators";
import { CommonService } from "../../../service/common.service";

@Component({
  selector: 'app-add',
  templateUrl: './add.component.html',
  styleUrls: ['./add.component.css']
})
export class AddComponent implements OnInit {
  formKeys = {
    gitlabUrl: 'gitlabUrl',
    token: 'token',
    secret: 'secret',
    name: 'name'
  }
  formGroup = new FormGroup({});
  @Output()
  isFinish = new EventEmitter<Setting | null>();

  constructor(private settingService: SettingService,
              private commonValidators: CommonValidators,
              private commonService: CommonService) {
  }

  ngOnInit(): void {
    this.formGroup.addControl(this.formKeys.gitlabUrl, new FormControl('', [Validators.required, this.commonValidators.urlValidate]));
    this.formGroup.addControl(this.formKeys.secret, new FormControl('', [Validators.required, this.commonValidators.secret], this.commonValidators.secretNotExist()));
    this.formGroup.addControl(this.formKeys.token, new FormControl('', [Validators.required, this.commonValidators.tokenLength, this.commonValidators.token]));
    this.formGroup.addControl(this.formKeys.name, new FormControl('', [Validators.required, this.commonValidators.nameValidate]));
  }

  /**
   * 返回
   */
  backWard() {
    this.isFinish.emit(null);
  }

  onSubmit(formGroup: FormGroup) {
    const setting = {
      gitlabUrl: this.formGroup.get(this.formKeys.gitlabUrl)!.value as string,
      token: this.formGroup.get(this.formKeys.token)!.value as string,
      secret: this.formGroup.get(this.formKeys.secret)!.value as string,
      name: this.formGroup.get(this.formKeys.name)!.value as string,
    } as Setting;
    this.settingService.save(setting)
      .subscribe(
        (setting) => {
          this.isFinish.emit(setting);
        }
      )
  }

  setRandomString() {
    const randomToken = this.commonService.randomString(20);
    this.formGroup.get(this.formKeys.secret)?.setValue(randomToken);
  }

}

