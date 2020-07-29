import { Component, OnInit, ViewChild, ElementRef } from "@angular/core";
import { QuestionService } from "app/main/content/_service/question.service";
import { QuestionControlService } from "app/main/content/_service/question-control.service";
import { Marketplace } from "app/main/content/_model/marketplace";
import {
  FormConfiguration,
  FormEntryReturnEventData,
  FormEntry,
} from "app/main/content/_model/meta/form";
import { ClassInstance } from "app/main/content/_model/meta/class";
import { Router, ActivatedRoute } from "@angular/router";
import { MarketplaceService } from "app/main/content/_service/core-marketplace.service";
import { ClassDefinitionService } from "app/main/content/_service/meta/core/class/class-definition.service";
import { ClassInstanceService } from "app/main/content/_service/meta/core/class/class-instance.service";
import { ObjectIdService } from "app/main/content/_service/objectid.service.";
import { AbstractControl, FormGroup, FormControl } from "@angular/forms";
import {
  PropertyInstance,
  PropertyType,
} from "app/main/content/_model/meta/property";
import { isNullOrUndefined } from "util";
import { LoginService } from "app/main/content/_service/login.service";
import { User, UserRole } from "app/main/content/_model/user";
import { GlobalInfo } from "app/main/content/_model/global-info";
import { Tenant } from "app/main/content/_model/tenant";

@Component({
  selector: "app-class-instance-form-editor",
  templateUrl: "./class-instance-form-editor.component.html",
  styleUrls: ["./class-instance-form-editor.component.scss"],
  providers: [QuestionService, QuestionControlService],
})
export class ClassInstanceFormEditorComponent implements OnInit {
  marketplace: Marketplace;
  tenantAdmin: User;
  tenant: Tenant;

  formConfigurations: FormConfiguration[];
  currentFormConfiguration: FormConfiguration;
  selectedVolunteers: User[];

  returnedClassInstances: ClassInstance[];

  loaded = false;
  finishClicked = false;
  showResultPage = false;

  expectedNumberOfResults: number;
  results: FormEntryReturnEventData[];

