import { Component, OnInit, Input, EventEmitter, Output } from '@angular/core';
import { FormConfiguration, FormEntry } from 'app/main/content/_model/meta/form';
import { FormGroup, FormControl } from '@angular/forms';

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
  @Input() expanded: boolean;
  @Input() ignoreValidity: boolean;
  @Output() result = new EventEmitter();
  @Output() tupleSelected: EventEmitter<any> = new EventEmitter();

  constructor() { }

  ngOnInit() {
    console.log(this.formEntry);
  }

  handleResultEvent(event) {
    this.result.emit(event);
  }

  navigateBack() {
    window.history.back();
  }

  handleTupleSelection(evt: { selection: { id: any, label: any }, formGroup: FormGroup }) {
    this.tupleSelected.emit(evt);

  }

}
