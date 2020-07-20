import { Component, OnInit } from '@angular/core';
import { CoreUserService } from 'app/main/content/_service/core-user.serivce';
import { UserRole } from 'app/main/content/_model/user';

@Component({
  selector: "service-test",
  templateUrl: './service-test.component.html',
  styleUrls: ['./service-test.component.scss'],
})
export class ServiceTestComponent implements OnInit {

  constructor(
    private userService: CoreUserService

  ) {

  }

  ngOnInit() {
    this.userService.findAll().toPromise().then((ret: any) => {
      console.log("findAll()");
      console.log(ret);
    });
    this.userService.findAllByRole(UserRole.ADMIN).toPromise().then((ret: any) => {
      console.log("findAllByRole(ADMIN)");
      console.log(ret);
    });
    this.userService.findAllByRole(UserRole.FLEXPROD).toPromise().then((ret: any) => {
      console.log("findAllByRole(FLEXPROD)");
      console.log(ret);
    });
    this.userService.findAllByRole(UserRole.HELP_SEEKER).toPromise().then((ret: any) => {
      console.log("findAllByRole(HELP_SEEKER)");
      console.log(ret);
    });
    this.userService.findAllByRole(UserRole.VOLUNTEER).toPromise().then((ret: any) => {
      console.log("findAllByRole(VOLUNTEER)");
      console.log(ret);
    });
    this.userService.findAllByRole(UserRole.RECRUITER).toPromise().then((ret: any) => {
      console.log("findAllByRole(RECRUITER)");
      console.log(ret);
    });

  }
}
