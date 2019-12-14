import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from "@angular/material/dialog";
import { Configurator } from 'app/main/content/_model/meta/Configurator';
import { ConfiguratorService } from 'app/main/content/_service/meta/core/configurator/configurator.service';
import { Marketplace } from 'app/main/content/_model/marketplace';

export interface IconDialogData {
  configurator: Configurator;
  marketplace: Marketplace;
}

@Component({
  selector: 'icon-dialog',
  templateUrl: './icon-dialog.component.html',
  styleUrls:['./icon-dialog.component.scss']
})
export class IconDialogComponent implements OnInit{
  
  constructor(
    public dialogRef: MatDialogRef<IconDialogComponent>, 
    @Inject(MAT_DIALOG_DATA) public data: IconDialogData,
    ) {
  }

  selected: string;
  configurators: Configurator[];
  recentConfigurators: Configurator[];
  loaded: boolean = false;
  
  ngOnInit() {    

  }

  onNoClick(): void {
    this.dialogRef.close();
  }






  
}


