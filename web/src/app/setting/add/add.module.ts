import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AddComponent } from "./add.component";
import { ThyIconModule } from "ngx-tethys/icon";
import { ThyFormModule } from "ngx-tethys/form";
import { ThyInputModule } from "ngx-tethys/input";
import { ThyButtonModule } from "ngx-tethys/button";
import { ReactiveFormsModule } from "@angular/forms";
import { ThyEmptyModule } from "ngx-tethys/empty";



@NgModule({
  declarations: [
    AddComponent
  ],
  imports: [
    CommonModule,
    ThyIconModule,
    ThyFormModule,
    ThyInputModule,
    ThyButtonModule,
    ReactiveFormsModule,
    ThyEmptyModule
  ],
  exports: [
    AddComponent
  ]
})
export class AddModule { }
