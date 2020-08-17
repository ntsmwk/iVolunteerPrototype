import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { LocalRepositoryLocationSwitchComponent } from "./local-repository-location-switch.component";
import {
  MatCardModule,
  MatButtonModule,
  MatRadioModule,
  MatIconModule,
} from "@angular/material";
import { FuseSharedModule } from "@fuse/shared.module";
import { FlexLayoutModule } from "@angular/flex-layout";

@NgModule({
  declarations: [LocalRepositoryLocationSwitchComponent],
  imports: [
    CommonModule,
    FlexLayoutModule,

    MatCardModule,
    MatButtonModule,
    MatRadioModule,
    MatIconModule,

    FuseSharedModule,
  ],
  exports: [LocalRepositoryLocationSwitchComponent],
})
export class LocalRepositoryLocationSwitchModule {}
