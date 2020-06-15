import { OnInit, Component, Input, Output, EventEmitter, Inject } from '@angular/core';
import { Marketplace } from 'app/main/content/_model/marketplace';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { EnumDefinition } from 'app/main/content/_model/meta/enum';
import { EnumDefinitionService } from 'app/main/content/_service/meta/core/enum/enum-configuration.service';
import { Helpseeker } from 'app/main/content/_model/helpseeker';
import { isNullOrUndefined } from 'util';

export class OpenEnumDefinitionDialogData {

  helpseeker: Helpseeker;
  marketplace: Marketplace;

  enumDefinition: EnumDefinition;
}

@Component({
  selector: 'open-enum-definition-dialog',
  templateUrl: './open-enum-definition-dialog.component.html',
  styleUrls: ['./open-enum-definition-dialog.component.scss'],
})
export class OpenEnumDefinitionDialogComponent implements OnInit {

  enumDefinitions: EnumDefinition[];
  loaded: boolean;

  constructor(
    public dialogRef: MatDialogRef<OpenEnumDefinitionDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: OpenEnumDefinitionDialogData,
    private enumDefinitionService: EnumDefinitionService
  ) {

  }

  ngOnInit() {

    this.enumDefinitionService.getAllEnumDefinitionsForTenant(this.data.marketplace, this.data.helpseeker.tenantId).toPromise().then((enumDefinitions: EnumDefinition[]) => {
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
    return isNullOrUndefined(this.enumDefinitions) || this.enumDefinitions.length <= 0;
  }


}
