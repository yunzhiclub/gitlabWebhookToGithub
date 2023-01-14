import { NgModule } from '@angular/core';
import { BrowserModule, DomSanitizer } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { RouterModule } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { ThyIconModule, ThyIconRegistry } from 'ngx-tethys/icon';
import { SettingModule } from './setting/setting.module';
import { ThyLayoutModule } from "ngx-tethys/layout";
import { ThyInputModule } from "ngx-tethys/input";
import { ThySpaceModule } from "ngx-tethys/space";
import { ThyTagModule } from "ngx-tethys/tag";
import { ThyButtonModule } from "ngx-tethys/button";
import { ThyPaginationModule } from "ngx-tethys/pagination";
import { ReactiveFormsModule } from "@angular/forms";
import { ApiProModule } from "./interceptors/apiPro.module";


@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    RouterModule,
    HttpClientModule,
    SettingModule,
    ThyLayoutModule,
    ThyInputModule,
    ThyIconModule,
    ThySpaceModule,
    ThyTagModule,
    ThyButtonModule,
    ThyPaginationModule,
    ReactiveFormsModule,
    ApiProModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
  constructor(iconRegistry: ThyIconRegistry, sanitizer: DomSanitizer) {
    iconRegistry.addSvgIconSet(sanitizer.bypassSecurityTrustResourceUrl(`assets/icons/defs/svg/sprite.defs.svg`));
    iconRegistry.addSvgIconSet(sanitizer.bypassSecurityTrustResourceUrl(`assets/icons/symbol/svg/sprite.symbol.svg`));
  }
}
