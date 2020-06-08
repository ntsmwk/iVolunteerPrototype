import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Marketplace } from 'app/main/content/_model/marketplace';
import { isNullOrUndefined } from 'util';
import { LoginService } from 'app/main/content/_service/login.service';
import { Helpseeker } from 'app/main/content/_model/helpseeker';
import { MatchingConfigurationService } from 'app/main/content/_service/configuration/matching-configuration.service';
import { ClassConfigurationService } from 'app/main/content/_service/configuration/class-configuration.service';
import { ClassConfiguration, MatchingConfiguration } from 'app/main/content/_model/meta/configurations';
import { ClassBrowseSubDialogData } from '../../class-configurator/browse-sub-dialog/browse-sub-dialog.component';

export interface NewMatchingDialogData {
  producerClassConfiguration: ClassConfiguration;
  consumerClassConfiguration: ClassConfiguration;
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
    private classConfigurationService: ClassConfigurationService,
    private matchingConfigurationService: MatchingConfigurationService,
    private loginService: LoginService,
  ) {
  }

  allClassConfigurations: ClassConfiguration[];
  recentClassConfigurations: ClassConfiguration[];
  loaded = false;
  showErrors = false;
  showDuplicateError = false;
  label: string;

  browseMode: boolean;
  browseDialogData: ClassBrowseSubDialogData;


  ngOnInit() {
    this.loginService.getLoggedIn().toPromise().then((helpseeker: Helpseeker) => {
      this.classConfigurationService.getAllClassConfigurationsSortedDesc(this.data.marketplace).toPromise().then((classConfigurations: ClassConfiguration[]) => {

        this.recentClassConfigurations = classConfigurations;
        this.allClassConfigurations = classConfigurations;

        //----DEBUG
        // this.recentMatchingConfigurations.push(...this.recentMatchingConfigurations);
        // this.recentMatchingConfigurations.push(...this.recentMatchingConfigurations);
        //----
        this.recentClassConfigurations = this.recentClassConfigurations.sort((a, b) => b.timestamp.valueOf() - a.timestamp.valueOf());

        if (this.recentClassConfigurations.length > 4) {
          this.recentClassConfigurations = this.recentClassConfigurations.slice(0, 4);
        }

        this.loaded = true;
      });
    });
  }

  producerItemSelected(event: any, c: ClassConfiguration) {
    this.data.producerClassConfiguration = c;
  }

  consumerItemSelected(event: any, c: ClassConfiguration) {
    this.data.consumerClassConfiguration = c;
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  onOKClick() {
    this.showDuplicateError = false;
    if (!isNullOrUndefined(this.data.producerClassConfiguration) &&
      !isNullOrUndefined(this.data.consumerClassConfiguration) &&
      this.data.consumerClassConfiguration !== this.data.producerClassConfiguration &&
      !isNullOrUndefined(this.data.label)) {

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

  handleBrowseClick(sourceReference: 'PRODUCER' | 'CONSUMER') {
    this.browseDialogData = new ClassBrowseSubDialogData();
    this.browseDialogData.title = 'Durchsuchen';
    this.browseDialogData.marketplace = this.data.marketplace;
    this.browseDialogData.sourceReference = sourceReference;

    this.browseDialogData.entries = [];
    for (const classConfiguration of this.allClassConfigurations) {
      this.browseDialogData.entries.push({ id: classConfiguration.id, name: classConfiguration.name, date: new Date(classConfiguration.timestamp) });
    }

    this.browseMode = true;


  }

  handleReturnFromBrowse(event: { cancelled: boolean, entryId: string, sourceReference: 'PRODUCER' | 'CONSUMER' }) {
    console.log("clicked browse");
    console.log(event);

    if (!event.cancelled) {
      const classConfiguration = this.allClassConfigurations.find(c => c.id === event.entryId);

      if (event.sourceReference === 'PRODUCER') {
        this.data.producerClassConfiguration = classConfiguration;

      } else if (event.sourceReference === 'CONSUMER') {
        this.data.consumerClassConfiguration = classConfiguration;
      }
    }

    this.browseMode = false;

  }







}


