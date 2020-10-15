import { Component, OnInit, Input, EventEmitter, Output } from "@angular/core";

import { LoginService } from "../../../../../_service/login.service";
import {
  FormGroup,
  FormBuilder,
  FormControl,
  Validators,
  FormGroupDirective,
  ControlContainer,
  FormArray
} from "@angular/forms";
import { Marketplace } from "app/main/content/_model/marketplace";
import {
  AttributeCondition,
  ClassAction
} from "app/main/content/_model/derivation-rule";
import {
  ClassDefinition,
  ClassArchetype
} from "app/main/content/_model/meta/class";
import { ClassDefinitionService } from "app/main/content/_service/meta/core/class/class-definition.service";
import { ClassProperty } from "app/main/content/_model/meta/property/property";
import { User, UserRole } from "app/main/content/_model/user";
import { DerivationRuleValidators } from "app/main/content/_validator/derivation-rule.validators";
import { GlobalInfo } from "app/main/content/_model/global-info";
import { Tenant } from "app/main/content/_model/tenant";
import { ClassPropertyService } from "app/main/content/_service/meta/core/property/class-property.service";
import { isNullOrUndefined } from "util";

@Component({
  selector: "target-rule-configurator",
  templateUrl: "./target-rule-configurator.component.html",
  styleUrls: ["../rule-configurator.component.scss"],
  viewProviders: [
    { provide: ControlContainer, useExisting: FormGroupDirective }
  ]
})
export class TargetRuleConfiguratorComponent implements OnInit {
  @Input("classAction") classAction: ClassAction;
  @Output("classActionChange") classActionChange: EventEmitter<
    ClassAction
  > = new EventEmitter<ClassAction>();

  tenantAdmin: User;
  marketplace: Marketplace;
  tenant: Tenant;
  classDefinitions: ClassDefinition[] = [];
  classProperties: ClassProperty<any>[] = [];
  initialized: boolean = false;

  ruleActionForm: FormGroup;

  classDefinitionCache: ClassDefinition[] = [];

  targetValidationMessages = DerivationRuleValidators.ruleValidationMessages;

  constructor(
    private loginService: LoginService,
    private formBuilder: FormBuilder,
    private classDefinitionService: ClassDefinitionService,
    private classPropertyService: ClassPropertyService,
    private parent: FormGroupDirective
  ) {
    //this.actionForm = this.parent.form;
    this.ruleActionForm = this.formBuilder.group({
      classDefinitionId: new FormControl(undefined, [Validators.required]),
      targetAttributes: new FormArray([])
    });
  }

  async ngOnInit() {
    this.ruleActionForm.patchValue({
      classDefinitionId: this.classAction.classDefinition
        ? this.classAction.classDefinition.id
        : ""
    });

    this.parent.form.addControl("ruleActionForm", this.ruleActionForm);
    /*
    let attributes = <FormArray>this.ruleActionForm.controls['targetAttributes'];  

     // console.log("classDefinitionId: " + classDefinitionId);
      let control = <FormArray>this.ruleActionForm.controls['attributes'];  
      console.log(" control from form --> " + control);
      // this.ruleActionForm.value.attributes.controls = [];
      console.log("attributes array: " + attributes.controls);*/

    const globalInfo = <GlobalInfo>(
      await this.loginService.getGlobalInfo().toPromise()
    );
    this.marketplace = globalInfo.marketplace;
    this.tenantAdmin = globalInfo.user;
    this.tenant = globalInfo.tenants[0];

    this.classDefinitionService
      .getAllClassDefinitions(this.marketplace, this.tenant.id)
      .toPromise()
      .then((definitions: ClassDefinition[]) => {
        this.classDefinitions = definitions;
      });

    this.initialized = true;
  }

  addTargetAttribute() {
    this.classAction.attributes.push(
      new AttributeCondition(this.classAction.classDefinition)
    );
    this.classActionChange.emit(this.classAction);
  }

  onChangeTargetAttribute($event) {
    if ($event.isUserInput) {
      this.classActionChange.emit(this.classAction);
    }
  }

  onTargetChange(classDefinition, $event) {
    if ($event.isUserInput) {
      if (
        isNullOrUndefined(this.classAction.classDefinition) ||
        this.classAction.classDefinition.id != classDefinition.id
      ) {
        /*this.classAction.classDefinition = this.classDefinitions.find(
          (cd) => cd.id === this.parent.form.get('ruleActionForm').get('classDefinitionId').value
        );*/
        this.classAction.classDefinition = classDefinition;
        this.classAction.attributes = new Array();
        this.loadClassProperties(classDefinition);
        this.classActionChange.emit(this.classAction);
      }
    }
  }

  private loadClassProperties(classDefinition: ClassDefinition) {
    this.classPropertyService
      .getAllClassPropertiesFromClass(this.marketplace, classDefinition.id)
      .toPromise()
      .then((props: ClassProperty<any>[]) => {
        this.classProperties = props;
      });
  }

  private retrieveClassType(classArchetype: ClassArchetype) {
    switch (classArchetype) {
      case ClassArchetype.COMPETENCE: {
        return "(Kompetenz)";
      }
      case ClassArchetype.ACHIEVEMENT: {
        return "(Verdienst)";
      }
      case ClassArchetype.FUNCTION: {
        return "(Funktion)";
      }
      case ClassArchetype.TASK: {
        return "(Tätigkeit)";
      }
      default: {
        return "";
      }
    }
  }
}
