import {
  Component,
  OnInit,
  Input,
  EventEmitter,
  Output,
} from "@angular/core";
import { ActivatedRoute } from "@angular/router";

import { LoginService } from "../../../../../_service/login.service";
import { User, UserRole } from "../../../../../_model/user";
import { FormGroup, FormBuilder, FormControl } from "@angular/forms";
import { Marketplace } from "app/main/content/_model/marketplace";
import {
  AttributeCondition,
  ClassCondition,
  AggregationOperatorType,
} from "app/main/content/_model/derivation-rule";
import { ClassDefinition } from "app/main/content/_model/meta/class";
import { ClassDefinitionService } from "app/main/content/_service/meta/core/class/class-definition.service";
import { GlobalInfo } from "app/main/content/_model/global-info";

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
  ) {
    this.rulePreconditionForm = formBuilder.group({
      classDefinitionId: new FormControl(undefined),
      aggregationOperatorType: new FormControl(undefined),
      value: new FormControl(undefined),
    });
  }

  async ngOnInit() {
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
      ).tenantId;
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