  @ViewChild("contentDiv", { static: false }) contentDiv: ElementRef;
  resultClassInstance: ClassInstance;

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private loginService: LoginService,
    private marketplaceService: MarketplaceService,
    private classDefinitionService: ClassDefinitionService,
    private classInstanceService: ClassInstanceService,
    private questionService: QuestionService,
    private questionControlService: QuestionControlService,
    private objectIdService: ObjectIdService
  ) {}

  async ngOnInit() {
    let globalInfo = <GlobalInfo>(
      await this.loginService.getGlobalInfo().toPromise()
    );
    this.tenantAdmin = globalInfo.user;
    this.tenant = globalInfo.tenants[0];
    this.marketplace = globalInfo.marketplace;

    let marketplaceId: string;
    const childClassIds: string[] = [];

    this.returnedClassInstances = [];
    this.results = [];
    this.expectedNumberOfResults = 0;

    Promise.all([
      this.route.params.subscribe((params) => {
        marketplaceId = params["marketplaceId"];
      }),
      this.route.queryParams.subscribe((queryParams) => {
        let i = 0;
        while (!isNullOrUndefined(queryParams[i])) {
          childClassIds.push(queryParams[i]);
          i++;
        }
      }),
    ]).then(() => {
      this.marketplaceService
        .findById(marketplaceId)
        .toPromise()
        .then((marketplace: Marketplace) => {
          this.marketplace = marketplace;

          Promise.all([
            this.classDefinitionService
              .getFormConfigurations(this.marketplace, childClassIds)
              .toPromise()
              .then((formConfigurations: FormConfiguration[]) => {
                this.formConfigurations = formConfigurations;
                for (const config of this.formConfigurations) {
                  config.formEntry = this.addQuestionsAndFormGroup(
                    config.formEntry,
                    config.formEntry.id
                  );
                }
              }),

            this.loginService
              .getLoggedIn()
              .toPromise()
              .then((tenantAdmin: User) => {
                this.tenantAdmin = tenantAdmin;
              }),
          ]).then(() => {
            this.currentFormConfiguration = this.formConfigurations.pop();
            this.loaded = true;
          });
        });
    });
  }

  private addQuestionsAndFormGroup(formEntry: FormEntry, idPrefix: string) {
    // formEntry.questions = this.questionService.getQuestionsFromProperties(
    //   formEntry.classProperties,
    //   idPrefix
    // );
    formEntry.questions = this.questionService.getQuestionsFromProperties(
      formEntry.classProperties,
      idPrefix
    );
    formEntry.formGroup = this.questionControlService.toFormGroup(
      formEntry.questions
    );

    if (
      !isNullOrUndefined(formEntry.questions) &&
      formEntry.questions.length > 0
    ) {
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

  handleTupleSelection(evt: {
    selection: { id: any; label: any };
    formGroup: FormGroup;
  }) {
    let unableToContinueControl: FormControl;
    let unableToContinueControlKey: string;
    let pathPrefix: string;

    Object.keys(evt.formGroup.controls).forEach((c) => {
      if (c.endsWith("unableToContinue")) {
        unableToContinueControlKey = c;
        unableToContinueControl = evt.formGroup.controls[c] as FormControl;
        pathPrefix = c.replace(/\.[^.]*unableToContinue/, "");
      }
    });

    this.classDefinitionService
      .getFormConfigurationChunk(this.marketplace, pathPrefix, evt.selection.id)
      .toPromise()
      .then((retFormEntry: FormEntry) => {
        const currentFormEntry = this.getFormEntry(
          pathPrefix,
          this.currentFormConfiguration.formEntry.id,
          this.currentFormConfiguration.formEntry
        );

        retFormEntry = this.addQuestionsAndFormGroup(retFormEntry, pathPrefix);

        currentFormEntry.classDefinitions = retFormEntry.classDefinitions;
        currentFormEntry.classProperties = retFormEntry.classProperties;
        currentFormEntry.formGroup = retFormEntry.formGroup;
        currentFormEntry.imagePath = retFormEntry.imagePath;
        currentFormEntry.questions = retFormEntry.questions;
        currentFormEntry.subEntries = retFormEntry.subEntries;

        this.expectedNumberOfResults = 0;
        this.calculateExpectedResults(this.currentFormConfiguration.formEntry);
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

    currentFormEntry = currentFormEntry.subEntries.find((e) =>
      pathString.startsWith(e.id)
    );
    return this.getFormEntry(pathString, currentFormEntry.id, currentFormEntry);
  }

  private calculateExpectedResults(formEntry: FormEntry) {
    this.expectedNumberOfResults++;
    for (const subFormEntry of formEntry.subEntries) {
      this.calculateExpectedResults(subFormEntry);
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
    } else {
      this.finishClicked = false;
    }
  }

  private createInstanceFromResults() {
    const allControls = this.getAllControlsFromResults();
    const classInstances: ClassInstance[] = [];

    if (isNullOrUndefined(this.selectedVolunteers)) {
      const classInstance = this.createClassInstance(
        this.currentFormConfiguration.formEntry,
        this.currentFormConfiguration.id,
        allControls
      );
      classInstance.tenantId = this.tenant.id;
      classInstance.issuerId = this.tenant.id;
      classInstances.push(classInstance);
    } else {
      for (const volunteer of this.selectedVolunteers) {
        const classInstance = this.createClassInstance(
          this.currentFormConfiguration.formEntry,
          this.currentFormConfiguration.id,
          allControls
        );
        classInstance.tenantId = this.tenant.id;
        classInstance.issuerId = this.tenant.id;
        classInstance.userId = volunteer.id;
        classInstances.push(classInstance);
      }
    }

    this.classInstanceService
      .createNewClassInstances(this.marketplace, classInstances)
      .toPromise()
      .then((ret: ClassInstance[]) => {
        this.resultClassInstance = ret.pop();
        this.contentDiv.nativeElement.scrollTo(0, 0);
        this.showResultPage = true;
      });
  }

  private getAllControlsFromResults() {
    const allControls: { id: string; control: AbstractControl }[] = [];

    for (const result of this.results) {
      const ids = Object.keys(result.formGroup.controls);
      for (const id of ids) {
        allControls.push({ id: id, control: result.formGroup.controls[id] });
      }
    }

    return allControls;
  }

  private createClassInstance(
    parentEntry: FormEntry,
    currentPath: string,
    controls: { id: string; control: AbstractControl }[]
  ) {
    const propertyInstances: PropertyInstance<any>[] = [];
    for (const classProperty of parentEntry.classProperties) {
      // skip "unableToContinue" Properties
      if (classProperty.id.endsWith("unableToContinue")) {
        continue;
      }

      const control = controls.find(
        (c) => c.id === currentPath + "." + classProperty.id
      );

      let value: any;
      if (classProperty.type === PropertyType.FLOAT_NUMBER) {
        value = Number(control.control.value);
      } else if (classProperty.type === PropertyType.WHOLE_NUMBER) {
        value = Number.parseInt(control.control.value, 10);
      } else {
        value = control.control.value;
      }
      const l = propertyInstances.push(
        new PropertyInstance(classProperty, [value])
      );

      // if (classProperty.type === PropertyType.ENUM) {
      //   let i = propertyInstances[l - 1].allowedValues.findIndex(a => a.id === value.id);
      //   let currentLevel = value.level;
      //   for (i; i >= 0; i--) {
      //     if (propertyInstances[l - 1].allowedValues[i].level < currentLevel) {
      //       propertyInstances[l - 1].values.push(propertyInstances[l - 1].allowedValues[i]);
      //       currentLevel--;
      //     }
      //   }
      // }

      console.log(propertyInstances[l - 1]);
    }

    const classInstance = new ClassInstance(
      parentEntry.classDefinitions[0],
      propertyInstances
    );
    classInstance.childClassInstances = [];
    classInstance.id = this.objectIdService.getNewObjectId();
    classInstance.marketplaceId = this.marketplace.id;

    if (!isNullOrUndefined(parentEntry.subEntries)) {
      for (const subEntry of parentEntry.subEntries) {
        const subClassInstance = this.createClassInstance(
          subEntry,
          subEntry.id,
          controls
        );
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
