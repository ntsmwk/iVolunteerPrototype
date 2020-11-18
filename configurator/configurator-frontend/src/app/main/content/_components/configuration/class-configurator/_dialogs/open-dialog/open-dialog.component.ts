import { Component, Inject, OnInit } from "@angular/core";
import { MatDialogRef, MAT_DIALOG_DATA } from "@angular/material/dialog";
import { isNullOrUndefined } from "util";
import { ClassConfigurationService } from "app/main/content/_service/configuration/class-configuration.service";
import { ClassConfiguration } from "app/main/content/_model/configurator/configurations";
import { Relationship } from "app/main/content/_model/configurator/relationship";
import { ClassDefinition } from "app/main/content/_model/configurator/class";
import { ClassDefinitionService } from "app/main/content/_service/meta/core/class/class-definition.service";
import { RelationshipService } from "app/main/content/_service/meta/core/relationship/relationship.service";
import { ClassBrowseSubDialogData } from "../browse-sub-dialog/browse-sub-dialog.component";

export interface OpenClassConfigurationDialogData {
  classConfiguration: ClassConfiguration;
  classDefinitions: ClassDefinition[];
  relationships: Relationship[];
  tenantId: string;
}

@Component({
  selector: "open-class-configuration-dialog",
  templateUrl: "./open-dialog.component.html",
  styleUrls: ["./open-dialog.component.scss"],
})
export class OpenClassConfigurationDialogComponent implements OnInit {
  constructor(
    public dialogRef: MatDialogRef<OpenClassConfigurationDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: OpenClassConfigurationDialogData,
    private classConfigurationService: ClassConfigurationService,
    private classDefinitionService: ClassDefinitionService,
    private relationshipService: RelationshipService,
  ) { }

  selected: string;
  allClassConfigurations: ClassConfiguration[];
  recentClassConfigurations: ClassConfiguration[];
  loaded: boolean;

  browseMode: boolean;
  browseDialogData: ClassBrowseSubDialogData;

  async ngOnInit() {
    this.classConfigurationService
      .getAllClassConfigurationsByTenantId(this.data.tenantId).toPromise()
      .then((classConfigurations: ClassConfiguration[]) => {

        this.allClassConfigurations = classConfigurations;
        this.recentClassConfigurations = this.allClassConfigurations;
        this.recentClassConfigurations = this.recentClassConfigurations.sort(
          (a, b) => b.timestamp.valueOf() - a.timestamp.valueOf()
        );

        if (this.recentClassConfigurations.length > 6) {
          this.recentClassConfigurations = this.recentClassConfigurations.slice(0, 6);
        }

        this.loaded = true;
      });
  }

  handleRowClick(c: ClassConfiguration) {
    this.data.classConfiguration = c;
    this.data.classDefinitions = [];
    this.data.relationships = [];

    Promise.all([
      this.classDefinitionService.getClassDefinitionsById(c.classDefinitionIds)
        .toPromise().then((classDefinitions: ClassDefinition[]) => {
          if (!isNullOrUndefined(classDefinitions)) {
            this.data.classDefinitions = classDefinitions;
          }
        }),
      this.relationshipService.getRelationshipsById(c.relationshipIds)
        .toPromise().then((relationships: Relationship[]) => {
          if (!isNullOrUndefined(relationships)) {
            this.data.relationships = relationships;
          }
        }),
    ]).then(() => {
      this.dialogRef.close(this.data);
    });
  }

  handleBrowseClick() {
    this.browseDialogData = new ClassBrowseSubDialogData();
    this.browseDialogData.sourceReference = undefined;
    this.browseDialogData.title = "Klassen-Konfigurationen durchsuchen";

    this.browseDialogData.entries = [];

    for (const classConfiguration of this.allClassConfigurations) {
      this.browseDialogData.entries.push({
        id: classConfiguration.id,
        name: classConfiguration.name,
        date: classConfiguration.timestamp,
      });
    }

    this.browseMode = true;
  }

  handleBrowseBackClick() {
    this.browseMode = false;
  }

  handleReturnFromBrowse(event: { cancelled: boolean; entryId: string }) {
    if (!event.cancelled) {
      const selectedClassConfiguration = this.allClassConfigurations.find(
        (c) => c.id === event.entryId
      );

      this.handleRowClick(selectedClassConfiguration);
    } else {
      this.browseMode = false;
    }
  }

  onNoClick(): void {
    this.dialogRef.close();
  }
}
