import {Component} from '@angular/core';
import {Router} from '@angular/router';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {LoginService} from '../_service/login.service';
import {HttpResponse} from '@angular/common/http';
import {MessageService} from '../_service/message.service';

@Component({
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  loginForm: FormGroup;

  constructor(formBuilder: FormBuilder,
              private router: Router,
              private loginService: LoginService,
              private messageService: MessageService) {
    this.loginForm = formBuilder.group({
      'username': new FormControl('', Validators.required),
      'password': new FormControl('', Validators.required)

    });
  }

  login() {
    if (!this.loginForm.valid) {
      return;
    }

    this.loginService.login(this.loginForm.value.username, this.loginForm.value.password)
      .toPromise()
      .then((response: HttpResponse<any>) => {
        localStorage.setItem('token', response.headers.get('Authorization'));
        this.messageService.broadcast('login', {});
        this.router.navigate(['/tasks']);
      });
  }

}
