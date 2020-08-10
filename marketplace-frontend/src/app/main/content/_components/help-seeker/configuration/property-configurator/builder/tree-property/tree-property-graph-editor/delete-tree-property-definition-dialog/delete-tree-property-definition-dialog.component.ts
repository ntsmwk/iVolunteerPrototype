import {
  OnInit,
  Component,
  Inject,
} from "@angular/core";
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
  enumDefinitions: TreePropertyDefinition[];
  loaded: boolean;
  checkboxStates: boolean[];
  tenant: Tenant;

  constructor(
    public dialogRef: MatDialogRef<DeleteTreePropertyDefinitionDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: DeleteTreePropertyDefinitionDialogData,
    private enumDefinitionService: TreePropertyDefinitionService,
    private loginService: LoginService
  ) { }

  async ngOnInit() {
    let globalInfo = <GlobalInfo>(
      await this.loginService.getGlobalInfo().toPromise()
    );
    this.tenant = globalInfo.tenants[0];
    this.data.idsToDelete = [];

    this.enumDefinitionService
      .getAllPropertyDefinitionsForTenant(this.data.marketplace, this.tenant.id)
      .toPromise()
      .then((enumDefinitions: TreePropertyDefinition[]) => {
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
    this.enumDefinitionService
      .deletePropertyDefinitions(this.data.marketplace, this.data.idsToDelete)
      .toPromise()
      .then(() => {
        this.dialogRef.close(undefined);
      });
  }

  handleCancelClick() { }

  hasNoEnumDefinitions() {
    return (
      isNullOrUndefined(this.enumDefinitions) ||
      this.enumDefinitions.length <= 0
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
