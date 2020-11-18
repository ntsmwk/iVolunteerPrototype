import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { isNullOrUndefined } from 'util';
import { MatchingConfigurationService } from 'app/main/content/_service/configuration/matching-configuration.service';
import { ClassConfigurationService } from 'app/main/content/_service/configuration/class-configuration.service';
import { ClassConfiguration, MatchingConfiguration } from 'app/main/content/_model/configurator/configurations';
import { ClassBrowseSubDialogData } from '../../../class-configurator/_dialogs/browse-sub-dialog/browse-sub-dialog.component';
import { FormGroup, FormControl, Validators } from '@angular/forms';

export interface NewMatchingDialogData {
  leftClassConfiguration: ClassConfiguration;
  leftIsUser: boolean;
  rightClassConfiguration: ClassConfiguration;
  rightIsUser: boolean;
  label: string;
  tenantId: string;
}

@Component({
  selector: "new-matching-dialog",
  templateUrl: './new-dialog.component.html',
  styleUrls: ['./new-dialog.component.scss'],
})
export class NewMatchingDialogComponent implements OnInit {
  constructor(
    public dialogRef: MatDialogRef<NewMatchingDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: NewMatchingDialogData,
    private classConfigurationService: ClassConfigurationService,
    private matchingConfigurationService: MatchingConfigurationService,
  ) { }

  allClassConfigurations: ClassConfiguration[];
  recentClassConfigurations: ClassConfiguration[];
  loaded = false;
  showErrors = false;
  showDuplicateError = false;

  browseMode: boolean;
  browseDialogData: ClassBrowseSubDialogData;

  dialogForm: FormGroup;

  async ngOnInit() {
    this.dialogForm = new FormGroup({
      label: new FormControl('', Validators.required)
    });

    this.classConfigurationService.getAllClassConfigurationsByTenantId(this.data.tenantId)
      .toPromise().then((classConfigurations: ClassConfiguration[]) => {
        this.recentClassConfigurations = classConfigurations;
        this.allClassConfigurations = classConfigurations;

        this.recentClassConfigurations = this.recentClassConfigurations.sort(
          (a, b) => b.timestamp.valueOf() - a.timestamp.valueOf()
        );

        if (this.recentClassConfigurations.length > 4) {
          this.recentClassConfigurations = this.recentClassConfigurations.slice(0, 4);
        }
        this.loaded = true;
      });
  }

  leftItemSelected(c: ClassConfiguration) {
    this.data.leftIsUser = false;
    this.data.leftClassConfiguration = c;
  }

  rightItemSelected(c: ClassConfiguration) {
    this.data.rightIsUser = false;
    this.data.rightClassConfiguration = c;
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  onOKClick() {
    this.showDuplicateError = false;
    this.data.label = this.dialogForm.controls['label'].value;
    if (
      (!isNullOrUndefined(this.data.leftClassConfiguration) || this.data.leftIsUser) &&
      (!isNullOrUndefined(this.data.rightClassConfiguration) || this.data.rightIsUser) &&
      !isNullOrUndefined(this.data.label)
    ) {

      const leftId = this.data.leftClassConfiguration.id;
      const rightId = this.data.rightClassConfiguration.id;

      this.matchingConfigurationService.getMatchingConfigurationByUnorderedClassConfigurationIds(leftId, rightId)
        .toPromise().then((ret: MatchingConfiguration) => {
          if (isNullOrUndefined(ret)) {
            this.dialogRef.close(this.data);
          } else {
            this.showDuplicateError = true;
            this.showErrors = true;
          }
        });
    } else {
      this.showErrors = true;
    }
  }

  handleBrowseClick(sourceReference: 'LEFT' | 'RIGHT') {
    this.browseDialogData = new ClassBrowseSubDialogData();
    this.browseDialogData.title = 'Durchsuchen';
    this.browseDialogData.sourceReference = sourceReference;

    this.browseDialogData.entries = [];
    for (const classConfiguration of this.allClassConfigurations) {
      this.browseDialogData.entries.push({
        id: classConfiguration.id,
        name: classConfiguration.name,
        date: new Date(classConfiguration.timestamp),
      });
    }

    this.browseMode = true;
    this.dialogForm.updateValueAndValidity();
  }

  handleBrowseBackClick() {
    this.browseMode = false;
  }

  handleUseUserClick(sourceReference: 'LEFT' | 'RIGHT') {
    const classConfiguration = new ClassConfiguration();
    classConfiguration.id = 'user';
    classConfiguration.name = 'Benutzer';


    if (sourceReference === 'LEFT') {
      this.data.leftClassConfiguration = classConfiguration;

      this.data.leftIsUser = true;
    } else if (sourceReference === 'RIGHT') {
      this.data.rightClassConfiguration = classConfiguration;
      this.data.rightIsUser = true;
    }

  }


  handleReturnFromBrowse(event: { cancelled: boolean; entryId: string; sourceReference: 'LEFT' | 'RIGHT'; }) {
    if (!event.cancelled) {
      const classConfiguration = this.allClassConfigurations.find(
        (c) => c.id === event.entryId
      );

      if (event.sourceReference === 'LEFT') {
        this.data.leftClassConfiguration = classConfiguration;
      } else if (event.sourceReference === 'RIGHT') {
        this.data.rightClassConfiguration = classConfiguration;
      }
    }

    this.browseMode = false;
  }
}
