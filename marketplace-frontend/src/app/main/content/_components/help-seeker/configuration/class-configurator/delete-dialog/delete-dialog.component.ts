import { OnInit, Component, Inject } from '@angular/core';
import { Marketplace } from 'app/main/content/_model/marketplace';
import { MatchingConfiguration, ClassConfiguration } from 'app/main/content/_model/meta/configurations';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';
import { isNullOrUndefined } from 'util';
import { ClassConfigurationService } from 'app/main/content/_service/configuration/class-configuration.service';

export class DeleteClassConfigurationDialogData {
  idsToDelete: string[];
  marketplace: Marketplace;
}

@Component({
  selector: 'delete-class-configuration-dialog',
  templateUrl: './delete-dialog.component.html',
  styleUrls: ['./delete-dialog.component.scss']
})
export class DeleteClassConfigurationDialogComponent implements OnInit {
  allClassConfigurations: ClassConfiguration[];
  checkboxStates: boolean[];
  loaded: boolean;

  constructor(
    public dialogRef: MatDialogRef<DeleteClassConfigurationDialogData>,
    @Inject(MAT_DIALOG_DATA) public data: DeleteClassConfigurationDialogData,
    private classConfigurationService: ClassConfigurationService,
  ) { }

  ngOnInit() {
    this.data.idsToDelete = [];

    this.classConfigurationService.getAllClassConfigurations(this.data.marketplace)
      .toPromise()
      .then((classConfigurations: ClassConfiguration[]) => {
        this.allClassConfigurations = classConfigurations;
        this.checkboxStates = Array(classConfigurations.length);
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
      const deleteIndex = this.data.idsToDelete.findIndex(e => e === entry.id);
      this.data.idsToDelete.splice(deleteIndex, 1);
    }
  }

  handleCheckboxRowClicked(event: any, index: number, entry: MatchingConfiguration) {
    event.stopPropagation();
    this.handleCheckboxClicked(!this.checkboxStates[index], entry, index);
  }

  onSubmit() {
    this.classConfigurationService.deleteClassConfigurations(this.data.marketplace, this.data.idsToDelete).toPromise().then((ret) => {
      this.dialogRef.close(this.data);
    });
  }

}
