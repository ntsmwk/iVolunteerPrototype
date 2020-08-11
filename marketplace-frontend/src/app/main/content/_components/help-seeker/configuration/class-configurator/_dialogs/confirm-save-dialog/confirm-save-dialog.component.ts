import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Marketplace } from 'app/main/content/_model/marketplace';
import { ClassConfigurationService } from 'app/main/content/_service/configuration/class-configuration.service';
import { ClassConfiguration } from 'app/main/content/_model/meta/configurations';
import { ClassDefinition } from 'app/main/content/_model/meta/class';
import { Relationship } from 'app/main/content/_model/meta/relationship';
import { RelationshipService } from 'app/main/content/_service/meta/core/relationship/relationship.service';
import { ClassDefinitionService } from 'app/main/content/_service/meta/core/class/class-definition.service';
import { GlobalInfo } from 'app/main/content/_model/global-info';
import { LoginService } from 'app/main/content/_service/login.service';

export interface ConfirmClassConfigurationSaveDialogData {
  classConfiguration: ClassConfiguration;
  classDefinitions: ClassDefinition[];
  relationships: Relationship[];

  deletedClassDefintions: string[];
  deletedRelationships: string[];

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
    private relationshipService: RelationshipService,
    private classDefinitionService: ClassDefinitionService,
    private loginService: LoginService
  ) {
  }

  loaded: boolean;
  globalInfo: GlobalInfo;

  async ngOnInit() {
    this.globalInfo = <GlobalInfo>(
      await this.loginService.getGlobalInfo().toPromise()
    );
  }

  onOKClick() {
    Promise.all([
      this.relationshipService
        .addAndUpdateRelationships(this.globalInfo.marketplace, this.data.relationships)
        .toPromise().then((ret: Relationship[]) => {
          console.log("rel done");
          this.data.relationships = ret;
        }),

      this.classDefinitionService
        .addOrUpdateClassDefintions(this.globalInfo.marketplace, this.data.classDefinitions)
        .toPromise().then((ret: ClassDefinition[]) => {
          console.log("cd done");
          this.data.classDefinitions = ret;
        }),

      this.classDefinitionService
        .deleteClassDefinitions(this.globalInfo.marketplace, this.data.deletedClassDefintions)
        .toPromise().then((ret: any) => {
          console.log("deleted cds done");
          this.data.deletedClassDefintions = [];
        }),

      this.relationshipService
        .deleteRelationships(this.globalInfo.marketplace, this.data.deletedRelationships)
        .toPromise().then((ret: any) => {
          console.log("deleted rels done");
          this.data.deletedRelationships = [];
        }),
    ]).then(() => {
      this.classConfigurationService
        .saveClassConfiguration(this.globalInfo.marketplace, this.data.classConfiguration)
        .toPromise().then((ret: ClassConfiguration) => {
          this.data.classConfiguration = ret;
          console.log("classConfig done");
        }).then(() => {
          console.log("should close");
          this.dialogRef.close(this.data);
        });
    });
  }

  onNoClick(): void {
    this.dialogRef.close();
  }







}


