import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EditComponent } from "./edit.component";
import { ThyIconModule } from "ngx-tethys/icon";
import { ThyFormModule } from "ngx-tethys/form";
import { ThyInputModule } from "ngx-tethys/input";
import { ThyButtonModule } from "ngx-tethys/button";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { ThyNotifyModule } from "ngx-tethys/notify";
import { ThyRadioModule } from "ngx-tethys/radio";



@NgModule({
  declarations: [
    EditComponent,
  ],
  imports: [
    CommonModule,
    ThyButtonModule,
    ThyFormModule,
    ThyIconModule,
    ThyRadioModule,
    ThyInputModule,
    ReactiveFormsModule,
    ThyNotifyModule,

  ],
  exports: [
    EditComponent
  ]
})
export class EditModule { }
