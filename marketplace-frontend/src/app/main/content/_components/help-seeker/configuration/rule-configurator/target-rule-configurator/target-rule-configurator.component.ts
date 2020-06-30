import { Component, OnInit, Input, EventEmitter, Output } from "@angular/core";
import { ActivatedRoute } from "@angular/router";

import { isNullOrUndefined } from "util";
import { LoginService } from "../../../../../_service/login.service";
import {
  Participant,
  ParticipantRole,
} from "../../../../../_model/participant";
import { MessageService } from "../../../../../_service/message.service";
import { FormGroup, FormBuilder, FormControl } from "@angular/forms";
import { Marketplace } from "app/main/content/_model/marketplace";
import { MarketplaceService } from "app/main/content/_service/core-marketplace.service";
import {
  AttributeCondition,
  ClassAction,
} from "app/main/content/_model/derivation-rule";
import { CoreHelpSeekerService } from "app/main/content/_service/core-helpseeker.service";
import { ClassDefinition } from "app/main/content/_model/meta/class";
import { ClassDefinitionService } from "app/main/content/_service/meta/core/class/class-definition.service";
import { ClassProperty } from "app/main/content/_model/meta/property";
import { ClassPropertyService } from "app/main/content/_service/meta/core/property/class-property.service";
import { Helpseeker } from "../../../../../_model/helpseeker";

@Component({
  selector: "target-rule-configurator",
  templateUrl: "./target-rule-configurator.component.html",
  styleUrls: ["../rule-configurator.component.scss"],
})
export class TargetRuleConfiguratorComponent implements OnInit {
  @Input("classAction") classAction: ClassAction;
  @Output("classAction") classActionChange: EventEmitter<
    ClassAction
  > = new EventEmitter<ClassAction>();

  helpseeker: Helpseeker;
  marketplace: Marketplace;
  // role: ParticipantRole;
  ruleActionForm: FormGroup;
  classDefinitions: ClassDefinition[] = [];
  classProperties: ClassProperty<any>[] = [];
  
  classDefinitionCache: ClassDefinition[] = [];

  constructor(
    private route: ActivatedRoute,
    private loginService: LoginService,
    private formBuilder: FormBuilder,
    private classDefinitionService: ClassDefinitionService,
    private classPropertyService: ClassPropertyService,
    private helpSeekerService: CoreHelpSeekerService
  ) {
    this.ruleActionForm = formBuilder.group({
      classDefinitionId: new FormControl(undefined)
    });
  }

  ngOnInit() {
    this.ruleActionForm.setValue({
      classDefinitionId:
        (this.classAction.classDefinition
          ? this.classAction.classDefinition.id
          : "") || ""
    });

   
    this.loginService
      .getLoggedIn()
      .toPromise()
      .then((helpseeker: Helpseeker) => {
        this.helpseeker = helpseeker;
        this.helpSeekerService
          .findRegisteredMarketplaces(helpseeker.id)
          .toPromise()
          .then((marketplace: Marketplace) => {
            this.marketplace = marketplace;
            this.classDefinitionService
            .getAllClassDefinitionsWithoutHeadAndEnums(
              marketplace,
              this.helpseeker.tenantId
            )
            .toPromise()
            .then((definitions: ClassDefinition[]) => {
              this.classDefinitions = definitions;
            });
          });
      });
  }

  addTargetAttribute() {
    this.classAction.attributes.push( 
      new AttributeCondition(this.classAction.classDefinition)
    );
  }

  onTargetChange(classDefinition, $event) {
    if ($event.isUserInput){
      if (this.classDefinitions.length > 0) {
        this.classAction.classDefinition = this.classDefinitions.find(
          (cd) => cd.id === this.ruleActionForm.value.classDefinitionId
        );
      }
      this.classAction.classDefinition = classDefinition;
      this.classActionChange.emit(this.classAction);
    }
  }

}
