import { OnInit, Component, Inject } from "@angular/core";
import { Marketplace } from "app/main/content/_model/marketplace";
import { MatDialogRef, MAT_DIALOG_DATA } from "@angular/material";
import { TreePropertyDefinition } from "app/main/content/_model/meta/property/tree-property";
import { TreePropertyDefinitionService } from "app/main/content/_service/meta/core/property/tree-property-definition.service";

import { isNullOrUndefined } from "util";
import { User } from "app/main/content/_model/user";
import { GlobalInfo } from "app/main/content/_model/global-info";
import { LoginService } from "app/main/content/_service/login.service";
import { Tenant } from "app/main/content/_model/tenant";

export class DeleteTreePropertyDefinitionDialogData {
  tenantAdmin: User;
  marketplace: Marketplace;
  idsToDelete: string[];
}

@Component({
  selector: "delete-tree-property-definition-dialog",
  templateUrl: "./delete-tree-property-definition-dialog.component.html",
  styleUrls: ["./delete-tree-property-definition-dialog.component.scss"],
})
export class DeleteTreePropertyDefinitionDialogComponent implements OnInit {
  treePropertyDefinitions: TreePropertyDefinition[];
  loaded: boolean;
  checkboxStates: boolean[];
  tenant: Tenant;

  constructor(
    public dialogRef: MatDialogRef<DeleteTreePropertyDefinitionDialogComponent>,
    @Inject(MAT_DIALOG_DATA)
    public data: DeleteTreePropertyDefinitionDialogData,
    private treePropertyDefinitionService: TreePropertyDefinitionService,
    private loginService: LoginService
  ) {}

  async ngOnInit() {
    let globalInfo = this.loginService.getGlobalInfo();
    this.tenant = globalInfo.currentTenants[0];
    this.data.idsToDelete = [];

    this.treePropertyDefinitionService
      .getAllPropertyDefinitionsForTenant(this.data.marketplace, this.tenant.id)
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
    console.log(this.data.idsToDelete);
    this.treePropertyDefinitionService
      .deletePropertyDefinitions(this.data.marketplace, this.data.idsToDelete)
      .toPromise()
      .then(() => {
        this.dialogRef.close(undefined);
      });
  }

  handleCancelClick() {}

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

  handleCheckboxRowClicked(
    event: any,
    index: number,
    entry: TreePropertyDefinition
  ) {
    event.stopPropagation();
    this.handleCheckboxClicked(!this.checkboxStates[index], entry, index);
  }
}
