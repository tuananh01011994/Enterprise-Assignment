import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { SignupComponent } from './signup/components/form/signup.component';
import {LoginComponent} from './login/components/login.component';


const routes: Routes = [
  {
    path : 'signup',
    component: SignupComponent
  },
  {
    path : 'login',
    component: LoginComponent
  },
  {
    path : '**',
    redirectTo: 'signup'
  },
  
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
  
 }
