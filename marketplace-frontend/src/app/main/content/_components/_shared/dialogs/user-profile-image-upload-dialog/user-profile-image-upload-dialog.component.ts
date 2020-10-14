import { Component, Inject, OnInit } from "@angular/core";
import { MatDialogRef, MAT_DIALOG_DATA } from "@angular/material/dialog";
import { User } from "app/main/content/_model/user";

export interface UserProfileImageUploadDialogData {
  user: User;
}

@Component({
  selector: "user-profile-image-upload-dialog",
  templateUrl: "./user-profile-image-upload-dialog.component.html",
  styleUrls: ["./user-profile-image-upload-dialog.component.scss"]
})
export class UserProfileImageUploadDialogComponent implements OnInit {
  user: User;

  constructor(
    public dialogRef: MatDialogRef<UserProfileImageUploadDialogComponent>,
    @Inject(MAT_DIALOG_DATA)
    public data: UserProfileImageUploadDialogData
  ) {}

  ngOnInit() {
    this.user = this.data.user;
  }

  handleOnSave($event) {
    this.dialogRef.close(this.user);
  }
}
