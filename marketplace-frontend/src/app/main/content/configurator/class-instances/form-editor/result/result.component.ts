import { Component, OnInit, Input } from '@angular/core';
import { ClassInstance } from 'app/main/content/_model/meta/Class';


@Component({
  selector: 'app-instance-creation-result',
  templateUrl: './result.component.html',
  styleUrls: ['./result.component.scss'],
  providers: []
})
export class InstanceCreationResultComponent implements OnInit {

  @Input() resultClassInstance: ClassInstance;
  jsonString: string;

  constructor(

  ) {
    // console.log('extras');
    // console.log(this.router.getCurrentNavigation().extras.state);
  }

  ngOnInit() {
    this.jsonString = JSON.stringify(this.resultClassInstance);
  }



  printAnything(anything: any) {
    console.log(anything);
  }


  navigateBack() {
    window.history.back();
  }

}
