import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {SignupComponent} from './components/form/signup.component';
import {DialogComponent} from  './components/dialog/dialog.component';
import {MaterialModule} from '../shared/material.module';
import { RouterModule } from '@angular/router';
import { AppRoutingModule } from '../app-routing.module';

import {MatButtonModule} from '@angular/material/button';
import { MatSliderModule } from '@angular/material/slider';

@NgModule({
  declarations: [
    SignupComponent,
    DialogComponent,
    
  ],
  imports: [
    CommonModule,
    MaterialModule,
    AppRoutingModule
  ],
  
})
export class SignupModule { }
