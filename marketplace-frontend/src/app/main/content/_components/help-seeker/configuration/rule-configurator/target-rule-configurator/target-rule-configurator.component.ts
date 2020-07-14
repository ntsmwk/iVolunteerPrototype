import { Component, OnInit, Input, EventEmitter, Output } from "@angular/core";

import { LoginService } from "../../../../../_service/login.service";

import { FormGroup, FormBuilder, FormControl } from "@angular/forms";
import { Marketplace } from "app/main/content/_model/marketplace";
import {
  AttributeCondition,
  ClassAction,
} from "app/main/content/_model/derivation-rule";
import {
  ClassDefinition,
  ClassArchetype,
} from "app/main/content/_model/meta/class";
import { ClassDefinitionService } from "app/main/content/_service/meta/core/class/class-definition.service";
import { ClassProperty } from "app/main/content/_model/meta/property";
import { User, UserRole } from "app/main/content/_model/user";
import { GlobalInfo } from "app/main/content/_model/global-info";

@Component({
  selector: "target-rule-configurator",
  templateUrl: "./target-rule-configurator.component.html",
  styleUrls: ["../rule-configurator.component.scss"],
})
export class TargetRuleConfiguratorComponent implements OnInit {
  @Input("classAction") classAction: ClassAction;
  @Output("classActionChange") classActionChange: EventEmitter<
    ClassAction
  > = new EventEmitter<ClassAction>();

  helpseeker: User;
  marketplace: Marketplace;
  role: UserRole;
  ruleActionForm: FormGroup;
  classDefinitions: ClassDefinition[] = [];
  classProperties: ClassProperty<any>[] = [];
  initialized: boolean = false;

  classDefinitionCache: ClassDefinition[] = [];

  constructor(
    private loginService: LoginService,
    private formBuilder: FormBuilder,
    private classDefinitionService: ClassDefinitionService,
  ) {
    this.ruleActionForm = formBuilder.group({
      classDefinitionId: new FormControl(undefined),
    });
  }

  async ngOnInit() {
    this.ruleActionForm.setValue({
      classDefinitionId:
        (this.classAction.classDefinition
          ? this.classAction.classDefinition.id
          : "") || "",
    });

    const globalInfo = <GlobalInfo>(
      await this.loginService.getGlobalInfo().toPromise()
    );
    this.marketplace = globalInfo.marketplace;
    this.helpseeker = globalInfo.user;

    this.classDefinitionService
      .getAllClassDefinitionsWithoutHeadAndEnums(
        this.marketplace,
        this.helpseeker.subscribedTenants.find(
          (t) => t.role === UserRole.HELP_SEEKER
        ).tenantId
      )
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
  }

  onTargetChange(classDefinition, $event) {
    if ($event.isUserInput) {
      if (
        this.classDefinitions.length > 0 &&
        (!this.classAction.classDefinition ||
          (this.classAction.classDefinition &&
            this.classAction.classDefinition.id != classDefinition.id))
      ) {
        this.classAction.classDefinition = this.classDefinitions.find(
          (cd) => cd.id === this.ruleActionForm.value.classDefinitionId
        );
        this.classAction.classDefinition = classDefinition;
        this.classAction.attributes = new Array();
        this.classActionChange.emit(this.classAction);
      }
    }
  }

  private retrieveClassType(classArchetype: ClassArchetype) {
    switch (classArchetype) {
      case ClassArchetype.COMPETENCE: {
        return "Kompetenz";
      }
      case ClassArchetype.ACHIEVEMENT: {
        return "Verdienst";
      }
      case ClassArchetype.FUNCTION: {
        return "Funktion";
      }
      case ClassArchetype.TASK: {
        return "Tätigkeit";
      }
      default: {
        return "";
      }
    }
  }
}
