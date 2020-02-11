import { Component, OnInit, Input } from '@angular/core';
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

  isLoaded = true;


  constructor(

  ) {
    // console.log('extras');
    // console.log(this.router.getCurrentNavigation().extras.state);
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
