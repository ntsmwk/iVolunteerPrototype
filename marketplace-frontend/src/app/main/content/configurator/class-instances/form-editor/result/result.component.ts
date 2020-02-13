import { Component, OnInit, Input, EventEmitter, Output, OnChanges } from '@angular/core';


@Component({
  selector: 'app-instance-creation-result',
  templateUrl: './result.html',
  styleUrls: ['./result.scss'],
  providers: []
})
export class InstanceCreationResultComponent implements OnInit {

  isLoaded = false;


  constructor(

  ) {
    // console.log('extras');
    // console.log(this.router.getCurrentNavigation().extras.state);
  }

  ngOnInit() {
    this.isLoaded = true;
  }



  printAnything(anything: any) {
    console.log(anything);
  }


  navigateBack() {
    window.history.back();
  }

}
