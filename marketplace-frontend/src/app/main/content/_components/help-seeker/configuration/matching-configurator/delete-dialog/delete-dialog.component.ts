import { OnInit, Component, Inject } from "@angular/core";
import { Marketplace } from "app/main/content/_model/marketplace";
import { MatchingConfiguration } from "app/main/content/_model/meta/configurations";
import { LoginService } from "app/main/content/_service/login.service";
import { MatchingConfigurationService } from "app/main/content/_service/configuration/matching-configuration.service";

import { MAT_DIALOG_DATA, MatDialogRef } from "@angular/material";
import { isNullOrUndefined } from "util";
import { User } from "app/main/content/_model/user";

export class DeleteMatchingDialogData {
  idsToDelete: string[];

  marketplace: Marketplace;
}

@Component({
  selector: "delete-matching-dialog",
  templateUrl: "./delete-dialog.component.html",
  styleUrls: ["./delete-dialog.component.scss"],
})
export class DeleteMatchingDialogComponent implements OnInit {
  allMatchingConfigurations: MatchingConfiguration[];
  checkboxStates: boolean[];

  loaded: boolean;

  constructor(
    public dialogRef: MatDialogRef<DeleteMatchingDialogData>,
    @Inject(MAT_DIALOG_DATA) public data: DeleteMatchingDialogData,
    private loginService: LoginService,
    private matchingConfigurationService: MatchingConfigurationService
  ) {}

  ngOnInit() {
    this.data.idsToDelete = [];
    this.loginService
      .getLoggedIn()
      .toPromise()
      .then(() => {
        this.matchingConfigurationService
          .getAllMatchingConfigurations(this.data.marketplace)
          .toPromise()
          .then((matchingConfigurations: MatchingConfiguration[]) => {
            this.allMatchingConfigurations = matchingConfigurations;
            this.checkboxStates = Array(matchingConfigurations.length);
            this.checkboxStates.fill(false);
            this.loaded = true;
          });
      });
  }

  handleCheckboxClicked(
    checked: boolean,
    entry: MatchingConfiguration,
    index?: number
  ) {
    if (!isNullOrUndefined(index)) {
      this.checkboxStates[index] = checked;
    }

    if (checked) {
      this.data.idsToDelete.push(entry.id);
    } else {
      const deleteIndex = this.data.idsToDelete.findIndex(
        (e) => e === entry.id
      );
      this.data.idsToDelete.splice(deleteIndex, 1);
    }
  }

  handleCheckboxRowClicked(
    event: any,
    index: number,
    entry: MatchingConfiguration
  ) {
    event.stopPropagation();
    this.handleCheckboxClicked(!this.checkboxStates[index], entry, index);
  }

  onSubmit() {
    this.matchingConfigurationService
      .deleteMatchingConfigurations(
        this.data.marketplace,
        this.data.idsToDelete
      )
      .toPromise()
      .then((ret) => {});

    this.dialogRef.close(this.data);
  }
}
