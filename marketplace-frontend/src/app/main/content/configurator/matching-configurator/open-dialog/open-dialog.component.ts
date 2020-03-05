import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Configurator } from 'app/main/content/_model/meta/Configurator';
import { ConfiguratorService } from 'app/main/content/_service/meta/core/configurator/configurator.service';
import { Marketplace } from 'app/main/content/_model/marketplace';
import { isNullOrUndefined } from 'util';
import { LoginService } from 'app/main/content/_service/login.service';
import { Helpseeker } from 'app/main/content/_model/helpseeker';
import { MatchingOperatorRelationshipStorageService } from 'app/main/content/_service/matchingoperator-relationship-storage.service';
import { MatchingConfigurator } from 'app/main/content/_model/matching';

export interface OpenMatchingDialogData {
  marketplace: Marketplace;
  matchingConfigurator: MatchingConfigurator;
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
    private matchingOperatorRelationshipStorageService: MatchingOperatorRelationshipStorageService,
    private loginService: LoginService,
  ) {
  }

  recentMatchingConfigurators: MatchingConfigurator[];
  loaded = false;

  ngOnInit() {
    this.loginService.getLoggedIn().toPromise().then((helpseeker: Helpseeker) => {
      this.matchingOperatorRelationshipStorageService.getAllMatchingOperatorRelationshipStorages(this.data.marketplace)
        .toPromise()
        .then((matchingConfigurators: MatchingConfigurator[]) => {
          this.recentMatchingConfigurators = matchingConfigurators;
          if (this.recentMatchingConfigurators.length > 5) {
            this.recentMatchingConfigurators.slice(0, 5);
          }
          this.loaded = true;
        });

    });
  }

  itemSelected(event: any, matchingConfigurator: MatchingConfigurator) {
    this.data.matchingConfigurator = matchingConfigurator;
    this.dialogRef.close(this.data);
  }

  onNoClick(): void {
    this.dialogRef.close();
  }







}


