import { OnInit, Component, Inject, } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { TreePropertyDefinition } from 'app/main/content/_model/configurator/property/tree-property';
import { TreePropertyDefinitionService } from 'app/main/content/_service/meta/core/property/tree-property-definition.service';
import { isNullOrUndefined } from 'util';

export class OpenTreePropertyDefinitionDialogData {
  treePropertyDefinitions: TreePropertyDefinition;
}

@Component({
  selector: "open-tree-property-definition-dialog",
  templateUrl: './open-tree-property-definition-dialog.component.html',
  styleUrls: ['./open-tree-property-definition-dialog.component.scss'],
})
export class OpenTreePropertyDefinitionDialogComponent implements OnInit {
  treePropertyDefinitions: TreePropertyDefinition[];
  loaded: boolean;
  tenantId: string;

  constructor(
    public dialogRef: MatDialogRef<OpenTreePropertyDefinitionDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: OpenTreePropertyDefinitionDialogData,
    private treePropertyDefinitionService: TreePropertyDefinitionService,
  ) { }

  async ngOnInit() {
    this.treePropertyDefinitionService
      .getAllPropertyDefinitionsForTenant(this.tenantId).toPromise()
      .then((treePropertyDefinitions: TreePropertyDefinition[]) => {
        if (!isNullOrUndefined(treePropertyDefinitions)) {
          this.treePropertyDefinitions = treePropertyDefinitions;
          this.loaded = true;
        }
      });
  }

  handleRowClick(entry: TreePropertyDefinition) {
    this.data.treePropertyDefinitions = entry;
    this.dialogRef.close(this.data);
  }

  handleCancelClick() {
    this.dialogRef.close(undefined);
  }

  hasNoTreePropertyDefinitions() {
    return (
      isNullOrUndefined(this.treePropertyDefinitions) ||
      this.treePropertyDefinitions.length <= 0
    );
  }
}
