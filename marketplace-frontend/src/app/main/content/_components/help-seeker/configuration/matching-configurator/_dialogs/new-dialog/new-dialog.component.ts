import { Component, Inject, OnInit, ElementRef, ViewChild } from "@angular/core";
import { MatDialogRef, MAT_DIALOG_DATA } from "@angular/material/dialog";
import { isNullOrUndefined } from "util";
import { LoginService } from "app/main/content/_service/login.service";
import { MatchingConfigurationService } from "app/main/content/_service/configuration/matching-configuration.service";
import { ClassConfigurationService } from "app/main/content/_service/configuration/class-configuration.service";
import {
  ClassConfiguration,
  MatchingConfiguration,
} from "app/main/content/_model/meta/configurations";
import { ClassBrowseSubDialogData } from "../../../class-configurator/_dialogs/browse-sub-dialog/browse-sub-dialog.component";
import { GlobalInfo } from "app/main/content/_model/global-info";
import { Tenant } from "app/main/content/_model/tenant";
import { FormGroup, FormBuilder, FormControl, Validators } from '@angular/forms';

export interface NewMatchingDialogData {
  leftClassConfiguration: ClassConfiguration;
  rightClassConfiguration: ClassConfiguration;
  label: string;
}

@Component({
  selector: "new-matching-dialog",
  templateUrl: "./new-dialog.component.html",
  styleUrls: ["./new-dialog.component.scss"],
})
export class NewMatchingDialogComponent implements OnInit {
  constructor(
    public dialogRef: MatDialogRef<NewMatchingDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: NewMatchingDialogData,
    private classConfigurationService: ClassConfigurationService,
    private matchingConfigurationService: MatchingConfigurationService,
    private loginService: LoginService,
  ) { }

  allClassConfigurations: ClassConfiguration[];
  recentClassConfigurations: ClassConfiguration[];
  loaded = false;
  showErrors = false;
  showDuplicateError = false;

  browseMode: boolean;
  browseDialogData: ClassBrowseSubDialogData;

  dialogForm: FormGroup;


  tenant: Tenant;
  globalInfo: GlobalInfo;

  async ngOnInit() {
    this.globalInfo = <GlobalInfo>(
      await this.loginService.getGlobalInfo().toPromise()
    );
    this.tenant = this.globalInfo.tenants[0];

    this.dialogForm = new FormGroup({
      label: new FormControl('', Validators.required)
    });


    this.classConfigurationService
      .getClassConfigurationsByTenantId(this.globalInfo.marketplace, this.tenant.id)
      .toPromise()
      .then((classConfigurations: ClassConfiguration[]) => {
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
    this.data.leftClassConfiguration = c;
  }

  rightItemSelected(c: ClassConfiguration) {
    this.data.rightClassConfiguration = c;
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  onOKClick() {
    this.showDuplicateError = false;
    this.data.label = this.dialogForm.controls['label'].value;
    if (
      !isNullOrUndefined(this.data.leftClassConfiguration) &&
      !isNullOrUndefined(this.data.rightClassConfiguration) &&
      !isNullOrUndefined(this.data.label)
    ) {
      this.matchingConfigurationService.getMatchingConfigurationByUnorderedClassConfigurationIds(
        this.globalInfo.marketplace, this.data.leftClassConfiguration.id, this.data.rightClassConfiguration.id)
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

  handleBrowseClick(sourceReference: "LEFT" | "RIGHT") {
    this.browseDialogData = new ClassBrowseSubDialogData();
    this.browseDialogData.title = "Durchsuchen";
    this.browseDialogData.globalInfo = this.globalInfo;
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


  handleReturnFromBrowse(event: { cancelled: boolean; entryId: string; sourceReference: "LEFT" | "RIGHT"; }) {
    if (!event.cancelled) {
      const classConfiguration = this.allClassConfigurations.find(
        (c) => c.id === event.entryId
      );

      if (event.sourceReference === "LEFT") {
        this.data.leftClassConfiguration = classConfiguration;
      } else if (event.sourceReference === "RIGHT") {
        this.data.rightClassConfiguration = classConfiguration;
      }
    }

    this.browseMode = false;
  }
}
