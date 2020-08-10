import { OnInit, Component, Inject, } from '@angular/core';
import { Marketplace } from 'app/main/content/_model/marketplace';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { TreePropertyDefinition } from 'app/main/content/_model/meta/property/tree-property';
import { TreePropertyDefinitionService } from 'app/main/content/_service/meta/core/property/tree-property-definition.service';
import { isNullOrUndefined } from 'util';
import { User } from 'app/main/content/_model/user';
import { GlobalInfo } from 'app/main/content/_model/global-info';
import { LoginService } from 'app/main/content/_service/login.service';
import { Tenant } from 'app/main/content/_model/tenant';

export class OpenTreePropertyDefinitionDialogData {
  tenantAdmin: User;
  marketplace: Marketplace;

  enumDefinition: TreePropertyDefinition;
}

@Component({
  selector: "open-tree-property-definition-dialog",
  templateUrl: './open-tree-property-definition-dialog.component.html',
  styleUrls: ['./open-tree-property-definition-dialog.component.scss'],
})
export class OpenTreePropertyDefinitionDialogComponent implements OnInit {
  enumDefinitions: TreePropertyDefinition[];
  loaded: boolean;
  tenant: Tenant;

  constructor(
    public dialogRef: MatDialogRef<OpenTreePropertyDefinitionDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: OpenTreePropertyDefinitionDialogData,
    private enumDefinitionService: TreePropertyDefinitionService,
    private loginService: LoginService
  ) { }

  async ngOnInit() {
    const globalInfo = <GlobalInfo>(
      await this.loginService.getGlobalInfo().toPromise()
    );
    this.tenant = globalInfo.tenants[0];
    this.enumDefinitionService
      .getAllPropertyDefinitionsForTenant(this.data.marketplace, this.tenant.id)
      .toPromise()
      .then((enumDefinitions: TreePropertyDefinition[]) => {
        if (!isNullOrUndefined(enumDefinitions)) {
          this.enumDefinitions = enumDefinitions;
          this.loaded = true;
        }
      });
  }

  handleRowClick(entry: TreePropertyDefinition) {
    this.data.enumDefinition = entry;
    this.dialogRef.close(this.data);
  }

  handleCancelClick() {
    this.dialogRef.close(undefined);
  }

  hasNoEnumDefinitions() {
    return (
      isNullOrUndefined(this.enumDefinitions) ||
      this.enumDefinitions.length <= 0
    );
  }
}
