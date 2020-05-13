import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { QuestionService } from 'app/main/content/_service/question.service';
import { QuestionControlService } from 'app/main/content/_service/question-control.service';
import { Marketplace } from 'app/main/content/_model/marketplace';
import { FormConfiguration, FormEntryReturnEventData, FormEntry } from 'app/main/content/_model/meta/form';
import { ClassInstance } from 'app/main/content/_model/meta/class';
import { Router, ActivatedRoute } from '@angular/router';
import { CoreMarketplaceService } from 'app/main/content/_service/core-marketplace.service';
import { ClassDefinitionService } from 'app/main/content/_service/meta/core/class/class-definition.service';
import { ClassInstanceService } from 'app/main/content/_service/meta/core/class/class-instance.service';
import { ObjectIdService } from 'app/main/content/_service/objectid.service.';
import { AbstractControl, FormGroup, FormControl } from '@angular/forms';
import { PropertyInstance, PropertyType } from 'app/main/content/_model/meta/property';
import { isNullOrUndefined } from 'util';

@Component({
    selector: 'app-class-instance-form-editor',
    templateUrl: './class-instance-form-editor.component.html',
    styleUrls: ['./class-instance-form-editor.component.scss'],
    providers: [QuestionService, QuestionControlService]
})
export class ClassInstanceFormEditorComponent implements OnInit {

    marketplace: Marketplace;

    formConfigurations: FormConfiguration[];
    currentFormConfiguration: FormConfiguration;

    returnedClassInstances: ClassInstance[];


    canContinue: boolean;
    canFinish: boolean;
    lastEntry: boolean;

    isLoaded = false;
    finishClicked = false;
    showResultPage = false;


    // formConfigurationType: string;

    expectedNumberOfResults: number;
    results: FormEntryReturnEventData[];

    @ViewChild('contentDiv', { static: false }) contentDiv: ElementRef;
    resultClassInstance: ClassInstance;

    constructor(private router: Router,
        private route: ActivatedRoute,
        private marketplaceService: CoreMarketplaceService,
        private classDefinitionService: ClassDefinitionService,
        private classInstanceService: ClassInstanceService,
        private questionService: QuestionService,
        private questionControlService: QuestionControlService,
        private objectIdService: ObjectIdService
    ) {
        // console.log('extras');
        // console.log(this.router.getCurrentNavigation().extras.state);
    }

    ngOnInit() {
        let marketplaceId: string;
        const childClassIds: string[] = [];

        this.returnedClassInstances = [];
        this.results = [];
        this.expectedNumberOfResults = 0;

        Promise.all([
            this.route.params.subscribe(params => {
                marketplaceId = params['marketplaceId'];
                // this.formConfigurationType = params['type'];
            }),
            this.route.queryParams.subscribe(queryParams => {
                let i = 0;
                while (!isNullOrUndefined(queryParams[i])) {
                    childClassIds.push(queryParams[i]);
                    i++;
                }
            })
        ]).then(() => {
            this.marketplaceService.findById(marketplaceId).toPromise().then((marketplace: Marketplace) => {
                this.marketplace = marketplace;

                // if (isNullOrUndefined(this.formConfigurationType)) {
                //     this.formConfigurationType = 'top-down';
                // }

                this.classDefinitionService.getFormConfigurations(this.marketplace, childClassIds).toPromise()
                    .then((formConfigurations: FormConfiguration[]) => {
                        this.formConfigurations = formConfigurations;
                        for (const config of this.formConfigurations) {
                            config.formEntry = this.addQuestionsAndFormGroup(config.formEntry, config.formEntry.id);
                        }
                    })

                    .then(() => {
                        this.currentFormConfiguration = this.formConfigurations.pop();

                        if (this.formConfigurations.length === 0) {
                            this.lastEntry = true;
                        }
                        this.isLoaded = true;
                    });

            });
        });
    }

