import { Component, OnInit, Input, EventEmitter, Output } from '@angular/core';
import { FormConfiguration, FormEntry, FormEntryReturnEventData } from 'app/main/content/_model/configurator/form';
import { FormGroup, FormArray } from '@angular/forms';
import { DynamicFormItemControlService } from 'app/main/content/_service/dynamic-form-item-control.service';

@Component({
  selector: 'app-form-container',
  templateUrl: './form-container.component.html',
  styleUrls: ['./form-container.component.scss'],
  providers: []
})
export class FormContainerComponent implements OnInit {

  @Input() formEntry: FormEntry;
  @Input() formConfiguration: FormConfiguration;
  @Input() finishClicked: boolean;
  @Input() expanded: boolean;
  @Input() subEntry: boolean;
  @Output() result: EventEmitter<FormEntryReturnEventData> = new EventEmitter();
  @Output() tupleSelected: EventEmitter<any> = new EventEmitter();
  @Output() errorEvent: EventEmitter<boolean> = new EventEmitter();

  localExpanded: boolean;


  arrayIndex = 0;
  values: any[] = [];

  constructor(
    private formItemControlService: DynamicFormItemControlService
  ) {
  }

  ngOnInit() {
    this.localExpanded = this.expanded;
    // console.log(this.formEntry.formGroup.controls['entries']);
    // console.log(this.formEntry.formGroup.controls['entries'].get(this.arrayIndex + ''));
  }

  handleResultEvent(event: FormEntryReturnEventData) {
    this.values.push(event.value);
    this.arrayIndex++;

    if (this.arrayIndex === (this.formEntry.formGroup.controls['entries'] as FormArray).length) {
      let retValue: any = this.values;
      let valueKey = Object.keys(event.value)[0];
      const split = valueKey.split('.');
      valueKey = valueKey.substr(0, valueKey.length - split[0].length - 1);

      retValue = {};
      retValue[valueKey] = this.values;

      Promise.all([this.result.emit({ formConfigurationId: event.formConfigurationId, value: retValue })]).then(() => {
        this.arrayIndex = 0;
        this.values = [];
      });
    }
  }

  handleErrorEvent(evt: boolean) {
    // console.log(event);
    this.expanded = true;
    this.localExpanded = true;
    this.errorEvent.emit(evt);
  }

  navigateBack() {
    window.history.back();
  }

  handleTupleSelection(evt: { selection: { id: any, label: any }, formGroup: FormGroup }) {
    this.tupleSelected.emit(evt);

  }

  onAddSubEntryClicked() {
    const formArray = this.formEntry.formGroup.controls['entries'] as FormArray;
    formArray.push(this.formItemControlService.getControlForSubEntry(this.formEntry.formItems));

  }

  onRemoveSubEntryClicked(index: number) {
    const formArray = this.formEntry.formGroup.controls['entries'] as FormArray;
    formArray.removeAt(index);
  }

  handlePanelOpened() {
    this.localExpanded = true;
  }

  handlePanelClosed() {
    this.localExpanded = false;
  }

  getPanelExpandedState() {
    return this.localExpanded;
  }
}
