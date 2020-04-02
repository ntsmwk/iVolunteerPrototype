import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Marketplace } from 'app/main/content/_model/marketplace';
import { isNullOrUndefined } from 'util';
import { LoginService } from 'app/main/content/_service/login.service';
import { Helpseeker } from 'app/main/content/_model/helpseeker';
import { ClassConfigurationService } from 'app/main/content/_service/configuration/class-configuration.service';
import { ClassConfiguration } from 'app/main/content/_model/configurations';
import { Relationship } from 'app/main/content/_model/meta/Relationship';
import { ClassDefinition } from 'app/main/content/_model/meta/Class';
import { ClassDefinitionService } from 'app/main/content/_service/meta/core/class/class-definition.service';
import { RelationshipService } from 'app/main/content/_service/meta/core/relationship/relationship.service';
import { isNull } from '@angular/compiler/src/output/output_ast';

export interface OpenDialogData {
  classConfiguration: ClassConfiguration;
  classDefinitions: ClassDefinition[];
  relationships: Relationship[];
  marketplace: Marketplace;
}

@Component({
  selector: 'open-dialog',
  templateUrl: './open-dialog.component.html',
  styleUrls: ['./open-dialog.component.scss']
})
export class OpenDialogComponent implements OnInit {

  constructor(
    public dialogRef: MatDialogRef<OpenDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: OpenDialogData,
    private classConfigurationService: ClassConfigurationService,
    private classDefinitionService: ClassDefinitionService,
    private relationshipService: RelationshipService,
    private loginService: LoginService,
  ) {
  }

  selected: string;
  allClassConfigurations: ClassConfiguration[];
  recentClassConfigurations: ClassConfiguration[];
  loaded = false;

  ngOnInit() {
    this.loginService.getLoggedIn().toPromise().then((helpseeker: Helpseeker) => {
      this.classConfigurationService.getAllClassConfigurations(this.data.marketplace).toPromise().then((classConfigurations: ClassConfiguration[]) => {
        this.allClassConfigurations = classConfigurations.filter(c => {
          return c.userId === helpseeker.id || isNullOrUndefined(c.userId);
        });

        this.recentClassConfigurations = this.allClassConfigurations;
        this.recentClassConfigurations = this.recentClassConfigurations.sort((a, b) => b.timestamp.valueOf() - a.timestamp.valueOf());

        if (this.recentClassConfigurations.length > 5) {
          this.recentClassConfigurations = this.recentClassConfigurations.slice(0, 5);
        }
        this.loaded = true;
      });
    });
  }

  itemSelected(event: any, c: ClassConfiguration) {
    this.data.classConfiguration = c;
    this.data.classDefinitions = [];
    this.data.relationships = [];


    Promise.all([
      this.classDefinitionService.getClassDefinitionsById(this.data.marketplace, c.classDefinitionIds).toPromise().then((classDefinitions: ClassDefinition[]) => {
        if (!isNullOrUndefined(classDefinitions)) {
          this.data.classDefinitions = classDefinitions;
        }
      }),
      this.relationshipService.getRelationshipsById(this.data.marketplace, c.relationshipIds).toPromise().then((relationships: Relationship[]) => {
        if (!isNullOrUndefined(relationships)) {
          this.data.relationships = relationships;
        }
      })
    ]).then(() => {
      this.dialogRef.close(this.data);
    });
  }

  onNoClick(): void {
    this.dialogRef.close();
  }







}


