import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {LoginComponent} from './components/login.component'
import {MaterialModule} from '../shared/material.module';
import { AppRoutingModule } from '../app-routing.module';


@NgModule({
  declarations: [
      LoginComponent
  ],
  imports: [
    CommonModule,
    MaterialModule,
    AppRoutingModule
  ]
})
export class LoginModule { }
