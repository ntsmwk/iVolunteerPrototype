import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Marketplace } from 'app/main/content/_model/marketplace';
import { isNullOrUndefined } from 'util';
import { LoginService } from 'app/main/content/_service/login.service';
import { Helpseeker } from 'app/main/content/_model/helpseeker';
import { ClassConfiguration } from 'app/main/content/_model/meta/Class';
import { ClassConfigurationService } from 'app/main/content/_service/configuration/class-configuration.service';

export interface OpenDialogData {
  classConfiguration: ClassConfiguration;
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
    private loginService: LoginService,
  ) {
  }

  selected: string;
  classConfigurations: ClassConfiguration[];
  recentClassConfigurations: ClassConfiguration[];
  loaded = false;

  ngOnInit() {
    this.loginService.getLoggedIn().toPromise().then((helpseeker: Helpseeker) => {
      this.classConfigurationService.getAllClassConfigurationsSortedDesc(this.data.marketplace).toPromise().then((classConfigurations: ClassConfiguration[]) => {
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

  itemSelected(event: any, c: ClassConfiguration) {
    this.data.classConfiguration = c;
    this.dialogRef.close(this.data);


  }

  onNoClick(): void {
    this.dialogRef.close();
  }







}


