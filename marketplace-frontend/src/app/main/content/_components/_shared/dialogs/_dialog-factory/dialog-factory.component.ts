import { MatDialog } from "@angular/material";
import { Directive } from "@angular/core";
import { ConfirmDialogComponent } from "../confirm-dialog/confirm-dialog.component";
import { ClassDefinition } from "app/main/content/_model/meta/class";
import { Relationship } from "app/main/content/_model/meta/relationship";
import {
  ClassInstanceFormPreviewDialogComponent,
  ClassInstanceFormPreviewDialogData
} from "../../../help-seeker/configuration/class-instance-configurator/form-preview-dialog/form-preview-dialog.component";
import {
  ClassInstanceFormPreviewExportDialogComponent,
  ClassInstanceFormPreviewExportDialogData
} from "../../../help-seeker/configuration/class-instance-configurator/form-preview-export-dialog/form-preview-export-dialog.component";
import { isNullOrUndefined } from "util";
import { User } from "app/main/content/_model/user";
import {
  AddHelpseekerDialogComponent,
  AddHelpseekerDialogData
} from "../../../admin/tenant-form/tenant-form-content/helpseekers-form/add-helpseeker-dialog/add-helpseeker-dialog.component";
import {
  UserProfileImageUploadDialogData,
  UserProfileImageUploadDialogComponent
} from "../user-profile-image-upload-dialog/user-profile-image-upload-dialog.component";

@Directive({
  selector: "app-dialog-factory"
})
export class DialogFactoryDirective {
  constructor(public dialog: MatDialog) { }

  confirmationDialog(title: string, description: string) {
    const dialogRef = this.dialog.open(ConfirmDialogComponent, {
      width: "500px",
      data: { title: title, description: description }
    });

    let ret = false;

    dialogRef
      .beforeClose()
      .toPromise()
      .then((result: boolean) => {
        if (result) {
          ret = result;
        }
      });

    return dialogRef
      .afterClosed()
      .toPromise()
      .then(() => {
        return ret;
      });
  }

  /**
   * ******CLASS CONFIGURATION******
   */

  /**
   * ******INSTANTIATION******
   */

  openInstanceFormPreviewDialog(
    classDefinitions: ClassDefinition[],
    relationships: Relationship[],
    rootClassDefinition: ClassDefinition
  ) {
    const dialogRef = this.dialog.open(
      ClassInstanceFormPreviewDialogComponent,
      {
        width: "90vw",
        minWidth: "90vw",
        height: "90vh",
        minHeight: "90vh",
        data: {
          classDefinitions: classDefinitions,
          relationships: relationships,
          rootClassDefinition: rootClassDefinition
        },
        disableClose: true
      }
    );

    let returnData: ClassInstanceFormPreviewDialogData;
    dialogRef
      .beforeClose()
      .toPromise()
      .then((result: ClassInstanceFormPreviewDialogData) => {
        returnData = result;
      });

    return dialogRef
      .afterClosed()
      .toPromise()
      .then(() => {
        return returnData;
      });
  }

  openPreviewExportDialog(classConfigurationIds: string[]) {
    const dialogRef = this.dialog.open(
      ClassInstanceFormPreviewExportDialogComponent,
      {
        width: "90vw",
        minWidth: "90vw",
        height: "90vh",
        minHeight: "90vh",
        data: {
          classConfigurationIds: classConfigurationIds
        },
        disableClose: true
      }
    );

    let returnValue: ClassInstanceFormPreviewExportDialogData;
    dialogRef
      .beforeClose()
      .toPromise()
      .then((result: ClassInstanceFormPreviewExportDialogData) => {
        returnValue = result;
      });

    return dialogRef
      .afterClosed()
      .toPromise()
      .then(() => {
        return returnValue;
      });
  }

  /*
   *  Matching-Configurator Dialogs
   */

  openAddHelpseekerDialog(helpseekers: User[]) {
    const dialogRef = this.dialog.open(AddHelpseekerDialogComponent, {
      width: "500px",
      minWidth: "500px",
      height: "418px",
      minHeight: "418px",
      data: {
        helpseekers
      }
    });

    let returnValue: AddHelpseekerDialogData;

    dialogRef
      .beforeClose()
      .toPromise()
      .then((result: AddHelpseekerDialogData) => {
        returnValue = result;
      });

    return dialogRef
      .afterClosed()
      .toPromise()
      .then(() => {
        return returnValue;
      });
  }

  openProfileImageUploadDialog(user: User) {
    const dialogRef = this.dialog.open(UserProfileImageUploadDialogComponent, {
      width: "800px",
      minWidth: "800px",
      height: "34px",
      minHeight: "340px",
      data: {
        user: user
      }
    });

    let returnValue: UserProfileImageUploadDialogData;

    dialogRef
      .beforeClose()
      .toPromise()
      .then((result: UserProfileImageUploadDialogData) => {
        returnValue = result;
      });

    return dialogRef
      .afterClosed()
      .toPromise()
      .then(() => {
        return returnValue;
      });
  }
}
