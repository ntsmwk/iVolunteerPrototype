import {
  Component,
  OnInit,
  Input,
  EventEmitter,
  Output,
  SimpleChanges,
} from "@angular/core";
import { ActivatedRoute } from "@angular/router";

import { isNullOrUndefined } from "util";
import { LoginService } from "../../../../../_service/login.service";
import { User, UserRole } from "../../../../../_model/user";
import { MessageService } from "../../../../../_service/message.service";
import { FormGroup, FormBuilder, FormControl } from "@angular/forms";
import { Marketplace } from "app/main/content/_model/marketplace";
import { MarketplaceService } from "app/main/content/_service/core-marketplace.service";
import {
  AttributeCondition,
  ClassCondition,
  AggregationOperatorType,
} from "app/main/content/_model/derivation-rule";
import { CoreHelpSeekerService } from "app/main/content/_service/core-helpseeker.service";
import { ClassDefinition } from "app/main/content/_model/meta/class";
import { ClassDefinitionService } from "app/main/content/_service/meta/core/class/class-definition.service";
import { ClassProperty } from "app/main/content/_model/meta/property";
import { ClassPropertyService } from "app/main/content/_service/meta/core/property/class-property.service";

@Component({
  selector: "class-rule-precondition",
  templateUrl: "./class-rule-configurator-precondition.component.html",
  styleUrls: ["../rule-configurator.component.scss"],
})
export class FuseClassRulePreconditionConfiguratorComponent implements OnInit {
  @Input("classCondition") classCondition: ClassCondition;
  @Output("classConditionChange") classConditionChange: EventEmitter<
    ClassCondition
  > = new EventEmitter<ClassCondition>();

  helpseeker: User;
  marketplace: Marketplace;
  role: UserRole;
  rulePreconditionForm: FormGroup;
  classDefinitions: ClassDefinition[] = [];
  attributes: AttributeCondition[] = [];
  aggregationOperators: any;

  classDefinitionCache: ClassDefinition[] = [];

  constructor(
    private route: ActivatedRoute,
    private loginService: LoginService,
    private formBuilder: FormBuilder,
    private classDefinitionService: ClassDefinitionService,
    private classPropertyService: ClassPropertyService,
    private helpSeekerService: CoreHelpSeekerService
  ) {
    this.rulePreconditionForm = formBuilder.group({
      classDefinitionId: new FormControl(undefined),
      aggregationOperatorType: new FormControl(undefined),
      value: new FormControl(undefined),
    });
  }

  ngOnInit() {
    this.rulePreconditionForm.setValue({
      classDefinitionId:
        (this.classCondition.classDefinition
          ? this.classCondition.classDefinition.id
          : "") || "",
      aggregationOperatorType:
        this.classCondition.aggregationOperatorType || "",
      value: this.classCondition.value || "",
    });

    this.classCondition.attributeConditions = this.classCondition
      .attributeConditions
      ? this.classCondition.attributeConditions
      : new Array();
    this.aggregationOperators = Object.keys(AggregationOperatorType);

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
                ).tenant.id
              )
              .toPromise()
              .then((definitions: ClassDefinition[]) => {
                this.classDefinitions = definitions;
              });
          });
      });
  }

  /*ngOnChanges(changes: SimpleChanges) {
    console.log('in child changes with: ', changes);
  }*/

  onClassChange(classDefinition: ClassDefinition, $event) {
    if ($event.isUserInput) {
      if (!this.classCondition.classDefinition) {
        this.classCondition.classDefinition = new ClassDefinition();
      }
      this.classCondition.classDefinition = classDefinition;
      this.classCondition.classDefinition.tenantId = this.helpseeker.subscribedTenants.find(
        (t) => t.role === UserRole.HELP_SEEKER
      ).tenant.id;
      this.classConditionChange.emit(this.classCondition);
    }
  }

  onOperatorChange(aggregationOperatorType: AggregationOperatorType, $event) {
    if ($event.isUserInput) {
      // ignore on deselection of the previous option
      this.classCondition.aggregationOperatorType = aggregationOperatorType;
      this.rulePreconditionForm.value.aggregationoperator = aggregationOperatorType;
      this.classConditionChange.emit(this.classCondition);
    }
  }

  onChange($event) {
    if (this.classDefinitions.length > 0) {
      this.classCondition.classDefinition = this.classDefinitions.find(
        (cd) => cd.id === this.rulePreconditionForm.value.classDefinitionId
      );
    }
    this.classCondition.value = this.rulePreconditionForm.value.value;
  }

  addAttributeCondition() {
    this.classCondition.attributeConditions.push(
      new AttributeCondition(this.classCondition.classDefinition)
    );
    this.classConditionChange.emit(this.classCondition);
  }

  private retrieveAggregationOperatorValueOf(op) {
    let x: AggregationOperatorType =
      AggregationOperatorType[op as keyof typeof AggregationOperatorType];
    return x;
  }
}
