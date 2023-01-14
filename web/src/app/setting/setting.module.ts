import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SettingComponent } from './setting.component';
import {ThyFormModule} from 'ngx-tethys/form';
import {ThyInputModule} from 'ngx-tethys/input';
import {ThyButtonModule} from 'ngx-tethys/button';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {ThyLayoutModule} from 'ngx-tethys/layout';
import {ThyCardModule} from 'ngx-tethys/card';
import {ThyDividerModule} from 'ngx-tethys/divider';
import {ThyIconModule} from 'ngx-tethys/icon';
import {ThyNavModule} from 'ngx-tethys/nav';
import {THY_NOTIFY_DEFAULT_CONFIG, ThyNotifyModule, ThyNotifyService} from 'ngx-tethys/notify';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { ThyBreadcrumbModule } from "ngx-tethys/breadcrumb";
import { ThyDialogModule } from "ngx-tethys/dialog";
import { ThyEmptyModule } from "ngx-tethys/empty";
import { ThyTagModule } from "ngx-tethys/tag";
import { ThyPaginationModule } from "ngx-tethys/pagination";
import { AddModule } from "./add/add.module";
import { EditModule } from "./edit/edit.module";



@NgModule({
  declarations: [
    SettingComponent,
  ],
  imports: [
    CommonModule,
    ThyFormModule,
    ThyInputModule,
    ThyButtonModule,
    ThyLayoutModule,
    ThyCardModule,
    ThyDividerModule,
    ThyIconModule,
    ThyNavModule,
    ThyNotifyModule,
    NoopAnimationsModule,
    ReactiveFormsModule,
    FormsModule,
    ThyBreadcrumbModule,
    ThyDialogModule,
    ThyEmptyModule,
    ThyTagModule,
    ThyPaginationModule,
    AddModule,
    EditModule,
  ],
  exports: [
    SettingComponent
  ],
  providers: [
    ThyNotifyService,
  ]
})
export class SettingModule { }
