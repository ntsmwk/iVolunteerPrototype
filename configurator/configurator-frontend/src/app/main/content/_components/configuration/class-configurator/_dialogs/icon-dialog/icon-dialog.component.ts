import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { isNullOrUndefined } from 'util';
import { ClassConfiguration } from 'app/main/content/_model/configurator/configurations';

export interface ChangeIconDialogData {
  imagePath: string;
}

@Component({
  selector: 'icon-dialog',
  templateUrl: './icon-dialog.component.html',
  styleUrls: ['./icon-dialog.component.scss']
})
export class ChangeIconDialogComponent implements OnInit {

  constructor(
    public dialogRef: MatDialogRef<ChangeIconDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: ChangeIconDialogData,
  ) {
  }

  selected: string;
  classConfigurations: ClassConfiguration[];
  recentClassConfigurations: ClassConfiguration[];
  loaded = false;

  numbers: number[];

  imagePaths = [
    { label: '', path: '/assets/icons/class_editor/user/assembly.png' },
    { label: '', path: '/assets/icons/class_editor/user/geometric.png' },
    { label: '', path: '/assets/icons/class_editor/user/haubenofen.png' },
    { label: '', path: '/assets/icons/class_editor/user/incoming.png' },
    { label: '', path: '/assets/icons/class_editor/user/output.png' },
    { label: '', path: '/assets/icons/class_editor/user/logistic.png' },
    { label: '', path: '/assets/icons/class_editor/user/operational.png' },
    { label: '', path: '/assets/icons/class_editor/user/price.png' },
    { label: '', path: '/assets/icons/class_editor/user/qualitative.png' },
    { label: '', path: '/assets/icons/class_editor/user/technical.png' },
  ];

  prevTile = undefined;

  async ngOnInit() {
    this.numbers = [];

    for (let i = 0; i < 36; i++) {
      this.numbers.push(i);
    }
  }

  onSelectionClick(event: any, tile: any, imagePath: any) {
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


