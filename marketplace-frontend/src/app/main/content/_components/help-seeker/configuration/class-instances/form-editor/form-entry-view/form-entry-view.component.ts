import { Component, OnInit, Input, EventEmitter, Output } from '@angular/core';
import { FormConfiguration, FormEntry } from 'app/main/content/_model/meta/form';
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
  @Output() result = new EventEmitter();
  @Output() tupleSelected: EventEmitter<any> = new EventEmitter();

  constructor(
    private qcs: QuestionControlService
  ) {
  }

  ngOnInit() {
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

  onAddSubEntryClicked() {
    const formArray = this.formEntry.formGroup.controls['entries'] as FormArray;
    formArray.push(this.qcs.getControlForSubEntry(this.formEntry.questions));

  }

  onRemoveSubEntryClicked(index: number) {
    const formArray = this.formEntry.formGroup.controls['entries'] as FormArray;
    formArray.removeAt(index);
  }
}
