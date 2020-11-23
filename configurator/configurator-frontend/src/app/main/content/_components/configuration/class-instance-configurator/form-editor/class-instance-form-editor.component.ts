import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { DynamicFormItemService } from 'app/main/content/_service/dynamic-form-item.service';
import { DynamicFormItemControlService } from 'app/main/content/_service/dynamic-form-item-control.service';
import { FormConfiguration, FormEntryReturnEventData, FormEntry } from 'app/main/content/_model/configurator/form';
import { ClassInstance } from 'app/main/content/_model/configurator/class';
import { Router, ActivatedRoute } from '@angular/router';
import { ClassDefinitionService } from 'app/main/content/_service/meta/core/class/class-definition.service';
import { ClassInstanceService } from 'app/main/content/_service/meta/core/class/class-instance.service';
import { ObjectIdService } from 'app/main/content/_service/objectid.service.';
import { FormGroup, FormControl } from '@angular/forms';
import { PropertyInstance, PropertyType, ClassProperty } from 'app/main/content/_model/configurator/property/property';
import { isNullOrUndefined } from 'util';
import { ResponseService } from 'app/main/content/_service/response.service';

@Component({
  selector: "app-class-instance-form-editor",
  templateUrl: './class-instance-form-editor.component.html',
  styleUrls: ['./class-instance-form-editor.component.scss'],
  providers: [DynamicFormItemService, DynamicFormItemControlService]
})
export class ClassInstanceFormEditorComponent implements OnInit {
  formConfigurations: FormConfiguration[];
  currentFormConfiguration: FormConfiguration;

  returnedClassInstances: ClassInstance[];

  loaded = false;
  finishClicked = false;
  showResultPage = false;

  canSubmitForm = true;
  errorOccurredInForm = false;

  @ViewChild('contentDiv', { static: false }) contentDiv: ElementRef;
  resultClassInstance: ClassInstance;

