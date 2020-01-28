import { Component, OnInit, Input, EventEmitter, Output } from '@angular/core';
import { ActivatedRoute } from '@angular/router';


import { isNullOrUndefined } from 'util';
import { LoginService } from '../../_service/login.service';
import { Participant, ParticipantRole } from '../../_model/participant';
import { MessageService } from '../../_service/message.service';
import { FormGroup, FormBuilder, FormControl } from '@angular/forms';
import { Marketplace } from 'app/main/content/_model/marketplace';
import { CoreMarketplaceService } from 'app/main/content/_service/core-marketplace.service';
import { MappingOperatorType, AttributeSourceRuleEntry, ClassSourceRuleEntry, AttributeAggregationOperatorType } from 'app/main/content/_model/derivation-rule';
import { CoreHelpSeekerService } from 'app/main/content/_service/core-helpseeker.service';
import { ClassDefinition } from 'app/main/content/_model/meta/Class';
import { ClassDefinitionService } from 'app/main/content/_service/meta/core/class/class-definition.service';
import { ClassProperty, PropertyDefinition } from 'app/main/content/_model/meta/Property';
import { ClassPropertyService } from 'app/main/content/_service/meta/core/property/class-property.service';
import { PropertyDefinitionService } from '../../_service/meta/core/property/property-definition.service';

@Component({
  selector: 'attribute-rule-precondition',
  templateUrl: './attribute-rule-configurator-precondition.component.html',
  styleUrls: ['../rule-configurator.component.scss']
})
export class FuseAttributeRulePreconditionConfiguratorComponent implements OnInit {

  @Input('attributeSourceRuleEntry') attributeSourceRuleEntry: AttributeSourceRuleEntry;
  @Output('attributeSourceRuleEntry') attributeSourceRuleEntryChange: EventEmitter<AttributeSourceRuleEntry> = new EventEmitter<AttributeSourceRuleEntry>();

  participant: Participant;
  marketplace: Marketplace;
  role: ParticipantRole;
  rulePreconditionForm: FormGroup;
  classDefinitions: ClassDefinition[] = [];
  classProperties: ClassProperty<any>[] = [];
  comparisonOperators: any;
  aggregationOperators: any;

  enumValues = [];

  propertyDefinition: PropertyDefinition<any>;

  classDefinitionCache: ClassDefinition[] = [];

  constructor(private route: ActivatedRoute,
    private loginService: LoginService,
    private formBuilder: FormBuilder,
    private classDefinitionService: ClassDefinitionService,
    private classPropertyService: ClassPropertyService,
    private propertyDefinitionService: PropertyDefinitionService,
    private helpSeekerService: CoreHelpSeekerService) {
    this.rulePreconditionForm = formBuilder.group({
      'classDefinitionId': new FormControl(undefined),
      'classPropertyId': new FormControl(undefined),
      'aggregationOperatorType': new FormControl(undefined),
      'mappingOperatorType': new FormControl(undefined),
      'value': new FormControl(undefined),
    });

  }

  ngOnInit() {
    this.rulePreconditionForm.setValue({
      classDefinitionId: (this.attributeSourceRuleEntry.classDefinition ? this.attributeSourceRuleEntry.classDefinition.id : "") || "",
      classPropertyId: (this.attributeSourceRuleEntry.classProperty ? this.attributeSourceRuleEntry.classProperty.id : "") || "",
      aggregationOperatorType: this.attributeSourceRuleEntry.aggregationOperatorType || AttributeAggregationOperatorType.SUM,
      mappingOperatorType: this.attributeSourceRuleEntry.mappingOperatorType || MappingOperatorType.EQ,
      value: this.attributeSourceRuleEntry.value || ""
    });

    this.comparisonOperators = Object.keys(MappingOperatorType);
    this.aggregationOperators = Object.keys(AttributeAggregationOperatorType);


    this.loginService.getLoggedIn().toPromise().then((participant: Participant) => {
      this.participant = participant;
      this.helpSeekerService.findRegisteredMarketplaces(participant.id).toPromise().then((marketplace: Marketplace) => {
        this.marketplace = marketplace;
        this.classDefinitionService.getAllClassDefinitionsWithoutHeadAndEnums(marketplace, this.participant.username==='MVS'?'MV':'FF').toPromise().then(
          (definitions: ClassDefinition[]) => {
            this.classDefinitions = definitions;
            this.loadClassProperties(null);
          }
        );
      });
    });
  }

  onClassChange($event) {
    if (!this.attributeSourceRuleEntry.classDefinition) {
      this.attributeSourceRuleEntry.classDefinition = new ClassDefinition();
    }
    this.attributeSourceRuleEntry.classDefinition.id = $event.source.value;
    this.enumValues = [];
    this.loadClassProperties($event);
  }

  onPropertyChange($event) {
    if (!this.attributeSourceRuleEntry.classProperty) {
      this.attributeSourceRuleEntry.classProperty = new ClassProperty();
    }
    this.attributeSourceRuleEntry.classProperty.id = $event.source.value;
    this.rulePreconditionForm.value.classPropertyId = $event.source.value;
    this.onChange($event);
  }

  private loadClassProperties($event) {
    if (this.attributeSourceRuleEntry && this.attributeSourceRuleEntry.classDefinition && this.attributeSourceRuleEntry.classDefinition.id) {
      this.classPropertyService.getAllClassPropertiesFromClass(this.marketplace, this.attributeSourceRuleEntry.classDefinition.id).toPromise()
        .then((props: ClassProperty<any>[]) => {
          this.classProperties = props;
          this.enumValues = [];
          this.onChange($event);
        });
    }
  }

  findEnumValues() {
    if (this.attributeSourceRuleEntry.classProperty.type === 'ENUM' && this.enumValues.length == 0) {
      this.classDefinitionService.getEnumValuesFromEnumHeadClassDefinition(this.marketplace,
        this.attributeSourceRuleEntry.classProperty.allowedValues[0].enumClassId).toPromise().then((list: any[]) => {
          this.enumValues = list.map(e => e.value);
        })
    }
    return this.enumValues;
  }

  onChange($event) {
    if (this.classDefinitions.length > 0 && this.classProperties.length > 0) {
      this.attributeSourceRuleEntry.classDefinition = this.classDefinitions.find(cd => cd.id === this.rulePreconditionForm.value.classDefinitionId);
      this.attributeSourceRuleEntry.classProperty = this.classProperties.find(cp => cp.id === this.rulePreconditionForm.value.classPropertyId) || new ClassProperty();
      this.attributeSourceRuleEntry.aggregationOperatorType = this.rulePreconditionForm.value.aggregationOperatorType;
      this.attributeSourceRuleEntry.mappingOperatorType = this.rulePreconditionForm.value.mappingOperatorType;
      this.attributeSourceRuleEntry.value = this.rulePreconditionForm.value.value;
      this.attributeSourceRuleEntryChange.emit(this.attributeSourceRuleEntry);
    }
  }

  private retrieveMappingOperatorValueOf(op) {
    var x: MappingOperatorType = MappingOperatorType[op as keyof typeof MappingOperatorType];
    return x;
  }

  private retrieveAggregationOperatorValueOf(op) {
    var x: AttributeAggregationOperatorType = AttributeAggregationOperatorType[op as keyof typeof AttributeAggregationOperatorType];
    return x;
  }
}