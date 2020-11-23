import { OnInit, Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { TreePropertyDefinition } from 'app/main/content/_model/configurator/property/tree-property';
import { TreePropertyDefinitionService } from 'app/main/content/_service/meta/core/property/tree-property-definition.service';
import { isNullOrUndefined } from 'util';

export class DeleteTreePropertyDefinitionDialogData {
  idsToDelete: string[];
  tenantId: string;
}

@Component({
  selector: "delete-tree-property-definition-dialog",
  templateUrl: './delete-tree-property-definition-dialog.component.html',
  styleUrls: ['./delete-tree-property-definition-dialog.component.scss'],
})
export class DeleteTreePropertyDefinitionDialogComponent implements OnInit {
  treePropertyDefinitions: TreePropertyDefinition[];
  loaded: boolean;
  checkboxStates: boolean[];


  constructor(
    public dialogRef: MatDialogRef<DeleteTreePropertyDefinitionDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: DeleteTreePropertyDefinitionDialogData,
    private treePropertyDefinitionService: TreePropertyDefinitionService,
  ) { }

  async ngOnInit() {
    this.data.idsToDelete = [];

    this.treePropertyDefinitionService
      .getAllPropertyDefinitionsForTenant(this.data.tenantId)
      .toPromise()
      .then((treePropertyDefinitions: TreePropertyDefinition[]) => {
        if (!isNullOrUndefined(treePropertyDefinitions)) {
          this.treePropertyDefinitions = treePropertyDefinitions;
          this.checkboxStates = Array(treePropertyDefinitions.length);
          this.checkboxStates.fill(false);
          this.loaded = true;
        }
      });
  }

  handleRowClick(entry: any) {
    console.log(entry);
  }

  onSubmit() {
    this.treePropertyDefinitionService
      .deletePropertyDefinitions(this.data.idsToDelete)
      .toPromise()
      .then(() => {
        this.dialogRef.close(undefined);
      });
  }

  handleCancelClick() { }

  hasNoTreePropertyDefinitions() {
    return (
      isNullOrUndefined(this.treePropertyDefinitions) ||
      this.treePropertyDefinitions.length <= 0
    );
  }

  handleCheckboxClicked(
    checked: boolean,
    entry: TreePropertyDefinition,
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

  handleCheckboxRowClicked(event: any, index: number, entry: TreePropertyDefinition) {
    event.stopPropagation();
    this.handleCheckboxClicked(!this.checkboxStates[index], entry, index);
  }
}
