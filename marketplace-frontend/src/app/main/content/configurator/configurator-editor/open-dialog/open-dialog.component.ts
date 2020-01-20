import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from "@angular/material/dialog";
import { Configurator } from 'app/main/content/_model/meta/Configurator';
import { ConfiguratorService } from 'app/main/content/_service/meta/core/configurator/configurator.service';
import { Marketplace } from 'app/main/content/_model/marketplace';
import { isNullOrUndefined } from 'util';
import { LoginService } from 'app/main/content/_service/login.service';
import { Helpseeker } from 'app/main/content/_model/helpseeker';

export interface OpenDialogData {
  configurator: Configurator;
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
    private configuratorService: ConfiguratorService,
    private loginService: LoginService,
  ) {
  }

  selected: string;
  configurators: Configurator[];
  recentConfigurators: Configurator[];
  loaded = false;

  ngOnInit() {
    this.loginService.getLoggedIn().toPromise().then((helpseeker: Helpseeker) => {
      this.configuratorService.getAllConfiguratorsSortedDesc(this.data.marketplace).toPromise().then((configurators: Configurator[]) => {
        console.log(configurators);
        this.configurators = configurators.filter(c => {
          return c.userId === helpseeker.id || isNullOrUndefined(c.userId);
        });

        console.log(this.configurators)
        if (this.configurators.length > 5) {
          this.recentConfigurators = this.configurators.slice(0, 5);
        }
        this.recentConfigurators = this.configurators;
        this.loaded = true;
      });
    });
  }

  itemSelected(event: any, c: Configurator) {
    this.data.configurator = c;
    this.dialogRef.close(this.data)


  }

  onNoClick(): void {
    this.dialogRef.close();
  }







}


