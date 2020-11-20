import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { NewClassConfigurationDialogComponent } from "./new-dialog.component";
import { MatButtonModule } from "@angular/material/button";
import { MatCheckboxModule } from "@angular/material/checkbox";
import { MatCommonModule, MatOptionModule } from "@angular/material/core";
import { MatDialogModule } from "@angular/material/dialog";
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatIconModule } from "@angular/material/icon";
import { MatInputModule } from "@angular/material/input";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { MatTooltipModule, MatDividerModule, MatSelectModule } from "@angular/material";

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    MatCommonModule,
    MatDialogModule,
    MatButtonModule,
    MatCheckboxModule,
    MatInputModule,
    MatFormFieldModule,
    MatIconModule,
    MatTooltipModule,
    MatDividerModule,
    MatOptionModule,
    MatSelectModule,
  ],
  declarations: [NewClassConfigurationDialogComponent],
  exports: [NewClassConfigurationDialogComponent],
})
export class NewClassConfigurationDialogModule { }
