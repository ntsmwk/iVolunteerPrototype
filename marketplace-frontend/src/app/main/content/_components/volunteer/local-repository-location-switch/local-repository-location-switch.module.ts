import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { LocalRepositoryLocationSwitchComponent } from "./local-repository-location-switch.component";
import {
  MatCardModule,
  MatButtonModule,
  MatRadioModule,
  MatIconModule,
  MatFormFieldModule,
  MatInputModule,
  MatTooltipModule,
  MatProgressSpinnerModule,
} from "@angular/material";
import { FuseSharedModule } from "@fuse/shared.module";
import { FlexLayoutModule } from "@angular/flex-layout";
import { DialogFactoryModule } from "../../_shared/dialogs/_dialog-factory/dialog-factory.module";
import { FormsModule } from "@angular/forms";

@NgModule({
  declarations: [LocalRepositoryLocationSwitchComponent],
  imports: [
    CommonModule,
    FlexLayoutModule,

    MatCardModule,
    MatButtonModule,
    MatRadioModule,
    MatIconModule,
    MatFormFieldModule,
    MatInputModule,
    MatIconModule,
    MatTooltipModule,
    DialogFactoryModule,
    FormsModule,
    MatProgressSpinnerModule,

    FuseSharedModule,
  ],
  exports: [LocalRepositoryLocationSwitchComponent],
})
export class LocalRepositoryLocationSwitchModule {}
