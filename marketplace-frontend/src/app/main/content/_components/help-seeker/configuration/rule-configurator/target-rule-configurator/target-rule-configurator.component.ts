import { Component, OnInit, Input, EventEmitter, Output } from "@angular/core";
import { ActivatedRoute } from "@angular/router";

import { isNullOrUndefined } from "util";
import { LoginService } from "../../../../../_service/login.service";

import { MessageService } from "../../../../../_service/message.service";
import { FormGroup, FormBuilder, FormControl, Validators, FormGroupDirective, ControlContainer, FormArray } from "@angular/forms";
import { Marketplace } from "app/main/content/_model/marketplace";
import { MarketplaceService } from "app/main/content/_service/core-marketplace.service";
import {
  AttributeCondition,
  ClassAction,
} from "app/main/content/_model/derivation-rule";
import { CoreHelpSeekerService } from "app/main/content/_service/core-helpseeker.service";
import { ClassDefinition, ClassArchetype } from "app/main/content/_model/meta/class";
import { ClassDefinitionService } from "app/main/content/_service/meta/core/class/class-definition.service";
import { ClassProperty } from "app/main/content/_model/meta/property";
import { ClassPropertyService } from "app/main/content/_service/meta/core/property/class-property.service";
import { User, UserRole } from "app/main/content/_model/user";
import { DerivationRuleValidators } from 'app/main/content/_validator/derivation-rule.validators';

@Component({
  selector: "target-rule-configurator",
  templateUrl: "./target-rule-configurator.component.html",
  styleUrls: ["../rule-configurator.component.scss"],
  viewProviders: [{ provide: ControlContainer, useExisting: FormGroupDirective }]
})
export class TargetRuleConfiguratorComponent implements OnInit {
  @Input("classAction") classAction: ClassAction;
  @Output("classActionChange") classActionChange: EventEmitter<
    ClassAction
  > = new EventEmitter<ClassAction>();

  helpseeker: User;
  marketplace: Marketplace;
  role: UserRole;
  classDefinitions: ClassDefinition[] = [];
  classProperties: ClassProperty<any>[] = [];
  initialized: boolean = false;

  ruleActionForm: FormGroup;

  classDefinitionCache: ClassDefinition[] = [];

  targetValidationMessages = DerivationRuleValidators.ruleValidationMessages;

  constructor(
    private route: ActivatedRoute,
    private loginService: LoginService,
    private formBuilder: FormBuilder,
    private classDefinitionService: ClassDefinitionService,
    private classPropertyService: ClassPropertyService,
    private helpSeekerService: CoreHelpSeekerService,
    private parent: FormGroupDirective
  ) {
    //this.actionForm = this.parent.form;
    this.ruleActionForm = this.formBuilder.group({
      classDefinitionId: new FormControl(undefined, [Validators.required]),
      targetAttributes: new FormArray([]) 
    });
  }

  ngOnInit() {
    this.ruleActionForm.patchValue({
      classDefinitionId: 
      this.classAction.classDefinition
      ? this.classAction.classDefinition.id
      : "",

    });

    this.parent.form.addControl('ruleActionForm', this.ruleActionForm);
    /*
    let attributes = <FormArray>this.ruleActionForm.controls['targetAttributes'];  

     // console.log("classDefinitionId: " + classDefinitionId);
      let control = <FormArray>this.ruleActionForm.controls['attributes'];  
      console.log(" control from form --> " + control);
      // this.ruleActionForm.value.attributes.controls = [];
      console.log("attributes array: " + attributes.controls);*/

   this.loginService
      .getLoggedIn()
      .toPromise()
      .then((helpseeker: User) => {
        this.helpseeker = helpseeker;
        this.helpSeekerService
          .findRegisteredMarketplaces(helpseeker.id)
          .toPromise()
          .then((marketplace: Marketplace) => {
            this.marketplace = marketplace;
            this.classDefinitionService
              .getAllClassDefinitionsWithoutHeadAndEnums(
                marketplace,
                this.helpseeker.subscribedTenants.find(
                  (t) => t.role === UserRole.HELP_SEEKER
                ).tenantId
              )
              .toPromise()
              .then((definitions: ClassDefinition[]) => {
                this.classDefinitions = definitions;
              });
          });
      });
      this.initialized = true;
  }

  addTargetAttribute() {
    this.classAction.attributes.push(
      new AttributeCondition(this.classAction.classDefinition)
    );
    this.classActionChange.emit(this.classAction);
  }

  onChangeTargetAttribute($event){
    if ($event.isUserInput){
      this.classActionChange.emit(this.classAction);
    }
  }

  onTargetChange(classDefinition, $event) {
    if ($event.isUserInput) {
      if (this.classDefinitions.length > 0 && 
          (!this.classAction.classDefinition || 
          (this.classAction.classDefinition &&
           this.classAction.classDefinition.id != classDefinition.id))) {
        /*this.classAction.classDefinition = this.classDefinitions.find(
          (cd) => cd.id === this.parent.form.get('ruleActionForm').get('classDefinitionId').value
        );*/
        this.classAction.classDefinition = classDefinition;
        this.classAction.attributes = new Array();
        this.classActionChange.emit(this.classAction);
      }
    }
  }

  private retrieveClassType(classArchetype: ClassArchetype){
    switch(classArchetype) { 
      case ClassArchetype.COMPETENCE: { 
         return "Kompetenz"; 
      } 
      case ClassArchetype.ACHIEVEMENT: { 
         return "Verdienst"
      } 
      case ClassArchetype.FUNCTION: { 
        return "Funktion"
     } 
     case ClassArchetype.TASK: { 
        return "Tätigkeit"
    } 
      default: { 
         return ""; 
      } 
   } 
  }
}
