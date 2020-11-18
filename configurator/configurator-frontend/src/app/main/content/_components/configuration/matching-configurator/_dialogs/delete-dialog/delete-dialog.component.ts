import { OnInit, Component, Inject } from '@angular/core';
import { MatchingConfiguration } from 'app/main/content/_model/configurator/configurations';
import { MatchingConfigurationService } from 'app/main/content/_service/configuration/matching-configuration.service';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';
import { isNullOrUndefined } from 'util';
import { ResponseService } from 'app/main/content/_service/response.service';

export class DeleteMatchingDialogData {
  idsToDelete: string[];
  tenantId: string;
  redirectUrl: string;
}

@Component({
  selector: "delete-matching-dialog",
  templateUrl: './delete-dialog.component.html',
  styleUrls: ['./delete-dialog.component.scss'],
})
export class DeleteMatchingDialogComponent implements OnInit {
  allMatchingConfigurations: MatchingConfiguration[];
  checkboxStates: boolean[];

  loaded: boolean;

  constructor(
    public dialogRef: MatDialogRef<DeleteMatchingDialogData>,
    @Inject(MAT_DIALOG_DATA) public data: DeleteMatchingDialogData,
    private matchingConfigurationService: MatchingConfigurationService,
    private responseService: ResponseService,
  ) { }

  async ngOnInit() {
    this.data.idsToDelete = [];

    this.matchingConfigurationService.getAllMatchingConfigurationsByTenantId(this.data.tenantId)
      .toPromise().then((matchingConfigurations: MatchingConfiguration[]) => {
        this.allMatchingConfigurations = matchingConfigurations;
        this.checkboxStates = Array(matchingConfigurations.length);
        this.checkboxStates.fill(false);
        this.loaded = true;
      });
  }

  handleCheckboxClicked(checked: boolean, entry: MatchingConfiguration, index?: number) {
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

  handleCheckboxRowClicked(event: any, index: number, entry: MatchingConfiguration) {
    event.stopPropagation();
    this.handleCheckboxClicked(!this.checkboxStates[index], entry, index);
  }

  onSubmit() {
    this.responseService.sendMatchingConfiguratorResponse(this.data.redirectUrl, null, this.data.idsToDelete, 'delete').toPromise().then(() => {
      this.matchingConfigurationService.deleteMatchingConfigurations(this.data.idsToDelete).toPromise().then((ret) => {
        this.dialogRef.close(this.data);

      });
    }).catch(error => {
      console.log("error - rollback");
      console.log(error);
    });

  }
}
