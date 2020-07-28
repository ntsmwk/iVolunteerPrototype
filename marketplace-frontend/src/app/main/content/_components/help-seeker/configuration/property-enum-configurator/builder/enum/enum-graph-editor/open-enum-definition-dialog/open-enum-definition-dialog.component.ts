import {
  OnInit,
  Component,
  Input,
  Output,
  EventEmitter,
  Inject,
} from "@angular/core";
import { Marketplace } from "app/main/content/_model/marketplace";
import { MatDialogRef, MAT_DIALOG_DATA } from "@angular/material";
import { EnumDefinition } from "app/main/content/_model/meta/enum";
import { EnumDefinitionService } from "app/main/content/_service/meta/core/enum/enum-configuration.service";

import { isNullOrUndefined } from "util";
import { User, UserRole } from "app/main/content/_model/user";
import { GlobalInfo } from "app/main/content/_model/global-info";
import { LoginService } from "app/main/content/_service/login.service";
import { Tenant } from "app/main/content/_model/tenant";

export class OpenEnumDefinitionDialogData {
  helpseeker: User;
  marketplace: Marketplace;

  enumDefinition: EnumDefinition;
}

@Component({
  selector: "open-enum-definition-dialog",
  templateUrl: "./open-enum-definition-dialog.component.html",
  styleUrls: ["./open-enum-definition-dialog.component.scss"],
})
export class OpenEnumDefinitionDialogComponent implements OnInit {
  enumDefinitions: EnumDefinition[];
  loaded: boolean;
  tenant: Tenant;

  constructor(
    public dialogRef: MatDialogRef<OpenEnumDefinitionDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: OpenEnumDefinitionDialogData,
    private enumDefinitionService: EnumDefinitionService,
    private loginService: LoginService
  ) {}

  async ngOnInit() {
    let globalInfo = <GlobalInfo>(
      await this.loginService.getGlobalInfo().toPromise()
    );
    this.tenant = globalInfo.tenants[0];
    this.enumDefinitionService
      .getAllEnumDefinitionsForTenant(this.data.marketplace, this.tenant.id)
      .toPromise()
      .then((enumDefinitions: EnumDefinition[]) => {
        if (!isNullOrUndefined(enumDefinitions)) {
          this.enumDefinitions = enumDefinitions;
          this.loaded = true;
        }
      });
  }

  handleRowClick(entry: EnumDefinition) {
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
