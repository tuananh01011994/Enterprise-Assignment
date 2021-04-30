import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';
import { FormControl } from '@angular/forms';
import { Validators} from '@angular/forms';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})

export class LoginComponent implements OnInit {
  
  constructor(private router: Router) { }


  username: string;
  password: string;

  ngOnInit(): void {
  }

  
  login() : void {
  
  }

}
