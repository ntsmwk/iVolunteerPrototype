import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Configurator } from 'app/main/content/_model/meta/Configurator';
import { ConfiguratorService } from 'app/main/content/_service/meta/core/configurator/configurator.service';
import { Marketplace } from 'app/main/content/_model/marketplace';
import { isNullOrUndefined } from 'util';
import { LoginService } from 'app/main/content/_service/login.service';
import { Helpseeker } from 'app/main/content/_model/helpseeker';
import { MatchingOperatorRelationshipStorageService } from 'app/main/content/_service/matchingoperator-relationship-storage.service';
import { MatchingOperatorRelationshipStorage } from 'app/main/content/_model/matching';

export interface NewMatchingDialogData {
  producerConfigurator: Configurator;
  consumerConfigurator: Configurator;
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
    private matchingOperatorRelationshipstorageService: MatchingOperatorRelationshipStorageService,
    private loginService: LoginService,
  ) {
  }

  configurators: Configurator[];
  recentConfigurators: Configurator[];
  loaded = false;
  showErrors = false;
  showDuplicateError = false;
  label: string;

  ngOnInit() {
    this.loginService.getLoggedIn().toPromise().then((helpseeker: Helpseeker) => {
      this.configuratorService.getAllConfiguratorsSortedDesc(this.data.marketplace).toPromise().then((configurators: Configurator[]) => {
        this.configurators = configurators.filter(c => {
          return c.userId === helpseeker.id || isNullOrUndefined(c.userId);
        });

        if (this.configurators.length > 5) {
          this.recentConfigurators = this.configurators.slice(0, 5);
        }

        this.recentConfigurators = this.configurators;
        this.loaded = true;
      });
    });
  }

  producerItemSelected(event: any, c: Configurator) {
    this.data.producerConfigurator = c;
  }

  consumerItemSelected(event: any, c: Configurator) {
    this.data.consumerConfigurator = c;
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  onOKClick() {
    this.showDuplicateError = false;
    if (!isNullOrUndefined(this.data.producerConfigurator) &&
      !isNullOrUndefined(this.data.consumerConfigurator) &&
      this.data.consumerConfigurator !== this.data.producerConfigurator) {

      // this.data.label = this.label;
      console.log(this.data);
      this.matchingOperatorRelationshipstorageService
        .getMatchingOperatorRelationshipByUnorderedConfiguratorIds(this.data.marketplace, this.data.producerConfigurator.id, this.data.consumerConfigurator.id)
        .toPromise()
        .then((ret: MatchingOperatorRelationshipStorage) => {
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


