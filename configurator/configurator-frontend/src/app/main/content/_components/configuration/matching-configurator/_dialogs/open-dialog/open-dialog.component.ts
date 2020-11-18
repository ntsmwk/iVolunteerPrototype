import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

import { MatchingConfigurationService } from 'app/main/content/_service/configuration/matching-configuration.service';
import { MatchingConfiguration } from 'app/main/content/_model/configurator/configurations';
import { MatchingBrowseSubDialogData } from 'app/main/content/_components/configuration/matching-configurator/_dialogs/browse-sub-dialog/browse-sub-dialog.component';

export interface OpenMatchingDialogData {
  matchingConfiguration: MatchingConfiguration;
  tenantId: string;
}

@Component({
  selector: "open-matching-dialog",
  templateUrl: './open-dialog.component.html',
  styleUrls: ['./open-dialog.component.scss'],
})
export class OpenMatchingDialogComponent implements OnInit {
  constructor(
    public dialogRef: MatDialogRef<OpenMatchingDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: OpenMatchingDialogData,
    private matchingConfigurationService: MatchingConfigurationService,
  ) { }

  allMatchingConfigurations: MatchingConfiguration[];
  browseDialogData: MatchingBrowseSubDialogData;

  recentMatchingConfigurations: MatchingConfiguration[];
  loaded: boolean;
  browseMode: boolean;

  async ngOnInit() {
    this.matchingConfigurationService
      .getAllMatchingConfigurationsByTenantId(this.data.tenantId)
      .toPromise().then((matchingConfigurations: MatchingConfiguration[]) => {
        this.recentMatchingConfigurations = matchingConfigurations;
        this.allMatchingConfigurations = matchingConfigurations;

        // ----DEBUG
        // this.recentMatchingConfigurations.push(...this.recentMatchingConfigurations);
        // this.recentMatchingConfigurations.push(...this.recentMatchingConfigurations);
        // ----
        this.recentMatchingConfigurations = this.recentMatchingConfigurations.sort(
          (a, b) => b.timestamp.valueOf() - a.timestamp.valueOf()
        );

        if (this.recentMatchingConfigurations.length > 5) {
          this.recentMatchingConfigurations = this.recentMatchingConfigurations.slice(
            0,
            5
          );
        }
        this.loaded = true;
      });
  }

  handleRowClick(matchingConfiguration: MatchingConfiguration) {
    this.data.matchingConfiguration = matchingConfiguration;
    this.dialogRef.close(this.data);
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  handleBrowseClick() {
    this.browseDialogData = new MatchingBrowseSubDialogData();

    this.browseDialogData.title = 'Durchsuchen';
    this.browseDialogData.entries = [];

    for (const matchingConfiguration of this.allMatchingConfigurations) {
      this.browseDialogData.entries.push({
        id: matchingConfiguration.id,
        name: matchingConfiguration.name,
        leftMatchingEntity: matchingConfiguration.leftSideName,
        rightMatchingEntity: matchingConfiguration.rightSideName,
        date: matchingConfiguration.timestamp,
      });
    }

    this.browseMode = true;
  }

  handleBrowseBackClick() {
    this.browseMode = false;
  }

  handleReturnFromBrowse(event: { cancelled: boolean; entryId: string }) {
    this.browseMode = false;

    if (!event.cancelled) {
      this.data.matchingConfiguration = this.allMatchingConfigurations.find(
        (c) => c.id === event.entryId
      );
      this.dialogRef.close(this.data);
    }
  }
}
