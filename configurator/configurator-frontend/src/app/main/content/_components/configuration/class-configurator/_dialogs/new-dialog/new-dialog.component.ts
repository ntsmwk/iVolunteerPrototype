import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ClassConfigurationService } from 'app/main/content/_service/configuration/class-configuration.service';
import { ClassConfiguration } from 'app/main/content/_model/configurator/configurations';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Relationship } from 'app/main/content/_model/configurator/relationship';
import { ClassDefinition } from 'app/main/content/_model/configurator/class';
import { RelationshipService } from 'app/main/content/_service/meta/core/relationship/relationship.service';
import { ClassDefinitionService } from 'app/main/content/_service/meta/core/class/class-definition.service';
import { stringUniqueValidator } from 'app/main/content/_validator/string-unique.validator';
import { isNullOrUndefined } from 'util';
import { ResponseService } from 'app/main/content/_service/response.service';
import { environment } from 'environments/environment';

export interface NewClassConfigurationDialogData {
  classConfiguration: ClassConfiguration;
  relationships: Relationship[];
  classDefinitions: ClassDefinition[];
  tenantId: string;
  redirectUrl: string;
}

@Component({
  selector: "new-class-configuration-dialog",
  templateUrl: './new-dialog.component.html',
  styleUrls: ['./new-dialog.component.scss']
})
export class NewClassConfigurationDialogComponent implements OnInit {
  constructor(
    public dialogRef: MatDialogRef<NewClassConfigurationDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: NewClassConfigurationDialogData,
    private classConfigurationService: ClassConfigurationService,
    private relationshipsService: RelationshipService,
    private classDefintionService: ClassDefinitionService,
    private responseService: ResponseService,
  ) { }

  dialogForm: FormGroup;
  allClassConfigurations: ClassConfiguration[];
  showEditDialog: boolean;
  loaded = false;
  configuratorMode: string;

  async ngOnInit() {
    this.configuratorMode = environment.MODE;

    this.classConfigurationService
      .getAllClassConfigurations()
      .toPromise()
      .then((classConfigurations: ClassConfiguration[]) => {
        this.allClassConfigurations = classConfigurations;
        this.dialogForm = new FormGroup({
          label: new FormControl('', isNullOrUndefined(this.data.classConfiguration) ?
            [Validators.required, stringUniqueValidator(this.allClassConfigurations.map(c => c.name))]
            : [Validators.required, stringUniqueValidator(this.allClassConfigurations.map(c => c.name), [this.data.classConfiguration.name])]
          ),
          description: new FormControl(''),
          // rootLabel: new FormControl(''),

        });

        if (!isNullOrUndefined(this.data.classConfiguration)) {
          this.showEditDialog = true;
          this.dialogForm
            .get('label')
            .setValue(this.data.classConfiguration.name);
          this.dialogForm
            .get('description')
            .setValue(this.data.classConfiguration.description);
        }

        if (!this.showEditDialog) {
          this.dialogForm.addControl('type', new FormControl('', Validators.required));
          this.dialogForm.get('type').setValue('iVolunteer');
          this.dialogForm.updateValueAndValidity();
        }
        // ----DEBUG
        // this.recentMatchingConfigurations.push(...this.recentMatchingConfigurations);
        // this.recentMatchingConfigurations.push(...this.recentMatchingConfigurations);
        // ----

        this.loaded = true; console.log(this.data.tenantId);

      });
  }

  displayErrorMessage(key: string) {
    if (this.dialogForm.get(key).hasError('required')) {
      return 'Pflichtfeld';
    } else if (this.dialogForm.get(key).hasError('stringunique')) {
      return 'Name bereits vorhanden';
    }
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  onCreateClick() {
    if (this.checkFormInvalid()) {
      return;
    }
    const formValues = this.getFormValues();
    const classConfiguration = new ClassConfiguration();
    classConfiguration.tenantId = this.data.tenantId;
    classConfiguration.name = formValues.name;
    classConfiguration.description = formValues.description;

    this.responseService.sendClassConfiguratorResponse(
      this.data.redirectUrl, { classConfiguration, tenantId: this.data.tenantId }, null, 'new', this.dialogForm.value.type
    ).toPromise()
      .then((cc: ClassConfiguration) => {
        this.data.classConfiguration = cc;
        Promise.all([
          this.relationshipsService
            .getRelationshipsById(this.data.classConfiguration.relationshipIds).toPromise()
            .then((ret: Relationship[]) => {
              this.data.relationships = ret;
            }),
          this.classDefintionService
            .getClassDefinitionsById(this.data.classConfiguration.classDefinitionIds).toPromise()
            .then((ret: ClassDefinition[]) => {
              this.data.classDefinitions = ret;
            })
        ]).then(() => {
          this.dialogRef.close(this.data);
        });
      });
  }

  onSaveClick() {
    if (this.checkFormInvalid()) {
      return;
    }
    const formValues = this.getFormValues();
    this.classConfigurationService
      .saveClassConfigurationMeta(this.data.classConfiguration.id, formValues.name, formValues.description)
      .toPromise().then((ret: ClassConfiguration) => {
        this.data.classConfiguration = ret;
        this.dialogRef.close(this.data);
      });
  }

  private checkFormInvalid() {
    this.dialogForm.get('label').markAsTouched();
    this.dialogForm.get('description').markAsTouched();
    return this.dialogForm.invalid;
  }

  private getFormValues(): { name: string; description: string } {
    const name = this.dialogForm.get('label').value;
    const description = this.dialogForm.get('description').value;

    return { name, description };
  }
}
