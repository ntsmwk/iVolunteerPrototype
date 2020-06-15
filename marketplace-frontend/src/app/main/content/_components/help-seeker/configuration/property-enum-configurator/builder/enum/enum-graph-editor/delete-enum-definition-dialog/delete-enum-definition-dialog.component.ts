import { OnInit, Component, Input, Output, EventEmitter, Inject } from '@angular/core';
import { Marketplace } from 'app/main/content/_model/marketplace';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { EnumDefinition } from 'app/main/content/_model/meta/enum';
import { EnumDefinitionService } from 'app/main/content/_service/meta/core/enum/enum-configuration.service';
import { Helpseeker } from 'app/main/content/_model/helpseeker';
import { isNullOrUndefined } from 'util';

export class DeleteEnumDefinitionDialogData {

  helpseeker: Helpseeker;
  marketplace: Marketplace;
  idsToDelete: string[];
}

@Component({
  selector: 'delete-enum-definition-dialog',
  templateUrl: './delete-enum-definition-dialog.component.html',
  styleUrls: ['./delete-enum-definition-dialog.component.scss'],
})
export class DeleteEnumDefinitionDialogComponent implements OnInit {

  enumDefinitions: EnumDefinition[];
  loaded: boolean;
  checkboxStates: boolean[];


  constructor(
    public dialogRef: MatDialogRef<DeleteEnumDefinitionDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: DeleteEnumDefinitionDialogData,
    private enumDefinitionService: EnumDefinitionService
  ) {

  }

  ngOnInit() {

    this.data.idsToDelete = [];

    this.enumDefinitionService.getAllEnumDefinitionsForTenant(this.data.marketplace, this.data.helpseeker.tenantId).toPromise().then((enumDefinitions: EnumDefinition[]) => {
      if (!isNullOrUndefined(enumDefinitions)) {
        this.enumDefinitions = enumDefinitions;
        this.checkboxStates = Array(enumDefinitions.length);
        this.checkboxStates.fill(false);
        this.loaded = true;
      }
    });
  }

  handleRowClick(entry: any) {
    console.log(entry);
  }

  onSubmit() {
    console.log(this.data.idsToDelete);
    this.enumDefinitionService.deleteEnumDefinitions(this.data.marketplace, this.data.idsToDelete).toPromise().then(() => {
      this.dialogRef.close(undefined);
    });
  }

  handleCancelClick() {
  }

  hasNoEnumDefinitions() {
    return isNullOrUndefined(this.enumDefinitions) || this.enumDefinitions.length <= 0;
  }

  handleCheckboxClicked(checked: boolean, entry: EnumDefinition, index?: number) {
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

  handleCheckboxRowClicked(event: any, index: number, entry: EnumDefinition) {
    event.stopPropagation();
    this.handleCheckboxClicked(!this.checkboxStates[index], entry, index);
  }


}
