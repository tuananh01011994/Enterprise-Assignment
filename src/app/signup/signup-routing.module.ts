import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {LoginComponent} from '../login/components/login.component';
import {SignupComponent} from './components/form/signup.component';


// const routes: Routes = [
//     {
//         path: '',
//         component: LoginComponent,
//         children: [
//           {path : '',
//           component: SignupComponent},
//           {
//             path: 'login',
//             component: LoginComponent
//           },
         
//         ]
//       }
// ];



const routes: Routes = [
  {
    path : 'loginn',
    component: LoginComponent
  },{
    path : '**',
    redirectTo: 'login'
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class SignupRoutingModule { }