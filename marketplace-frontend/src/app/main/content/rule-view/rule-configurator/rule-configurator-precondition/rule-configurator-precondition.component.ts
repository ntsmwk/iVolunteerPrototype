import { Component, OnInit, Input, EventEmitter, Output } from '@angular/core';
import { ActivatedRoute } from '@angular/router';


import { isNullOrUndefined } from 'util';
import { LoginService } from '../../../_service/login.service';
import { Participant, ParticipantRole } from '../../../_model/participant';
import { MessageService } from '../../../_service/message.service';
import { FormGroup, FormBuilder, FormControl } from '@angular/forms';
import { Marketplace } from 'app/main/content/_model/marketplace';
import { CoreMarketplaceService } from 'app/main/content/_service/core-marketplace.service';
import { SourceRuleEntry, MappingOperatorType } from 'app/main/content/_model/derivation-rule';
import { CoreHelpSeekerService } from 'app/main/content/_service/core-helpseeker.service';
import { ClassDefinition } from 'app/main/content/_model/meta/Class';
import { ClassDefinitionService } from 'app/main/content/_service/meta/core/class/class-definition.service';
import { ClassProperty } from 'app/main/content/_model/meta/Property';
import { ClassPropertyService } from 'app/main/content/_service/meta/core/property/class-property.service';

@Component({
  selector: 'rule-precondition',
  templateUrl: './rule-configurator-precondition.component.html',
  styleUrls: ['../rule-configurator.component.scss']
})
export class FuseRulePreconditionConfiguratorComponent implements OnInit {

  @Input('sourceRuleEntry') sourceRuleEntry: SourceRuleEntry;
  @Output('sourceRuleEntry') sourceRuleEntryChange: EventEmitter<SourceRuleEntry> = new EventEmitter<SourceRuleEntry>();

  participant: Participant;
  marketplace: Marketplace;
  role: ParticipantRole;
  rulePreconditionForm: FormGroup;
  classDefinitions: ClassDefinition[] = [];
  classProperties: ClassProperty<any>[] = [];
  operations: any;

  classDefinitionCache: ClassDefinition[] = [];

  constructor(private route: ActivatedRoute,
    private loginService: LoginService,
    private formBuilder: FormBuilder,
    private classDefinitionService: ClassDefinitionService,
    private classPropertyService: ClassPropertyService,
    private helpSeekerService: CoreHelpSeekerService) {
    this.rulePreconditionForm = formBuilder.group({
      'classDefinitionId': new FormControl(undefined),
      'classPropertyId': new FormControl(undefined),
      'mappingOperatorType': new FormControl(undefined),
      'value': new FormControl(undefined),
    });

  }

  ngOnInit() {
    console.error(this.sourceRuleEntry);
    this.rulePreconditionForm.setValue({
      classDefinitionId: (this.sourceRuleEntry.classDefinition ? this.sourceRuleEntry.classDefinition.id : "") || "",
      classPropertyId: (this.sourceRuleEntry.classProperty ? this.sourceRuleEntry.classProperty.id : "") || "",
      mappingOperatorType: this.sourceRuleEntry.mappingOperatorType || MappingOperatorType.EQ,
      value: this.sourceRuleEntry.value || ""
    });

    this.operations = Object.keys(MappingOperatorType);

    this.loginService.getLoggedIn().toPromise().then((participant: Participant) => {
      this.participant = participant;
      this.helpSeekerService.findRegisteredMarketplaces(participant.id).toPromise().then((marketplace: Marketplace) => {
        this.marketplace = marketplace;
        this.classDefinitionService.getAllClassDefinitions(marketplace).toPromise().then(
          (definitions: ClassDefinition[]) => {
            this.classDefinitions = definitions;
            this.loadClassProperties(null);
          }
        );
      });
    });
  }

  onClassChange($event) {
    if(!this.sourceRuleEntry.classDefinition){
      this.sourceRuleEntry.classDefinition = new ClassDefinition();
    }
    this.sourceRuleEntry.classDefinition.id = $event.source.value;
    this.loadClassProperties($event);
  }

  onPropertyChange($event){
    if(!this.sourceRuleEntry.classProperty){
      this.sourceRuleEntry.classProperty = new ClassProperty();
    }
    this.sourceRuleEntry.classProperty.id = $event.source.value;
    this.onChange($event);
  }

  private loadClassProperties($event) {
    if (this.sourceRuleEntry && this.sourceRuleEntry.classDefinition && this.sourceRuleEntry.classDefinition.id) {
      this.classPropertyService.getAllClassPropertiesFromClass(this.marketplace, this.sourceRuleEntry.classDefinition.id).toPromise()
        .then((props: ClassProperty<any>[]) => {
          this.classProperties = props;
          this.onChange($event);
        });
    }
  }

  onChange($event) {
    if (this.classDefinitions.length > 0 && this.classProperties.length > 0) {
      this.sourceRuleEntry.classDefinition = this.classDefinitions.find(cd => cd.id === this.rulePreconditionForm.value.classDefinitionId);
      this.sourceRuleEntry.classProperty = this.classProperties.find(cp => cp.id === this.rulePreconditionForm.value.classPropertyId) || new ClassProperty();
      this.sourceRuleEntry.mappingOperatorType = this.rulePreconditionForm.value.mappingOperatorType;
      this.sourceRuleEntry.value = this.rulePreconditionForm.value.value;
      this.sourceRuleEntryChange.emit(this.sourceRuleEntry);
    }
  }

  private retrieveValueOf(op) {
    var x: MappingOperatorType = MappingOperatorType[op as keyof typeof MappingOperatorType];
    return x;
  }
}