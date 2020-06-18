import { Component, OnInit, Input, EventEmitter, Output } from '@angular/core';
import { FormGroup, Validators, FormBuilder } from '@angular/forms';
import { Marketplace } from 'app/main/content/_model/marketplace';
import { Helpseeker } from 'app/main/content/_model/helpseeker';
import { EnumDefinition } from 'app/main/content/_model/meta/enum';
import { EnumDefinitionService } from 'app/main/content/_service/meta/core/enum/enum-configuration.service';
import { isNullOrUndefined } from 'util';
import { OpenEnumDefinitionDialogData, OpenEnumDefinitionDialogComponent } from './enum-graph-editor/open-enum-definition-dialog/open-enum-definition-dialog.component';
import { MatDialog } from '@angular/material';
import { DeleteEnumDefinitionDialogComponent, DeleteEnumDefinitionDialogData } from './enum-graph-editor/delete-enum-definition-dialog/delete-enum-definition-dialog.component';
import { ActivatedRoute } from '@angular/router';


@Component({
    selector: 'app-enum-builder',
    templateUrl: './enum-builder.component.html',
    styleUrls: ['./enum-builder.component.scss'],
    // providers: [DialogFactoryDirective]
})
export class EnumBuilderComponent implements OnInit {
    constructor(
        public dialog: MatDialog,
        private route: ActivatedRoute,
        private formBuilder: FormBuilder,
        // private dialogFactory: DialogFactoryDirective,
        private enumDefinitionService: EnumDefinitionService,
    ) { }

    @Input() marketplace: Marketplace;
    @Input() helpseeker: Helpseeker;
    @Input() entryId: string;
    @Input() sourceString: string;
    @Output() result: EventEmitter<{builderType: string, value: EnumDefinition}> = new EventEmitter();

    form: FormGroup;
    enumDefinition: EnumDefinition;
    showEditor: boolean;

    loaded: boolean;

    ngOnInit() {
        this.form = this.formBuilder.group({
            name: this.formBuilder.control('', Validators.required),
            description: this.formBuilder.control('')
        });

        if (!isNullOrUndefined(this.entryId)) {
            this.enumDefinitionService.getEnumDefinitionById(this.marketplace, this.entryId).toPromise().then((ret: EnumDefinition) => {
                this.enumDefinition = ret;
                console.log(this.enumDefinition);
                this.form.get('name').setValue(this.enumDefinition.name);
                this.form.get('description').setValue(this.enumDefinition.description);
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

    populateEditor() {

    }

    handleCreateClick() {
        this.form.controls['name'].markAsTouched();
        if (this.form.invalid) {
            this.form.markAllAsTouched();
        } else {
            this.enumDefinitionService
                .newEmptyEnumDefinition(this.marketplace, this.form.controls['name'].value, this.form.controls['description'].value, this.helpseeker.tenantId)
                .toPromise().then((enumDefinition: EnumDefinition) => {
                    if (!isNullOrUndefined(enumDefinition)) {
                        this.enumDefinition = enumDefinition;
                        this.showEditor = true;
                    }
                });
        }

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


    handleCancelClick() {
        this.result.emit(undefined);
    }

    handleResult(event: EnumDefinition) {
        this.result.emit({builderType: 'enum', value: event});
    }

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
