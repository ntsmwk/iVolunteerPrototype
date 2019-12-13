import { Component, OnInit, Inject } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Marketplace } from '../../../_model/marketplace';
import { ClassDefinitionService } from '../../../_service/meta/core/class/class-definition.service';
import { ClassInstance } from '../../../_model/meta/Class';
import { CoreMarketplaceService } from 'app/main/content/_service/core-marketplace.service';
import { QuestionService } from 'app/main/content/_service/question.service';
import { FormConfiguration, FormEntryReturnEventData } from 'app/main/content/_model/meta/form';
import { QuestionControlService } from 'app/main/content/_service/question-control.service';
import { PropertyInstance } from 'app/main/content/_model/meta/Property';
import { ClassInstanceService } from 'app/main/content/_service/meta/core/class/class-instance.service';
import { isNullOrUndefined } from 'util';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { SaveAsDialogComponent, SaveAsDialogData } from '../../configurator-editor/save-as-dialog/save-as-dialog.component';

export interface ClassInstanceFormPreviewDialogData {
  marketplace: Marketplace;
  classConfigurationIds: string[];
}

@Component({
  selector: 'class-instance-form-preview-dialog',
  templateUrl: './form-preview-dialog.component.html',
  styleUrls: ['./form-preview-dialog.component.scss'],
  providers: [QuestionService, QuestionControlService]
})
export class ClassInstanceFormPreviewDialogComponent implements OnInit {

  formConfigurations: FormConfiguration[];
  currentFormConfiguration: FormConfiguration;

  returnedClassInstances: ClassInstance[];

  isLoaded: boolean = false;


  constructor(
    public dialogRef: MatDialogRef<ClassInstanceFormPreviewDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: ClassInstanceFormPreviewDialogData,

    private marketplaceService: CoreMarketplaceService,
    private classDefinitionService: ClassDefinitionService,
    private classInstanceService: ClassInstanceService,
    private questionService: QuestionService,
    private questionControlService: QuestionControlService,
  ) {
  }

  ngOnInit() {
    let marketplaceId: string;

    this.returnedClassInstances = [];

    this.classDefinitionService.getAllParentsIdMap(this.data.marketplace, this.data.classConfigurationIds).toPromise().then((formConfigurations: FormConfiguration[]) => {

      this.formConfigurations = formConfigurations

      for (let config of this.formConfigurations) {
        config.formEntry.questions = this.questionService.getQuestionsFromProperties(config.formEntry.classProperties);
        config.formEntry.formGroup = this.questionControlService.toFormGroup(config.formEntry.questions);
      }

    }).then(() => {
      this.currentFormConfiguration = this.formConfigurations.pop();
      this.isLoaded = true;
    });
  }

  handleResultEvent(event: FormEntryReturnEventData) {

  }

  handleCloseClick() {
    this.dialogRef.close();
  }

  printAnything(anything: any) {
    console.log(anything);
  }

  navigateBack() {
    window.history.back();
  }

}