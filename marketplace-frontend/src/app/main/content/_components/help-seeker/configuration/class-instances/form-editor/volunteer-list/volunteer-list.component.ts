import { Component, OnInit, Input } from '@angular/core';
import { Router } from '@angular/router';


@Component({
  selector: 'app-instance-creation-volunteer-list',
  templateUrl: './volunteer-list.component.html',
  styleUrls: ['./volunteer-list.component.scss'],
  providers: []
})
export class InstanceCreationVolunteerListComponent implements OnInit {

  jsonString: string;

  constructor(
    private router: Router,

  ) {

  }

  ngOnInit() {
  }


  printAnything(anything: any) {
    console.log(anything);
  }


  navigateBack() {
    window.history.back();
  }

}
