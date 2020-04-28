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

  constructor() { }

  ngOnInit() {
  }

  handleResultEvent(event) {
    this.result.emit(event);
  }

  navigateBack() {
    window.history.back();
  }

}
