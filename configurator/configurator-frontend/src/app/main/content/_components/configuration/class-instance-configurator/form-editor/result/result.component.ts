import { Component, OnInit, Input, EventEmitter, Output } from '@angular/core';
import { ClassInstance } from 'app/main/content/_model/configurator/class';
import { Router, ActivatedRoute, Params } from '@angular/router';


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

  queryParams: Params;

  constructor(
    private router: Router,
    private route: ActivatedRoute,

  ) {
    // console.log('extras');
    // console.log(this.router.getCurrentNavigation().extras.state);
  }

  ngOnInit() {
    this.route.queryParams.subscribe(params => {
      this.queryParams = params;
    });

    this.jsonString = JSON.stringify(this.resultClassInstance);
  }

  handleAnotherClick() {
    this.router.routeReuseStrategy.shouldReuseRoute = () => false;
    this.router.onSameUrlNavigation = 'reload';
    this.router.navigate([`main/instance-editor`], { queryParams: this.queryParams });
  }

  handleFinishedClick() {
    this.navigateBack.emit(true);
  }


}
