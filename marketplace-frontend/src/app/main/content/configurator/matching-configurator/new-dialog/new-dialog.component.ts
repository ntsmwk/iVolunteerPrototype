import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Configurator } from 'app/main/content/_model/meta/Configurator';
import { ConfiguratorService } from 'app/main/content/_service/configuration/configurator.service';
import { Marketplace } from 'app/main/content/_model/marketplace';
import { isNullOrUndefined } from 'util';
import { LoginService } from 'app/main/content/_service/login.service';
import { Helpseeker } from 'app/main/content/_model/helpseeker';
import { MatchingConfiguration } from 'app/main/content/_model/matching';
import { MatchingConfigurationService } from 'app/main/content/_service/configuration/matching-configuration.service';

export interface NewMatchingDialogData {
  producerClassConfiguration: Configurator;
  consumerClassConfiguration: Configurator;
  label: string;
  marketplace: Marketplace;
}

@Component({
  selector: 'new-matching-dialog',
  templateUrl: './new-dialog.component.html',
  styleUrls: ['./new-dialog.component.scss']
})
export class NewMatchingDialogComponent implements OnInit {

  constructor(
    public dialogRef: MatDialogRef<NewMatchingDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: NewMatchingDialogData,
    private configuratorService: ConfiguratorService,
    private matchingConfigurationService: MatchingConfigurationService,
    private loginService: LoginService,
  ) {
  }

  classConfigurations: Configurator[];
  recentClassConfigurations: Configurator[];
  loaded = false;
  showErrors = false;
  showDuplicateError = false;
  label: string;

  ngOnInit() {
    this.loginService.getLoggedIn().toPromise().then((helpseeker: Helpseeker) => {
      this.configuratorService.getAllConfiguratorsSortedDesc(this.data.marketplace).toPromise().then((classConfigurations: Configurator[]) => {
        this.classConfigurations = classConfigurations.filter(c => {
          return c.userId === helpseeker.id || isNullOrUndefined(c.userId);
        });

        if (this.classConfigurations.length > 5) {
          this.recentClassConfigurations = this.classConfigurations.slice(0, 5);
        }

        this.recentClassConfigurations = this.classConfigurations;

        this.loaded = true;
      });
    });
  }

  producerItemSelected(event: any, c: Configurator) {
    this.data.producerClassConfiguration = c;
  }

  consumerItemSelected(event: any, c: Configurator) {
    this.data.consumerClassConfiguration = c;
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  onOKClick() {
    console.log(this.data);
    this.showDuplicateError = false;
    if (!isNullOrUndefined(this.data.producerClassConfiguration) &&
      !isNullOrUndefined(this.data.consumerClassConfiguration) &&
      this.data.consumerClassConfiguration !== this.data.producerClassConfiguration) {

      // this.data.label = this.label;
      console.log(this.data);
      this.matchingConfigurationService
        .getMatchingConfigurationByUnorderedClassConfigurationIds(this.data.marketplace, this.data.producerClassConfiguration.id, this.data.consumerClassConfiguration.id)
        .toPromise()
        .then((ret: MatchingConfiguration) => {
          if (isNullOrUndefined(ret)) {
            this.dialogRef.close(this.data);
          } else {
            this.showDuplicateError = true;
            this.showErrors = true;
          }
        });

    } else {
      this.showErrors = true;
    }
  }







}


