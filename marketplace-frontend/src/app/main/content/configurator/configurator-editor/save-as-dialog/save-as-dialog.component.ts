import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from "@angular/material";
import { Configurator } from 'app/main/content/_model/meta/Configurator';
import { ConfiguratorService } from 'app/main/content/_service/meta/core/configurator/configurator.service';
import { Marketplace } from 'app/main/content/_model/marketplace';

export interface SaveAsDialogData {
  configurator: Configurator;
  marketplace: Marketplace;
}

@Component({
  selector: 'save-as-dialog',
  templateUrl: './save-as-dialog.component.html',
  styleUrls:['./save-as-dialog.component.scss']
})
export class SaveAsDialogComponent implements OnInit{
  
  constructor(
    public dialogRef: MatDialogRef<SaveAsDialogComponent>, 
    @Inject(MAT_DIALOG_DATA) public data: SaveAsDialogData,
    private configuratorService: ConfiguratorService,
    ) {
  }

  selected: string;
  configurators: Configurator[];
  recentConfigurators: Configurator[];
  loaded: boolean = false;
  
  ngOnInit() {
    console.log(this.data.marketplace);
    
    this.configuratorService.getAllConfiguratorsSortedDesc(this.data.marketplace).toPromise().then((configurators: Configurator[]) => {
      console.log("init dialog open");
      console.log(configurators);
      this.configurators = configurators;
      this.recentConfigurators = this.configurators.slice(0, 5);

      this.loaded = true;
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


