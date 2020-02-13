import { Component, OnInit, Input } from '@angular/core';
import { ClassInstance } from 'app/main/content/_model/meta/Class';
import { Router } from '@angular/router';


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
    private router: Router,

  ) {
    // console.log('extras');
    // console.log(this.router.getCurrentNavigation().extras.state);
  }

  ngOnInit() {
    this.jsonString = JSON.stringify(this.resultClassInstance);
  }

  handleAnotherClick() {
    this.router.routeReuseStrategy.shouldReuseRoute = () => false;
    this.router.onSameUrlNavigation = 'reload';
    this.router.navigate([`main/configurator/instance-editor/${this.resultClassInstance.marketplaceId}/top-down`], { queryParams: [this.resultClassInstance.classDefinitionId] });
  }


  printAnything(anything: any) {
    console.log(anything);
  }


  navigateBack() {
    window.history.back();
  }

}
