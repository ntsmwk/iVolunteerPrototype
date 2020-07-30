import { Component, OnInit, Input, EventEmitter, Output, OnChanges } from '@angular/core';
import { FormConfiguration, FormEntry, FormEntryReturnEventData } from 'app/main/content/_model/meta/form';
import { FormGroup, FormControl, FormArray } from '@angular/forms';
import { QuestionControlService } from 'app/main/content/_service/question-control.service';

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
  @Input() subEntry: boolean;
  @Output() result: EventEmitter<FormEntryReturnEventData> = new EventEmitter();
  @Output() tupleSelected: EventEmitter<any> = new EventEmitter();
  @Output() errorEvent: EventEmitter<boolean> = new EventEmitter();

  localExpanded: boolean;


  arrayIndex = 0;
  values: any[] = [];

  constructor(
    private qcs: QuestionControlService
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
    formArray.push(this.qcs.getControlForSubEntry(this.formEntry.questions));

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
