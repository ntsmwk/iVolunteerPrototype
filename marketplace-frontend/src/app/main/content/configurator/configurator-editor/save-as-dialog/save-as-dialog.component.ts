import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Configurator } from 'app/main/content/_model/meta/Configurator';
import { ConfiguratorService } from 'app/main/content/_service/meta/core/configurator/configurator.service';
import { Marketplace } from 'app/main/content/_model/marketplace';
import { isNullOrUndefined } from 'util';
import { Volunteer } from 'app/main/content/_model/volunteer';
import { LoginService } from 'app/main/content/_service/login.service';

export interface SaveAsDialogData {
  configurator: Configurator;
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
    private configuratorService: ConfiguratorService,
    private loginService: LoginService,
  ) {
  }

  selected: string;
  configurators: Configurator[];
  recentConfigurators: Configurator[];
  loaded = false;

  ngOnInit() {
    console.log(this.data.marketplace);
    this.loginService.getLoggedIn().toPromise().then((volunteer: Volunteer) => {

      this.configuratorService.getAllConfiguratorsSortedDesc(this.data.marketplace).toPromise().then((configurators: Configurator[]) => {
        console.log('init dialog open');
        console.log(configurators);

        this.configurators = configurators.filter(c => {
          return c.userId === volunteer.id || isNullOrUndefined(c.userId);
        });

        console.log(this.configurators);
        if (this.configurators.length > 5) {
          this.recentConfigurators = this.configurators.slice(0, 5);
        }
        this.recentConfigurators = this.configurators;
        this.loaded = true;


      });
    });
  }

  itemSelected(event: any, c: Configurator) {
    console.log(event);
    console.log(c);
    this.data.configurator = c;
    // this.data = s;
    this.dialogRef.close(this.data)


  }

  onNoClick(): void {
    this.dialogRef.close();
  }







}


