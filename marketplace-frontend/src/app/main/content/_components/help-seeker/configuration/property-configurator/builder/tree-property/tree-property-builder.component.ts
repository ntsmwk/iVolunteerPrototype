import { Component, OnInit, Input, EventEmitter, Output } from '@angular/core';
import { FormGroup, Validators, FormBuilder } from '@angular/forms';
import { Marketplace } from 'app/main/content/_model/marketplace';

import { TreePropertyDefinition } from 'app/main/content/_model/meta/property/tree-property';
import { TreePropertyDefinitionService } from 'app/main/content/_service/meta/core/property/tree-property-definition.service';
import { isNullOrUndefined } from 'util';
import { MatDialog } from '@angular/material';
import { ActivatedRoute } from '@angular/router';
import { User } from 'app/main/content/_model/user';
import { LoginService } from 'app/main/content/_service/login.service';
import { GlobalInfo } from 'app/main/content/_model/global-info';
import { Tenant } from 'app/main/content/_model/tenant';

@Component({
  selector: "app-tree-property-builder",
  templateUrl: './tree-property-builder.component.html',
  styleUrls: ['./tree-property-builder.component.scss'],
  // providers: [DialogFactoryDirective]
})
export class TreePropertyBuilderComponent implements OnInit {
  constructor(
    public dialog: MatDialog,
    private route: ActivatedRoute,
    private formBuilder: FormBuilder,
    // private dialogFactory: DialogFactoryDirective,
    private enumDefinitionService: TreePropertyDefinitionService,
    private loginService: LoginService
  ) { }

  @Input() marketplace: Marketplace;
  @Input() tenantAdmin: User;
  @Input() entryId: string;
  @Input() sourceString: string;
  @Output() result: EventEmitter<{
    builderType: string;
    value: TreePropertyDefinition;
  }> = new EventEmitter();
  @Output() management: EventEmitter<String> = new EventEmitter();

  form: FormGroup;
  enumDefinition: TreePropertyDefinition;
  showEditor: boolean;
  multipleToggled: boolean;

  loaded: boolean;

  tenant: Tenant;

  async ngOnInit() {
    const globalInfo = <GlobalInfo>(
      await this.loginService.getGlobalInfo().toPromise()
    );
    this.tenant = globalInfo.tenants[0];

    this.form = this.formBuilder.group({
      name: this.formBuilder.control('', Validators.required),
      description: this.formBuilder.control(''),
    });

    if (!isNullOrUndefined(this.entryId)) {
      this.enumDefinitionService
        .getPropertyDefinitionById(this.marketplace, this.entryId)
        .toPromise()
        .then((ret: TreePropertyDefinition) => {
          this.enumDefinition = ret;
          this.form.get('name').setValue(this.enumDefinition.name);
          this.form
            .get('description')
            .setValue(this.enumDefinition.description);
          this.multipleToggled = this.enumDefinition.multiple;
          this.showEditor = true;
          this.loaded = true;
        });
    } else {
      this.loaded = true;
    }
  }

  navigateBack() {
    window.history.back();
  }

  populateEditor() { }

  handleCreateClick() {
    this.form.controls['name'].markAsTouched();
    if (this.form.invalid) {
      this.form.markAllAsTouched();
    } else {
      this.enumDefinitionService
        .newEmptyPropertyDefinition(
          this.marketplace,
          this.form.controls['name'].value,
          this.form.controls['description'].value,
          this.multipleToggled,
          this.tenant.id
        )
        .toPromise()
        .then((enumDefinition: TreePropertyDefinition) => {
          if (!isNullOrUndefined(enumDefinition)) {
            this.enumDefinition = enumDefinition;
            this.showEditor = true;
          }
        });
    }
  }

  handleCancelClick() {
    this.result.emit(undefined);
  }

  handleResult(event: { type: string; payload: TreePropertyDefinition }) {
    if (event.type === 'save') {
      event.payload.description = this.form.controls['description'].value;
      event.payload.multiple = this.multipleToggled;

      this.enumDefinitionService
        .savePropertyDefinition(this.marketplace, event.payload)
        .toPromise()
        .then((ret: TreePropertyDefinition) => { });
    } else if (event.type === 'back') {
      this.result.emit({ builderType: 'tree', value: event.payload });
    } else if (event.type === 'saveAndBack') {
      event.payload.description = this.form.controls['description'].value;
      event.payload.multiple = this.multipleToggled;

      this.enumDefinitionService
        .savePropertyDefinition(this.marketplace, event.payload)
        .toPromise()
        .then((ret: TreePropertyDefinition) => {
          this.result.emit({ builderType: 'tree', value: ret });
        });
    }
  }

  handleManagementEvent(event: string) {
    this.management.emit(event);
  }

  // openClicked() {
  //     // this.form.controls['name'].markAsPending();

  //     this.openOpenEnumDefinitionDialog(this.marketplace, this.helpseeker).then((result: OpenEnumDefinitionDialogData) => {
  //         if (!isNullOrUndefined(result)) {
  //             this.enumDefinition = result.enumDefinition;
  //             this.showEditor = true;
  //         }
  //     });

  // }

  // deleteClicked() {
  //     // this.form.controls['name'].markAsPending();
  //     this.openDeleteEnumDefinitionDialog(this.marketplace, this.helpseeker).then((result: DeleteEnumDefinitionDialogData) => {
  //         if (!isNullOrUndefined(result)) {
  //             console.log('TODO');
  //         }
  //     });
  // }

  // openOpenEnumDefinitionDialog(marketplace: Marketplace, helpseeker: Helpseeker) {
  //     const dialogRef = this.dialog.open(OpenEnumDefinitionDialogComponent, {
  //         width: '500px',
  //         minWidth: '500px',
  //         height: '400px',
  //         minHeight: '400px',
  //         data: { marketplace: marketplace, helpseeker: helpseeker }
  //     });

  //     let returnValue: OpenEnumDefinitionDialogData;

  //     dialogRef.beforeClose().toPromise().then((result: OpenEnumDefinitionDialogData) => {
  //         returnValue = result;
  //     });

  //     return dialogRef.afterClosed().toPromise().then(() => {
  //         return returnValue;
  //     });
  // }

  // openDeleteEnumDefinitionDialog(marketplace: Marketplace, helpseeker: Helpseeker) {
  //     const dialogRef = this.dialog.open(DeleteEnumDefinitionDialogComponent, {
  //         width: '500px',
  //         minWidth: '500px',
  //         height: '400px',
  //         minHeight: '400px',
  //         data: { marketplace: marketplace, helpseeker: helpseeker }
  //     });

  //     let returnValue: DeleteEnumDefinitionDialogData;

  //     dialogRef.beforeClose().toPromise().then((result: DeleteEnumDefinitionDialogData) => {
  //         returnValue = result;
  //     });

  //     return dialogRef.afterClosed().toPromise().then(() => {
  //         return returnValue;
  //     });
  // }
}
