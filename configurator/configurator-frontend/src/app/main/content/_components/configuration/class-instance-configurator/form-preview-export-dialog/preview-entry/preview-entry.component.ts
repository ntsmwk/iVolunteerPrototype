import { Component, OnInit, Input, EventEmitter, Output } from '@angular/core';
import { FormConfiguration, FormEntry, FormEntryReturnEventData } from 'app/main/content/_model/configurator/form';
import { DynamicFormItemBase } from 'app/main/content/_model/dynamic-forms/item';

@Component({
  selector: 'app-form-preview-entry',
  templateUrl: './preview-entry.component.html',
  styleUrls: ['./preview-entry.component.scss'],
  providers: []
})
export class FormPreviewEntryComponent implements OnInit {

  @Input() formEntry: FormEntry;
  @Input() formConfiguration: FormConfiguration;
  @Input() exportClicked: boolean;
  @Input() expanded: boolean;
  @Output() result = new EventEmitter();
  @Output() tupleSelected: EventEmitter<any> = new EventEmitter();

  hasUnableToContinueProperty: boolean;

  constructor() { }

  ngOnInit() {

  }

  handleResultEvent() {
    if (this.formEntry.formGroup.valid) {
      this.result.emit(new FormEntryReturnEventData(this.formConfiguration.id, this.formEntry.formGroup.value));
    } else {
      console.log('invalid');
      //   const invalidKeys: string[] = [];

      //   for (const key of Object.keys(this.formEntry.formGroup.controls)) {
      //     if (this.formEntry.formGroup.controls[key].invalid) {
      //       invalidKeys.push(key);
      //     }
      //   }

      //   console.log("invalid");
      //   console.log(invalidKeys);
      //   if (invalidKeys.length === 1 && invalidKeys[0].endsWith('unableToContinue')) {
      //     this.result.emit(new FormEntryReturnEventData(this.formEntry.formGroup, this.formConfiguration.id));
      // }


    }
  }

  navigateBack() {
    window.history.back();
  }

  handleTupleSelection(opt) {
    console.log(opt);
    this.emitTupleSelectionEvent({ selection: opt, formEntry: this.formEntry });
  }

  emitTupleSelectionEvent(evt: { selection: { id: any, label: any }, formEntry: FormEntry }) {
    this.tupleSelected.emit(evt);
  }

  ngOnChanges() {
    if (this.exportClicked) {
      this.handleResultEvent();
    }
  }

  addFormItemEntry(formItem: DynamicFormItemBase<any>) {

    if (formItem.controlType === 'tuple') {
      this.hasUnableToContinueProperty = true;
    } else {
      return '- ' + formItem.label;
    }
  }


}
