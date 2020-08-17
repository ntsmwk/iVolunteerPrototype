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
    private treePropertyDefinitionService: TreePropertyDefinitionService,
    private loginService: LoginService
  ) { }

  @Input() marketplace: Marketplace;
  @Input() tenantAdmin: User;
  @Input() entryId: string;
  @Input() sourceString: string;
  @Output() result: EventEmitter<{ builderType: string, value: TreePropertyDefinition }> = new EventEmitter();
  @Output() management: EventEmitter<String> = new EventEmitter();

  form: FormGroup;
  treePropertyDefinition: TreePropertyDefinition;
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
      multiple: this.formBuilder.control(''),
      required: this.formBuilder.control(''),
      requiredMessage: this.formBuilder.control(''),
    });

    if (!isNullOrUndefined(this.entryId)) {
      this.treePropertyDefinitionService
        .getPropertyDefinitionById(this.marketplace, this.entryId)
        .toPromise()
        .then((ret: TreePropertyDefinition) => {
          this.treePropertyDefinition = ret;
          this.form.get('name').setValue(this.treePropertyDefinition.name);
          this.form
            .get('description')
            .setValue(this.treePropertyDefinition.description);

          this.form.get('required').setValue(this.treePropertyDefinition.required);
          this.form.get('requiredMessage').setValue(this.treePropertyDefinition.requiredMessage);
          this.multipleToggled = this.treePropertyDefinition.multiple;
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

      const newTreePropertyDefinition = new TreePropertyDefinition(
        this.form.controls['name'].value,
        this.form.controls['description'].value,
        this.form.controls['multiple'].value,
        this.form.controls['required'].value,
        this.form.controls['requiredMessage'].value
      );

      if (!newTreePropertyDefinition.required) {
        newTreePropertyDefinition.requiredMessage = null;
      }

      this.treePropertyDefinitionService
        .newPropertyDefinition(this.marketplace, newTreePropertyDefinition, this.tenant.id)
        .toPromise()
        .then((treePropertyDefinition: TreePropertyDefinition) => {
          if (!isNullOrUndefined(treePropertyDefinition)) {
            this.treePropertyDefinition = treePropertyDefinition;
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
      this.treePropertyDefinitionService
        .savePropertyDefinition(this.marketplace, event.payload)
        .toPromise()
        .then((ret: TreePropertyDefinition) => { });

    } else if (event.type === 'back') {
      this.result.emit({ builderType: 'tree', value: event.payload });

    } else if (event.type === 'saveAndBack') {
      event.payload.description = this.form.controls['description'].value;
      event.payload.multiple = this.multipleToggled;

      this.treePropertyDefinitionService
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

  //     this.openOpentreePropertyDefinitionDialog(this.marketplace, this.helpseeker).then((result: OpentreePropertyDefinitionDialogData) => {
  //         if (!isNullOrUndefined(result)) {
  //             this.treePropertyDefinition = result.treePropertyDefinition;
  //             this.showEditor = true;
  //         }
  //     });

  // }

  // deleteClicked() {
  //     // this.form.controls['name'].markAsPending();
  //     this.openDeletetreePropertyDefinitionDialog(this.marketplace, this.helpseeker).then((result: DeletetreePropertyDefinitionDialogData) => {
  //         if (!isNullOrUndefined(result)) {
  //             console.log('TODO');
  //         }
  //     });
  // }

  // openOpentreePropertyDefinitionDialog(marketplace: Marketplace, helpseeker: Helpseeker) {
  //     const dialogRef = this.dialog.open(OpentreePropertyDefinitionDialogComponent, {
  //         width: '500px',
  //         minWidth: '500px',
  //         height: '400px',
  //         minHeight: '400px',
  //         data: { marketplace: marketplace, helpseeker: helpseeker }
  //     });

  //     let returnValue: OpentreePropertyDefinitionDialogData;

  //     dialogRef.beforeClose().toPromise().then((result: OpentreePropertyDefinitionDialogData) => {
  //         returnValue = result;
  //     });

  //     return dialogRef.afterClosed().toPromise().then(() => {
  //         return returnValue;
  //     });
  // }

  // openDeletetreePropertyDefinitionDialog(marketplace: Marketplace, helpseeker: Helpseeker) {
  //     const dialogRef = this.dialog.open(DeletetreePropertyDefinitionDialogComponent, {
  //         width: '500px',
  //         minWidth: '500px',
  //         height: '400px',
  //         minHeight: '400px',
  //         data: { marketplace: marketplace, helpseeker: helpseeker }
  //     });

  //     let returnValue: DeletetreePropertyDefinitionDialogData;

  //     dialogRef.beforeClose().toPromise().then((result: DeletetreePropertyDefinitionDialogData) => {
  //         returnValue = result;
  //     });

  //     return dialogRef.afterClosed().toPromise().then(() => {
  //         return returnValue;
  //     });
  // }
}