    private addQuestionsAndFormGroup(formEntry: FormEntry, idPrefix: string) {
        formEntry.questions = this.questionService.getQuestionsFromProperties(formEntry.classProperties, idPrefix);
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

    handleTupleSelection(evt: { selection: { id: any, label: any }, formGroup: FormGroup }) {
        // console.log('Dynamic Form Tuple Clicked');
        // console.log(evt);
        // console.log(evt.formGroup);
        // console.log(evt.formGroup.controls);
        // console.log('---------');

        let unableToContinueControl: FormControl;
        let pathPrefix: string;

        Object.keys(evt.formGroup.controls).forEach(c => {
            if (c.endsWith('.unableToContinue')) {
                unableToContinueControl = evt.formGroup.controls[c] as FormControl;
                pathPrefix = c.replace('.unableToContinue', '');
            }
        });

        // console.log(unableToContinueControl);
        // console.log(pathPrefix);
        this.classDefinitionService.getFormConfigurationChunk(this.marketplace, pathPrefix, evt.selection.id).toPromise().then((retFormEntry: FormEntry) => {
            // console.log("Unable to continue Control");
            // console.log(unableToContinueControl);
            // console.log("parentControl");
            // console.log(evt.formGroup);
            // console.log(ret);


            //patch formEntry
            // console.log("find form Entry - location");
            // console.log(pathPrefix);
            const path = pathPrefix.split('.');


            const currentFormEntry = this.getFormEntry(pathPrefix, this.currentFormConfiguration.formEntry.id, this.currentFormConfiguration.formEntry);
            console.log(currentFormEntry);
            const unableToContinueProperty = currentFormEntry.classProperties.find(e => e.id.endsWith('unableToContinue'));
            console.log(unableToContinueProperty);

            const updatedProperties = [unableToContinueProperty];
            updatedProperties.push(...retFormEntry.classProperties);
            retFormEntry.classProperties = updatedProperties;

            console.log(updatedProperties);

            retFormEntry = this.addQuestionsAndFormGroup(retFormEntry, pathPrefix);

            currentFormEntry.classDefinitions = retFormEntry.classDefinitions;
            currentFormEntry.classProperties = retFormEntry.classProperties;
            currentFormEntry.enumRepresentations = retFormEntry.enumRepresentations;
            currentFormEntry.formGroup = retFormEntry.formGroup;
            currentFormEntry.imagePath = retFormEntry.imagePath;
            currentFormEntry.questions = retFormEntry.questions;
            currentFormEntry.subEntries = retFormEntry.subEntries;



            // console.log(this.currentFormConfiguration.formEntry.id);
            // console.log(this.currentFormConfiguration.formEntry.subEntries.find(e => e.id === path[0] + '.' + path[1]));

            // console.log(path);
            // console.log(this.currentFormConfiguration.formEntry.subEntries);

            //TODO patch form Entry and Configuration
        });


    }

    private getFormEntry(pathString: string, currentPath: string, currentFormEntry: FormEntry): FormEntry {

        if (currentPath === pathString) {
            return currentFormEntry;
        }

        currentFormEntry = currentFormEntry.subEntries.find(e => pathString.startsWith(e.id));

        return this.getFormEntry(pathString, currentFormEntry.id, currentFormEntry);


    }



    // handleResultEvent(event: FormEntryReturnEventData) {
    //   const classInstances: ClassInstance[] = [];
    //   this.currentFormConfiguration.formEntry.formGroup.disable();
    //   const propertyInstances: PropertyInstance<any>[] = [];

    //   for (const classProperty of this.currentFormConfiguration.formEntry.classProperties) {
    //     const values = [event.formGroup.value[classProperty.id]];
    //     propertyInstances.push(new PropertyInstance(classProperty, values));
    //   }

    //   for (const enumRepresentation of this.currentFormConfiguration.formEntry.enumRepresentations) {
    //     const values = [event.formGroup.value[enumRepresentation.classDefinition.id]];
    //     const propertyInstance = new PropertyInstance(enumRepresentation.classDefinition.properties[0], values);
    //     propertyInstance.name = enumRepresentation.classDefinition.name;
    //     propertyInstance.id = enumRepresentation.id;
    //     propertyInstances.push(propertyInstance);
    //   }

    //   const classInstance: ClassInstance = new ClassInstance(this.currentFormConfiguration.formEntry.classDefinitions[0], propertyInstances);
    //   classInstance.imagePath = this.currentFormConfiguration.formEntry.imagePath;
    //   classInstances.push(classInstance);

    //   this.classInstanceService.createNewClassInstances(this.marketplace, classInstances).toPromise().then((ret: ClassInstance[]) => {
    //     // handle returned value if necessary
    //     if (!isNullOrUndefined(ret)) {
    //       this.returnedClassInstances.push(...ret);
    //       this.handleNextClick();
    //     }
    //   });
    // }

    handleNextClick() {
        this.canContinue = false;
        if (this.formConfigurations.length > 0) {
            this.currentFormConfiguration = this.formConfigurations.pop();
            if (this.formConfigurations.length === 0) {
                this.lastEntry = true;
            }
        } else {
            this.handleFinishClick();
        }
    }

    handleFinishClick() {
        this.finishClicked = true;
    }

    handleResultEvent(event: FormEntryReturnEventData) {
        this.results.push(event);

        if (this.results.length === this.expectedNumberOfResults) {
            this.createInstanceFromResults();

            setTimeout(() => {
                this.finishClicked = false;
            });
        }
    }


    createInstanceFromResults() {

        const allControls = this.getAllControlsFromResults();
        const classInstance = this.createClassInstances(this.currentFormConfiguration.formEntry, this.currentFormConfiguration.id, allControls);

        this.classInstanceService.createNewClassInstances(this.marketplace, [classInstance]).toPromise().then((ret: ClassInstance[]) => {
            this.resultClassInstance = ret.pop();
            this.contentDiv.nativeElement.scrollTo(0, 0);
            this.showResultPage = true;
        });

    }

    private getAllControlsFromResults() {
        const allControls: { id: string, control: AbstractControl }[] = [];

        for (const result of this.results) {
            const ids = Object.keys(result.formGroup.controls);
            for (const id of ids) {
                allControls.push({ id: id, control: result.formGroup.controls[id] });
            }
        }

        return allControls;
    }

    private createClassInstances(parentEntry: FormEntry, currentPath: string, controls: { id: string, control: AbstractControl }[]) {

        const propertyInstances: PropertyInstance<any>[] = [];
        for (const classProperty of parentEntry.classProperties) {
            console.log(currentPath + '.' + classProperty.id);
            // console.log(controls[0].id);

            const control = controls.find(c => c.id === (currentPath + '.' + classProperty.id));
            console.log(control);
            //make sure numbers are correct
            // console.log(controls);
            // console.log(control);
            // console.log(control.control);
            // console.log(control.control.value);

            let value: any;
            if (classProperty.type === PropertyType.FLOAT_NUMBER) {
                value = Number(control.control.value);
            } else if (classProperty.type === PropertyType.WHOLE_NUMBER) {
                value = Number.parseInt(control.control.value, 10);
            } else {
                value = control.control.value;
            }

            propertyInstances.push(new PropertyInstance(classProperty, [value]));
        }

        const classInstance = new ClassInstance(parentEntry.classDefinitions[0], propertyInstances);
        classInstance.childClassInstances = [];
        classInstance.id = this.objectIdService.getNewObjectId();
        classInstance.marketplaceId = this.marketplace.id;

        if (!isNullOrUndefined(parentEntry.subEntries)) {
            for (const subEntry of parentEntry.subEntries) {
                const subClassInstance = this.createClassInstances(subEntry, subEntry.id, controls);
                classInstance.childClassInstances.push(subClassInstance);
            }
        }

        return classInstance;
    }

    handleCancelEvent() {
        this.navigateBack();
    }

    printAnything(anything: any) {
        console.log(anything);
    }


    navigateBack() {
        window.history.back();
    }

}