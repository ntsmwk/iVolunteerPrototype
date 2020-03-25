import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Marketplace } from 'app/main/content/_model/marketplace';
import { LoginService } from 'app/main/content/_service/login.service';
import { Helpseeker } from 'app/main/content/_model/helpseeker';
import { MatchingConfigurationService } from 'app/main/content/_service/configuration/matching-configuration.service';
import { MatchingConfiguration } from 'app/main/content/_model/configurations';

export interface OpenMatchingDialogData {
  marketplace: Marketplace;
  matchingConfiguration: MatchingConfiguration;
}

@Component({
  selector: 'open-matching-dialog',
  templateUrl: './open-dialog.component.html',
  styleUrls: ['./open-dialog.component.scss']
})
export class OpenMatchingDialogComponent implements OnInit {

  constructor(
    public dialogRef: MatDialogRef<OpenMatchingDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: OpenMatchingDialogData,
    private matchingConfigurationService: MatchingConfigurationService,
    private loginService: LoginService,
  ) {
  }

  recentMatchingConfigurations: MatchingConfiguration[];
  loaded = false;

  ngOnInit() {
    this.loginService.getLoggedIn().toPromise().then((helpseeker: Helpseeker) => {
      this.matchingConfigurationService.getAllMatchingConfigurations(this.data.marketplace)
        .toPromise()
        .then((matchingConfigurations: MatchingConfiguration[]) => {
          this.recentMatchingConfigurations = matchingConfigurations;
          if (this.recentMatchingConfigurations.length > 5) {
            this.recentMatchingConfigurations.slice(0, 5);
          }
          this.loaded = true;
        });

    });
  }

  itemSelected(event: any, matchingConfiguration: MatchingConfiguration) {
    this.data.matchingConfiguration = matchingConfiguration;
    this.dialogRef.close(this.data);
  }

  onNoClick(): void {
    this.dialogRef.close();
  }







}

