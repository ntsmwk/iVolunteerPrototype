import {Component} from '@angular/core';
import {Router} from '@angular/router';
import {Configuration} from '../../providers/configuration';


@Component({
  templateUrl: './login.component.html'
})

export class LoginComponent {

  private actionUrl: string;

  constructor(private router: Router, _configuration: Configuration) {
    this.actionUrl = _configuration.Server + '/auth/github';
  }

  loginWithGithub() {
    this.router.navigateByUrl(this.actionUrl);
  }
}
