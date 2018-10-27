import {Component} from '@angular/core';
import {MatDialogRef} from '@angular/material';

import {Dashlet} from '../_model/dashlet';
import {DashletEntry, DashletsConf} from './dashlets.config';
import {isArray, isNullOrUndefined} from 'util';

@Component({
  templateUrl: './dashlet-selector.dialog.html'
})
export class FuseDashletSelectorDialog {

  dashletEntries = DashletsConf.getDashletEntries();
  selectedDashletEntry: DashletEntry;

  constructor(private dialogRef: MatDialogRef<FuseDashletSelectorDialog>) {
  }

  closeDialog() {
    if (isNullOrUndefined(this.selectedDashletEntry)) {
      this.dialogRef.close();
    }
    this.dialogRef.close(this.buildDashlet(this.selectedDashletEntry));
  }

  private buildDashlet(dashletEntry: DashletEntry) {
    const dashlet = new Dashlet();
    dashlet.id = dashletEntry.id;
    dashlet.type = dashletEntry.type as string;
    dashlet.name = dashletEntry.name;
    dashlet.cols = dashletEntry.cols;
    dashlet.minItemCols = dashletEntry.cols;
    dashlet.rows = dashletEntry.rows;
    dashlet.minItemRows = dashletEntry.rows;
    if (!isNullOrUndefined(dashletEntry.settings)) {
      dashlet.settings = this.buildDashletSettings(dashletEntry.settings);
    }
    return dashlet;
  }

  private buildDashletSettings(dashletEntrySettings: any) {
    const dashletSettings = {};

    Object.entries(dashletEntrySettings).forEach((entry: [string, any]) => {
      dashletSettings[entry[0]] = isArray(entry[1]) ? entry[1][0] : entry[1];
    });

    return dashletSettings;
  }
}


