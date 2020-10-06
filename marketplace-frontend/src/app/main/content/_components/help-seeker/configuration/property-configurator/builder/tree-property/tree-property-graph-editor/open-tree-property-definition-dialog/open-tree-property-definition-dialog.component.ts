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

export class OpenTreePropertyDefinitionDialogData {
  tenantAdmin: User;
  marketplace: Marketplace;

  treePropertyDefinitions: TreePropertyDefinition;
}

@Component({
  selector: "open-tree-property-definition-dialog",
  templateUrl: "./open-tree-property-definition-dialog.component.html",
  styleUrls: ["./open-tree-property-definition-dialog.component.scss"],
})
export class OpenTreePropertyDefinitionDialogComponent implements OnInit {
  treePropertyDefinitions: TreePropertyDefinition[];
  loaded: boolean;
  tenant: Tenant;

  constructor(
    public dialogRef: MatDialogRef<OpenTreePropertyDefinitionDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: OpenTreePropertyDefinitionDialogData,
    private treePropertyDefinitionService: TreePropertyDefinitionService,
    private loginService: LoginService
  ) {}

  async ngOnInit() {
    const globalInfo = this.loginService.getGlobalInfo();
    this.tenant = globalInfo.currentTenants[0];
    this.treePropertyDefinitionService
      .getAllPropertyDefinitionsForTenant(this.data.marketplace, this.tenant.id)
      .toPromise()
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
