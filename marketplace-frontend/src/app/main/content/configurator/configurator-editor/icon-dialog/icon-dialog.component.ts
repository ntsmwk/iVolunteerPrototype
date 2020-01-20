import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Configurator } from 'app/main/content/_model/meta/Configurator';
import { Marketplace } from 'app/main/content/_model/marketplace';
import { MatGridTile } from '@angular/material';
import { isNullOrUndefined } from 'util';

export interface ChangeIconDialogData {
  marketplace: Marketplace;
  imagePath: string;
}

@Component({
  selector: 'icon-dialog',
  templateUrl: './icon-dialog.component.html',
  styleUrls: ['./icon-dialog.component.scss']
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
  loaded = false;

  numbers: number[];

  imagePaths = [
    {label: '', path: '/assets/icons/class_editor/user/assembly.png'},
    {label: '', path: '/assets/icons/class_editor/user/geometric.png'},
    {label: '', path: '/assets/icons/class_editor/user/haubenofen.png'},
    {label: '', path: '/assets/icons/class_editor/user/incoming.png'},
    {label: '', path: '/assets/icons/class_editor/user/output.png'},
    {label: '', path: '/assets/icons/class_editor/user/logistic.png'},
    {label: '', path: '/assets/icons/class_editor/user/operational.png'},
    {label: '', path: '/assets/icons/class_editor/user/price.png'},
    {label: '', path: '/assets/icons/class_editor/user/qualitative.png'},
    {label: '', path: '/assets/icons/class_editor/user/technical.png'},



  
  ];

  prevTile = undefined;
  
  ngOnInit() {  
    this.numbers = [];  

    for (let i = 0; i < 36; i++) {
      this.numbers.push(i);
    }

  }
  onSelectionClick(event: any, tile: any, imagePath: any) {
    // console.log('selected');
    // console.log(event);
    // console.log('tile');
    // console.log(tile);
    // console.log();
    if (!isNullOrUndefined(this.prevTile)) {
      this.prevTile._element.nativeElement.style.background = 'rgb(240, 240, 240)';
    }
    this.prevTile = tile;
    tile._element.nativeElement.style.background = 'rgb(62, 125, 219)';
    this.data.imagePath = imagePath.path;

  }

  onNoClick(): void {
    this.dialogRef.close();
  }






  
}


