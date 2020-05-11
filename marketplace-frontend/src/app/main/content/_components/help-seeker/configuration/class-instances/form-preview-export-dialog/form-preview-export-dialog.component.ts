import { Marketplace } from 'app/main/content/_model/marketplace';
import { Component, OnInit, Inject } from '@angular/core';
import { QuestionService } from 'app/main/content/_service/question.service';
import { QuestionControlService } from 'app/main/content/_service/question-control.service';
import { FormConfiguration, FormEntryReturnEventData } from 'app/main/content/_model/meta/form';
import { ClassInstance } from 'app/main/content/_model/meta/class';
import { Helpseeker } from 'app/main/content/_model/helpseeker';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { ClassDefinitionService } from 'app/main/content/_service/meta/core/class/class-definition.service';
import { LoginService } from 'app/main/content/_service/login.service';
import { ClassProperty } from 'app/main/content/_model/meta/property';
import { isNullOrUndefined } from 'util';

export interface ClassInstanceFormPreviewExportDialogData {
  marketplace: Marketplace;
  classConfigurationIds: string[];
}

@Component({
  selector: 'class-instance-form-preview-export-dialog',
  templateUrl: './form-preview-export-dialog.component.html',
  styleUrls: ['./form-preview-export-dialog.component.scss'],
  providers: [QuestionService, QuestionControlService]
})
export class ClassInstanceFormPreviewExportDialogComponent implements OnInit {

  formConfigurations: FormConfiguration[];
  currentFormConfiguration: FormConfiguration;

  returnedClassInstances: ClassInstance[];

  isLoaded = false;

  helpseeker: Helpseeker;


  constructor(
    public dialogRef: MatDialogRef<ClassInstanceFormPreviewExportDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: ClassInstanceFormPreviewExportDialogData,

    private classDefinitionService: ClassDefinitionService,
    private questionService: QuestionService,
    private questionControlService: QuestionControlService,
    private loginService: LoginService
  ) {
  }

  ngOnInit() {
    this.returnedClassInstances = [];

    this.loginService.getLoggedIn().toPromise().then((helpseeker: Helpseeker) => {
      this.helpseeker = helpseeker;

      // this.classDefinitionService
      //   .getAllParentsIdMap(this.data.marketplace, this.data.classConfigurationIds, this.helpseeker.tenantId)
      //   .toPromise()
      //   .then((formConfigurations: FormConfiguration[]) => {

      //     this.formConfigurations = formConfigurations;


      //     for (const config of this.formConfigurations) {
      //       const classProperties: ClassProperty<any>[] = [];
      //       for (const classProperty of config.formEntry.classProperties) {
      //         classProperty.id = classProperty.name;
      //       }

      //       config.formEntry.questions = this.questionService.getQuestionsFromProperties(config.formEntry.classProperties);
      //       config.formEntry.formGroup = this.questionControlService.toFormGroup(config.formEntry.questions);
      //     }

      //   }).then(() => {
      //     this.currentFormConfiguration = this.formConfigurations.pop();
      //     this.isLoaded = true;

      //     const returnData = new FormEntryReturnEventData(this.currentFormConfiguration.formEntry.formGroup, this.currentFormConfiguration.id);
      //     this.handleExportClick(returnData);
      //     this.handleCloseClick();
      //   });
    });


  }

  handleExportClick(returnData: FormEntryReturnEventData) {
    returnData.formGroup.enable();
    returnData.formGroup.updateValueAndValidity();

    const json =
      '{' +
      '"tenantId": "' + this.helpseeker.tenantId + '", ' +
      '"classDefinitionId": "' + this.currentFormConfiguration.formEntry.classDefinitions[0].id + '", ' +
      '"assets": [' + JSON.stringify(returnData.formGroup.value, this.replacer) + ']' +
      '}';
    this.exportFile([json]);
  }

  private replacer(key, value) {
    if (isNullOrUndefined(value)) {
      return '';
    } else {
      return value;
    }
  }

  private exportFile(content: string[]) {
    const blob = new Blob(content, { type: 'application/json' });
    const url = window.URL.createObjectURL(blob);

    const link = document.createElement('a');
    link.href = url;
    link.download = 'export.json';
    // this is necessary as link.click() does not work on the latest firefox
    link.dispatchEvent(new MouseEvent('click', { bubbles: true, cancelable: true, view: window }));

    setTimeout(() => {
      // For Firefox it is necessary to delay revoking the ObjectURL
      window.URL.revokeObjectURL(url);
      link.remove();
    }, 100);

  }

  handleCloseClick() {
    // console.log("handle close click");
    this.dialogRef.close(this.data);
  }

}