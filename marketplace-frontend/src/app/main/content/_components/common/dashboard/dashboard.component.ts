import { Component, OnInit } from '@angular/core';

import { LoginService } from '../../../_service/login.service';
import { UserRole } from '../../../_model/user';
import { Router } from '@angular/router';

@Component({
  selector: "dashboard",
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss'],
})
export class DashboardComponent implements OnInit {
  public role: UserRole;

  constructor(private loginService: LoginService, private router: Router) { }

  async ngOnInit() {
    this.role = <UserRole>(
      await this.loginService.getLoggedInUserRole().toPromise()
    );

    if (this.role === UserRole.RECRUITER) {
      this.router.navigate(['main/recruitment']);
    }
    if (this.role === UserRole.ADMIN) {
      this.router.navigate(['main/marketplace/all']);
    }
  }
}
