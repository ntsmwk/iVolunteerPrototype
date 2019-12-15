import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from "@angular/material/dialog";
import { Configurator } from 'app/main/content/_model/meta/Configurator';
import { Marketplace } from 'app/main/content/_model/marketplace';

export interface ChangeIconDialogData {
  marketplace: Marketplace;
  imagePath: string;
}

@Component({
  selector: 'icon-dialog',
  templateUrl: './icon-dialog.component.html',
  styleUrls:['./icon-dialog.component.scss']
})
export class ChangeIconDialogComponent implements OnInit{
  
  constructor(
    public dialogRef: MatDialogRef<ChangeIconDialogComponent>, 
    @Inject(MAT_DIALOG_DATA) public data: ChangeIconDialogData,
    ) {
  }

  selected: string;
  configurators: Configurator[];
  recentConfigurators: Configurator[];
  loaded: boolean = false;

  numbers: number[];

  imagePaths = [
    {label: "", path: "/assets/icons/class_editor/user/assembly.png"},


  
  ]
  
  ngOnInit() {  
    this.numbers = [];  

    for (let i = 0; i < 36; i++) {
      this.numbers.push(i);
    }

  }

  onNoClick(): void {
    this.dialogRef.close();
  }






  
}


