import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ClassConfigurationService } from 'app/main/content/_service/configuration/class-configuration.service';
import { ClassConfiguration } from 'app/main/content/_model/configurator/configurations';
import { ClassDefinition } from 'app/main/content/_model/configurator/class';
import { Relationship } from 'app/main/content/_model/configurator/relationship';
import { ResponseService } from 'app/main/content/_service/response.service';

export interface ConfirmClassConfigurationSaveDialogData {
  classConfiguration: ClassConfiguration;
  classDefinitions: ClassDefinition[];
  relationships: Relationship[];

  deletedClassDefinitionIds: string[];
  deletedRelationshipIds: string[];
  tenantId: string;

  redirectUrl?: string;

}

@Component({
  selector: 'confirm-class-configuration-save-dialog',
  templateUrl: './confirm-save-dialog.component.html',
  styleUrls: ['./confirm-save-dialog.component.scss']
})
export class ConfirmClassConfigurationSaveDialogComponent implements OnInit {

  constructor(
    public dialogRef: MatDialogRef<ConfirmClassConfigurationSaveDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: ConfirmClassConfigurationSaveDialogData,
    private classConfigurationService: ClassConfigurationService,
    private responseService: ResponseService,
  ) {
  }

  loaded: boolean;

  async ngOnInit() {

  }

  onOKClick() {
    const { classConfiguration, classDefinitions, relationships, deletedClassDefinitionIds, deletedRelationshipIds, tenantId } = this.data;

    this.classConfigurationService.saveFullClassConfiguration(
      { classConfiguration, classDefinitions, relationships, deletedClassDefinitionIds, deletedRelationshipIds, tenantId }
    ).toPromise().then((ret: ClassConfiguration) => {
      this.responseService.sendClassConfiguratorResponse(this.data.redirectUrl, ret.id, null, 'save').toPromise().then(() => {
        this.dialogRef.close();
      });
    });

  }

  onNoClick(): void {
    this.dialogRef.close();
  }







}


