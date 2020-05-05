import { Marketplace } from 'app/main/content/_model/marketplace';
import { ClassDefinition, ClassInstance } from 'app/main/content/_model/meta/class';
import { Relationship } from 'app/main/content/_model/meta/relationship';
import { Component, OnInit, Inject } from '@angular/core';
import { QuestionService } from 'app/main/content/_service/question.service';
import { QuestionControlService } from 'app/main/content/_service/question-control.service';
import { FormConfiguration, FormEntry, FormEntryReturnEventData } from 'app/main/content/_model/meta/form';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { ClassDefinitionService } from 'app/main/content/_service/meta/core/class/class-definition.service';
import { isNullOrUndefined } from 'util';

export interface ClassInstanceFormPreviewDialogData {
    marketplace: Marketplace;
    classDefinitions: ClassDefinition[];
    relationships: Relationship[];
    rootClassDefinition: ClassDefinition;
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

    expectedNumberOfResults: number;


    isLoaded = false;


    constructor(
        public dialogRef: MatDialogRef<ClassInstanceFormPreviewDialogComponent>,
        @Inject(MAT_DIALOG_DATA) public data: ClassInstanceFormPreviewDialogData,

        private classDefinitionService: ClassDefinitionService,
        private questionService: QuestionService,
        private questionControlService: QuestionControlService,
    ) {
    }

    ngOnInit() {

        console.log(this.data);

        this.returnedClassInstances = [];

        this.classDefinitionService
            .getFromConfigurationPreview(this.data.marketplace, this.data.classDefinitions, this.data.relationships, this.data.rootClassDefinition)
            .toPromise()
            .then((ret: FormConfiguration[]) => {
                this.formConfigurations = ret;

                for (const config of this.formConfigurations) {
                    config.formEntry = this.addQuestionsAndFormGroup(config.formEntry, config.formEntry.classDefinitions[0].id + '.');
                }

            }).then(() => {
                this.currentFormConfiguration = this.formConfigurations.pop();
                this.isLoaded = true;
            });
    }

    private addQuestionsAndFormGroup(formEntry: FormEntry, idPrefix: string) {
        formEntry.questions = this.questionService.getQuestionsFromProperties(formEntry.classProperties, idPrefix);
        formEntry.formGroup = this.questionControlService.toFormGroup(formEntry.questions);

        if (!isNullOrUndefined(formEntry.subEntries)) {
            for (let subEntry of formEntry.subEntries) {
                const newIdPrefix = idPrefix + subEntry.classDefinitions[0].id + '.';
                subEntry = this.addQuestionsAndFormGroup(subEntry, newIdPrefix);
            }
        }
        return formEntry;
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