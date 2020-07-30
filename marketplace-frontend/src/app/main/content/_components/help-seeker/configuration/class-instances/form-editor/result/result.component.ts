import { Component, OnInit, Input, EventEmitter, Output } from '@angular/core';
import { ClassInstance } from 'app/main/content/_model/meta/class';
import { Router, ActivatedRoute } from '@angular/router';


@Component({
  selector: 'app-instance-creation-result',
  templateUrl: './result.component.html',
  styleUrls: ['./result.component.scss'],
  providers: []
})
export class InstanceCreationResultComponent implements OnInit {

  @Input() resultClassInstance: ClassInstance;
  @Output() navigateBack: EventEmitter<boolean> = new EventEmitter();
  jsonString: string;
  queryParams: any;

  constructor(
    private router: Router,
    private route: ActivatedRoute,

  ) {
    // console.log('extras');
    // console.log(this.router.getCurrentNavigation().extras.state);
  }

  ngOnInit() {
    let queryParams: any;
    this.route.queryParams.subscribe(params => {
      queryParams = params;
    });

    this.jsonString = JSON.stringify(this.resultClassInstance);
  }

  handleAnotherClick() {
    this.router.routeReuseStrategy.shouldReuseRoute = () => false;
    this.router.onSameUrlNavigation = 'reload';
    this.router.navigate([`main/configurator/instance-editor/${this.resultClassInstance.marketplaceId}`], { queryParams: this.queryParams });
  }


  printAnything(anything: any) {
    console.log(anything);
  }


  handleFinishedClick() {
    this.navigateBack.emit(true);
  }


}