  tenantId: string;
  redirectUrl: string;

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private classDefinitionService: ClassDefinitionService,
    private classInstanceService: ClassInstanceService,
    private formItemService: DynamicFormItemService,
    private formItemControlService: DynamicFormItemControlService,
    private objectIdService: ObjectIdService,
  ) { }

  async ngOnInit() {

    const childClassIds: string[] = [];
    this.returnedClassInstances = [];

    this.route.queryParams.subscribe(params => {
      if (isNullOrUndefined(params['tenantId']) || isNullOrUndefined(params['redirect'] || isNullOrUndefined(params['cdId']))) {
        this.router.navigate(['main/invalid-parameters']);
      } else {
        this.tenantId = params['tenantId'];
        this.redirectUrl = params['redirect'];
        childClassIds.push(params['cdId']);
      }
    });

    // Promise.all([
    //   this.route.queryParams.subscribe(queryParams => {
    //     let i = 0;
    //     while (!isNullOrUndefined(queryParams[i])) {
    //       childClassIds.push(queryParams[i]);
    //       i++;
    //     }
    //   })
    // ]).then(() => {
    this.classDefinitionService
      .getFormConfigurations(childClassIds)
      .toPromise().then((formConfigurations: FormConfiguration[]) => {
        this.formConfigurations = formConfigurations;

        for (const config of this.formConfigurations) {
          config.formEntry = this.addFormItemsAndFormGroup(config.formEntry, config.formEntry.id);
        }
      }).then(() => {
        this.currentFormConfiguration = this.formConfigurations.pop();
        this.loaded = true;
      });
    // });
  }

  private addFormItemsAndFormGroup(formEntry: FormEntry, idPrefix: string) {
    formEntry.formItems = this.formItemService.getFormItemsFromProperties(
      formEntry.classProperties,
      idPrefix
    );
    formEntry.formGroup = this.formItemControlService.toFormGroup(
      formEntry.formItems
    );

    if (!isNullOrUndefined(formEntry.subEntries)) {
      for (let subEntry of formEntry.subEntries) {
        const newIdPrefix = subEntry.id;
        subEntry = this.addFormItemsAndFormGroup(subEntry, newIdPrefix);
      }
    }
    return formEntry;
  }

  handleTupleSelection(evt: {
    selection: { id: any; label: any };
    formGroup: FormGroup;
  }) {
    let unableToContinueControl: FormControl;
    let unableToContinueControlKey: string;
    let pathPrefix: string;

    Object.keys(evt.formGroup.controls).forEach(c => {
      if (c.endsWith('unableToContinue')) {
        unableToContinueControlKey = c;
        unableToContinueControl = evt.formGroup.controls[c] as FormControl;
        pathPrefix = c.replace(/\.[^.]*unableToContinue/, '');
      }
    });

    this.classDefinitionService
      .getFormConfigurationChunk(pathPrefix, evt.selection.id).toPromise().then((retFormEntry: FormEntry) => {
        const currentFormEntry = this.getFormEntry(
          pathPrefix,
          this.currentFormConfiguration.formEntry.id,
          this.currentFormConfiguration.formEntry
        );

        retFormEntry = this.addFormItemsAndFormGroup(retFormEntry, pathPrefix);

        currentFormEntry.classDefinitions = retFormEntry.classDefinitions;
        currentFormEntry.classProperties = retFormEntry.classProperties;
        currentFormEntry.formGroup = retFormEntry.formGroup;
        currentFormEntry.imagePath = retFormEntry.imagePath;
        currentFormEntry.formItems = retFormEntry.formItems;
        currentFormEntry.subEntries = retFormEntry.subEntries;
      });
  }

  private getFormEntry(
    pathString: string,
    currentPath: string,
    currentFormEntry: FormEntry
  ): FormEntry {
    if (currentPath === pathString) {
      return currentFormEntry;
    }

    currentFormEntry = currentFormEntry.subEntries.find(e =>
      pathString.startsWith(e.id)
    );
    return this.getFormEntry(pathString, currentFormEntry.id, currentFormEntry);
  }

  handleFinishClick() {
    this.canSubmitForm = false;
    this.errorOccurredInForm = false;
    setTimeout(() => {
      if (!this.canSubmitForm) {
        this.finishClicked = true;
        this.canSubmitForm = true;
      }
    }, 200);
  }

  handleErrorEvent(event: boolean) {
    this.errorOccurredInForm = this.errorOccurredInForm || event;
    this.canSubmitForm = true;
    this.finishClicked = false;

  }

  handleResultEvent(event: FormEntryReturnEventData) {
    setTimeout(() => {
      if (!this.errorOccurredInForm) {
        this.createInstanceFromResults(event);
      }
      this.finishClicked = false;
    });
  }

  private createInstanceFromResults(result: FormEntryReturnEventData) {
    const classInstances: ClassInstance[] = [];
    // const tenantId = this.tenantAdmin.subscribedTenants.find(
    //   t => t.role === UserRole.TENANT_ADMIN
    // ).tenantId;

    // if (isNullOrUndefined(this.selectedVolunteers)) {
    const classInstance = this.createClassInstance(
      this.currentFormConfiguration.formEntry,
      result.value[this.currentFormConfiguration.formEntry.id]
    );
    classInstance.tenantId = this.tenantId;
    classInstance.issuerId = this.tenantId;
    classInstances.push(classInstance);
    // } else {
    //   for (const volunteer of this.selectedVolunteers) {
    //     const classInstance = this.createClassInstance(
    //       this.currentFormConfiguration.formEntry,
    //       result.value[this.currentFormConfiguration.formEntry.id]
    //     );
    //     classInstance.tenantId = this.tenantId;
    //     classInstance.issuerId = this.tenantId;
    //     classInstance.userId = volunteer.id;

    //     classInstances.push(classInstance);
    //   }
    // }

    this.classInstanceService.createNewClassInstances(classInstances, this.redirectUrl).then((ret: ClassInstance) => {
      this.resultClassInstance = ret;
      this.contentDiv.nativeElement.scrollTo(0, 0);
      this.showResultPage = true;
    });
  }

  private createClassInstance(
    formEntry: FormEntry,
    result: any[],
    forceAddProperties?: boolean,
    resultIndex?: number
  ) {
    if (isNullOrUndefined(resultIndex)) {
      resultIndex = 0;
    }

    const keys = Object.keys(result[resultIndex]);

    let propertyInstances = [];
    if (forceAddProperties || !formEntry.multipleAllowed) {
      propertyInstances = this.addPropertyInstances(
        formEntry.classProperties,
        result,
        keys
      );
    }

    const classInstance = new ClassInstance(
      formEntry.classDefinitions[0],
      propertyInstances
    );
    classInstance.childClassInstances = [];
    classInstance.id = this.objectIdService.getNewObjectId();

    if (formEntry.multipleAllowed) {
      if (!forceAddProperties) {
        for (let i = 0; i < result.length; i++) {
          const childClassInstance = this.createClassInstance(
            formEntry,
            result,
            true,
            i
          );
          classInstance.childClassInstances.push(childClassInstance);
        }
      }
    }

    if (!isNullOrUndefined(formEntry.subEntries)) {
      for (const subEntry of formEntry.subEntries) {
        const next = this.findSubEntryResult(subEntry.id, result);
        const subClassInstance = this.createClassInstance(
          subEntry,
          next[subEntry.id]
        );
        classInstance.childClassInstances.push(subClassInstance);
      }
    }
    return classInstance;
  }

  private addPropertyInstances(
    classProperties: ClassProperty<any>[],
    values: any[],
    keys: string[]
  ) {
    const propertyInstances: PropertyInstance<any>[] = [];
    for (const classProperty of classProperties) {
      // skip "unableToContinue" Properties
      if (classProperty.id.endsWith('unableToContinue')) {
        continue;
      }

      let value = values[0][keys.find(k => k.endsWith(classProperty.id))];

      if (!classProperty.computed && classProperty.type === PropertyType.FLOAT_NUMBER) {
        value = Number(value);
      } else if (!classProperty.computed && classProperty.type === PropertyType.WHOLE_NUMBER) {
        value = Number.parseInt(value, 10);
      }
      const l = propertyInstances.push(
        new PropertyInstance(classProperty, [value])
      );

    }
    return propertyInstances;
  }

  private findSubEntryResult(subEntryId: string, result: any[]) {
    return result.find(r => Object.keys(r)[0] === subEntryId);
  }

  handleCancelEvent() {

    let returnParam: string;
    this.route.queryParams.subscribe(params => {
      returnParam = params['returnTo'];
    });
    if (!isNullOrUndefined(returnParam)) {
      if (returnParam === 'classConfigurator') {
        this.router.navigate([`main/class-configurator`], { queryParams: { ccId: this.currentFormConfiguration.formEntry.classDefinitions[0].configurationId } });
      }
    } else {
      history.back();
    }
  }

  printAnything(anything: any) {
    console.log(anything);
  }

  printDebug() {
    console.log(this.currentFormConfiguration);
    console.log(this.currentFormConfiguration.formEntry.formGroup);
    console.log(this.currentFormConfiguration.formEntry.formItems);
    console.log(this.currentFormConfiguration.formEntry.subEntries);
  }

}
