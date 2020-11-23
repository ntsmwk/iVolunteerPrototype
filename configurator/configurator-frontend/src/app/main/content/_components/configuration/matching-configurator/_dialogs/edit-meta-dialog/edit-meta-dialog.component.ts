import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ClassConfigurationService } from 'app/main/content/_service/configuration/class-configuration.service';
import { ClassConfiguration, MatchingConfiguration } from 'app/main/content/_model/configurator/configurations';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Relationship } from 'app/main/content/_model/configurator/relationship';
import { ClassDefinition } from 'app/main/content/_model/configurator/class';
import { MatchingConfigurationService } from 'app/main/content/_service/configuration/matching-configuration.service';
import { stringUniqueValidator } from 'app/main/content/_validator/string-unique.validator';

export interface EditMetaMatchingConfigurationDialogData {
  tenantId: string;
  matchingConfiguration: MatchingConfiguration;
}

@Component({
  selector: "edit-meta-matching-configuration-dialog",
  templateUrl: './edit-meta-dialog.component.html',
  styleUrls: ['./edit-meta-dialog.component.scss']
})
export class EditMetaMatchingConfigurationDialogComponent implements OnInit {
  constructor(
    public dialogRef: MatDialogRef<EditMetaMatchingConfigurationDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: EditMetaMatchingConfigurationDialogData,
    private matchingConfigurationService: MatchingConfigurationService,
  ) { }

  dialogForm: FormGroup;
  allClassConfigurations: ClassConfiguration[];
  showEditDialog: boolean;
  loaded = false;

  async ngOnInit() {
    this.matchingConfigurationService
      .getAllMatchingConfigurationsByTenantId(this.data.tenantId)
      .toPromise()
      .then((classConfigurations: ClassConfiguration[]) => {
        this.allClassConfigurations = classConfigurations;
        this.dialogForm = new FormGroup({
          label: new FormControl('', [Validators.required, stringUniqueValidator(this.allClassConfigurations.map(c => c.name), [this.data.matchingConfiguration.name])]),
        });

        this.dialogForm.setValue({ label: this.data.matchingConfiguration.name });

        this.loaded = true;
      });
  }

  displayErrorMessage(key: string) {
    if (this.dialogForm.get(key).hasError('required')) {
      return "Pflichtfeld";
    } else if (this.dialogForm.get(key).hasError('stringunique')) {
      return "Name bereits vorhanden";
    }
  }

  onNoClick(): void {
    this.dialogRef.close();
  }


  onSaveClick() {
    if (this.checkFormInvalid()) {
      return;
    }
    const formValues = this.getFormValues();
    this.matchingConfigurationService
      .saveMatchingConfigurationMeta(this.data.matchingConfiguration.id, formValues.name)
      .toPromise().then((ret: MatchingConfiguration) => {
        // this.responseService.sendClassConfiguratorResponse(this.data.redirectUrl, ret.id, null, "save").toPromise().then(() => {
        this.data.matchingConfiguration = ret;
        this.dialogRef.close(this.data);
        // });
      });
  }

  private checkFormInvalid() {
    this.dialogForm.get('label').markAsTouched();
    return this.dialogForm.invalid;
  }

  private getFormValues(): { name: string } {
    const name = this.dialogForm.get('label').value;

    return { name };
  }
}
