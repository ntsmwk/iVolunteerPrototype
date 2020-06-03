import { Marketplace } from 'app/main/content/_model/marketplace';
import { Component, OnInit, Inject } from '@angular/core';
import { QuestionService } from 'app/main/content/_service/question.service';
import { QuestionControlService } from 'app/main/content/_service/question-control.service';
import { FormConfiguration, FormEntryReturnEventData, FormEntry } from 'app/main/content/_model/meta/form';
import { ClassInstance } from 'app/main/content/_model/meta/class';
import { Helpseeker } from 'app/main/content/_model/helpseeker';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { ClassDefinitionService } from 'app/main/content/_service/meta/core/class/class-definition.service';
import { LoginService } from 'app/main/content/_service/login.service';
import { ClassProperty } from 'app/main/content/_model/meta/property';
import { isNullOrUndefined } from 'util';
import { FormGroup, FormControl } from '@angular/forms';
import { QuestionBase } from 'app/main/content/_model/dynamic-forms/questions';

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

  expectedNumberOfResults: number;

  isLoaded = false;
  exportClicked = false;

  results: FormEntryReturnEventData[] = [];

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
    this.expectedNumberOfResults = 0;

    this.loginService.getLoggedIn().toPromise().then((helpseeker: Helpseeker) => {
      this.helpseeker = helpseeker;

      this.classDefinitionService
        .getFormConfigurations(this.data.marketplace, this.data.classConfigurationIds)
        .toPromise()
        .then((formConfigurations: FormConfiguration[]) => {

          this.formConfigurations = formConfigurations;


          for (const config of this.formConfigurations) {
            const classProperties: ClassProperty<any>[] = [];
            for (const classProperty of config.formEntry.classProperties) {
              classProperty.id = classProperty.name;
            }

            config.formEntry = this.addQuestionsAndFormGroup(config.formEntry, config.formEntry.id);

            // config.formEntry.questions = this.questionService.getQuestionsFromProperties(config.formEntry.classProperties);
            // config.formEntry.formGroup = this.questionControlService.toFormGroup(config.formEntry.questions);
          }

        }).then(() => {
          this.currentFormConfiguration = this.formConfigurations.pop();
          this.isLoaded = true;

          console.log(this.currentFormConfiguration);

          // const returnData = new FormEntryReturnEventData(this.currentFormConfiguration.formEntry.formGroup, this.currentFormConfiguration.id);
          // this.handleExportClick(returnData);
          // this.handleCloseClick();
        });
    });


  }

  private addQuestionsAndFormGroup(formEntry: FormEntry, idPrefix: string) {
    formEntry.questions = this.questionService.getQuestionsFromProperties(formEntry.classProperties, idPrefix);

    // clear validators for everything except the unableToContinue Property-Question
    for (const question of formEntry.questions) {
      if (question.controlType !== 'tuple') {
        question.validators = [];
      }
    }

    formEntry.formGroup = this.questionControlService.toFormGroup(formEntry.questions);


    if (!isNullOrUndefined(formEntry.questions) && formEntry.questions.length > 0) {
      this.expectedNumberOfResults++;
    }

    if (!isNullOrUndefined(formEntry.subEntries)) {
      for (let subEntry of formEntry.subEntries) {
        const newIdPrefix = subEntry.id;
        subEntry = this.addQuestionsAndFormGroup(subEntry, newIdPrefix);
      }
    }
    return formEntry;
  }

  handleTupleSelection(evt: { selection: { id: any; label: any }; formEntry: FormEntry }) {
    console.log("handling selection")
    let unableToContinueControl: FormControl;
    // let unableToContinueControlKey: string;
    let unableToContinueQuestion: QuestionBase<any>;
    let pathPrefix: string;
    this.results = [];

    Object.keys(evt.formEntry.formGroup.controls).forEach((c) => {
      if (c.endsWith("unableToContinue")) {
        // unableToContinueControlKey = c;
        unableToContinueControl = evt.formEntry.formGroup.controls[c] as FormControl;
        pathPrefix = c.replace(/\.[^.]*unableToContinue/, "");
      }
    });

    unableToContinueQuestion = evt.formEntry.questions.find(q => q.key.endsWith('unableToContinue'));
    console.log(this.data.marketplace);
    console.log(pathPrefix);
    console.log(evt.selection.id);


    this.classDefinitionService.getFormConfigurationChunk(this.data.marketplace, pathPrefix, evt.selection.id)
      .toPromise().then((retFormEntry: FormEntry) => {
        const currentFormEntry = this.getFormEntry(pathPrefix, this.currentFormConfiguration.formEntry.id, this.currentFormConfiguration.formEntry);

        const unableToContinueProperty = currentFormEntry.classProperties.find(p => p.id.endsWith('unableToContinue'));

        console.log(unableToContinueProperty);
        unableToContinueProperty.defaultValues = [evt.selection];
        retFormEntry.classProperties.push(unableToContinueProperty);

        retFormEntry = this.addQuestionsAndFormGroup(retFormEntry, pathPrefix);



        currentFormEntry.classDefinitions = retFormEntry.classDefinitions;

        currentFormEntry.classProperties = retFormEntry.classProperties;

        currentFormEntry.enumRepresentations = retFormEntry.enumRepresentations;

        currentFormEntry.formGroup = retFormEntry.formGroup;

        currentFormEntry.imagePath = retFormEntry.imagePath;

        currentFormEntry.questions = retFormEntry.questions;

        currentFormEntry.subEntries = retFormEntry.subEntries;


        this.expectedNumberOfResults = 0;
        this.calculateExpectedResults(this.currentFormConfiguration.formEntry);
      });
  }

  private getFormEntry(pathString: string, currentPath: string, currentFormEntry: FormEntry): FormEntry {
    if (currentPath === pathString) {
      return currentFormEntry;
    }

    currentFormEntry = currentFormEntry.subEntries.find((e) => pathString.startsWith(e.id));
    return this.getFormEntry(pathString, currentFormEntry.id, currentFormEntry);
  }

  private calculateExpectedResults(formEntry: FormEntry) {
    this.expectedNumberOfResults++;
    for (const subFormEntry of formEntry.subEntries) {
      this.calculateExpectedResults(subFormEntry);
    }
  }

  handleExportClick() {
    this.exportClicked = true;
  }

  handleResultEvent(event: FormEntryReturnEventData) {
    this.results.push(event);
    console.log("actual vs expected");
    console.log(this.results.length + "vs" + this.expectedNumberOfResults);
    // const unableToContinue = this.containsUnsetUnableToContinue(this.results.map(fg => fg.formGroup));
    if (this.results.length === this.expectedNumberOfResults /*&& !unableToContinue*/) {

      this.doExport();

      setTimeout(() => {
        this.exportClicked = false;
      });
    } else {
      this.exportClicked = false;


      // if (unableToContinue) {
      //   this.results.pop();
      // } else {
      //   // this.results = [];
      // }
    }
  }

  // private containsUnsetUnableToContinue(formGroups: FormGroup[]) {
  //   for (const formGroup of formGroups) {
  //     Object.keys(formGroup.controls).forEach(k => {
  //       if (k.endsWith('unableToContinue')) {
  //         return true;
  //       }
  //     });
  //   }

  //   return false;
  // }

  private doExport() {
    const json =
      '{' +
      '"tenantId": "' + this.helpseeker.tenantId + '", ' +
      this.addClassToJSON(this.currentFormConfiguration.formEntry) +
      '}';

    this.exportFile([json]);
  }

  private addClassToJSON(formEntry: FormEntry) {
    return '"classDefinitionId": "' + formEntry.classDefinitions[0].id + '", ' +
      '"properties": [' + this.addPropertiesToJSON(formEntry) + '],' +
      '"subClassInstances": [' +
      this.addClassesToJSON(formEntry.subEntries) +
      ']';
  }

  private addClassesToJSON(formEntries: FormEntry[]) {
    let returnString = '';

    for (let i = 0; i < formEntries.length; i++) {
      returnString += '{';
      returnString += this.addClassToJSON(formEntries[i]);
      returnString += '}';

      if (i < formEntries.length - 1) {
        returnString += ',';
      }
    }
    return returnString;
  }

  private addPropertiesToJSON(formEntry: FormEntry) {
    let returnString = '{';

    for (let i = 0; i < formEntry.questions.length; i++) {

      if (formEntry.questions[i].controlType !== 'tuple') {

        returnString += ` "${formEntry.questions[i].label}": ""`;
        if (i < formEntry.questions.length - 1) {
          returnString += ',';
        }

      } else {
        returnString = returnString.substring(0, returnString.length - 1);
      }
    }

    returnString += '}';



    return returnString;
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