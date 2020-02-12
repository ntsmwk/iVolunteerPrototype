import { Component, OnInit, Input, EventEmitter, Output, OnChanges } from '@angular/core';
import { ClassInstance } from '../../../../_model/meta/Class';
import { PropertyInstance } from 'app/main/content/_model/meta/Property';
import { isNullOrUndefined } from 'util';
import { FormConfiguration, FormEntry } from 'app/main/content/_model/meta/form';


@Component({
  selector: 'app-form-entry-view',
  templateUrl: './form-entry-view.component.html',
  styleUrls: ['./form-entry-view.component.scss'],
  providers: []
})
export class FormEntryViewComponent implements OnInit {

  @Input() formEntry: FormEntry;
  @Input() formConfiguration: FormConfiguration;
  @Input() finishClicked: boolean;
  @Output() result = new EventEmitter();

  isLoaded = false;


  constructor(

  ) {
    // console.log('extras');
    // console.log(this.router.getCurrentNavigation().extras.state);
  }

  ngOnInit() {
    this.isLoaded = true;
  }


  handleResultEvent(event) {
    this.result.emit(event);
  }


  printAnything(anything: any) {
    console.log(anything);
  }


  navigateBack() {
    window.history.back();
  }

}
