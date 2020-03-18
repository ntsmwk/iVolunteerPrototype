import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Marketplace } from 'app/main/content/_model/marketplace';
import { isNullOrUndefined } from 'util';
import { Volunteer } from 'app/main/content/_model/volunteer';
import { LoginService } from 'app/main/content/_service/login.service';
import { ClassConfigurationService } from 'app/main/content/_service/configuration/class-configuration.service';
import { ClassConfiguration } from 'app/main/content/_model/configurations';

export interface SaveAsDialogData {
  classConfiguration: ClassConfiguration;
  marketplace: Marketplace;
  userId: string;
}

@Component({
  selector: 'save-as-dialog',
  templateUrl: './save-as-dialog.component.html',
  styleUrls: ['./save-as-dialog.component.scss']
})
export class SaveAsDialogComponent implements OnInit {

  constructor(
    public dialogRef: MatDialogRef<SaveAsDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: SaveAsDialogData,
    private classConfigurationService: ClassConfigurationService,
    private loginService: LoginService,
  ) {
  }

  selected: string;
  classConfigurations: ClassConfiguration[];
  recentClassConfigurations: ClassConfiguration[];
  loaded = false;

  ngOnInit() {
    console.log(this.data.marketplace);
    this.loginService.getLoggedIn().toPromise().then((volunteer: Volunteer) => {

      this.classConfigurationService.getAllClassConfigurationsSortedDesc(this.data.marketplace).toPromise().then((classConfigurations: ClassConfiguration[]) => {
        console.log('init dialog open');
        console.log(classConfigurations);

        this.classConfigurations = classConfigurations.filter(c => {
          return c.userId === volunteer.id || isNullOrUndefined(c.userId);
        });

        console.log(this.classConfigurations);
        if (this.classConfigurations.length > 5) {
          this.recentClassConfigurations = this.classConfigurations.slice(0, 5);
        }
        this.recentClassConfigurations = this.classConfigurations;
        this.loaded = true;


      });
    });
  }

  itemSelected(event: any, c: ClassConfiguration) {
    console.log(event);
    console.log(c);
    this.data.classConfiguration = c;
    // this.data = s;
    this.dialogRef.close(this.data)


  }

  onNoClick(): void {
    this.dialogRef.close();
  }







}


