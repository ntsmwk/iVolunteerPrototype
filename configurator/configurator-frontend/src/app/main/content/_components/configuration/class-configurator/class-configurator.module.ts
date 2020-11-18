import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ClassConfiguratorComponent } from './class-configurator.component';
import { MatButtonModule } from '@angular/material/button';
import { MatCommonModule, MatOptionModule } from '@angular/material/core';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatSelectModule } from '@angular/material/select';
import { MatTooltipModule } from '@angular/material/tooltip';
import { FuseSharedModule } from '@fuse/shared.module';
import { EditorTopMenuBarModule } from './top-menu-bar/top-menu-bar.module';
import { EditorTreeViewModule } from './tree-view/tree-view.module';
import { ClassOptionsOverlayControlModule } from './options-overlay/options-overlay-control/options-overlay-control.module';
import { FormsModule } from '@angular/forms';
import { MatCheckboxModule } from '@angular/material';
import { DialogFactoryModule } from '../../_shared/dialogs/_dialog-factory/dialog-factory.module';

@NgModule({
  imports: [
    CommonModule,

    FormsModule,

    MatCommonModule,
    MatIconModule,
    MatButtonModule,
    MatFormFieldModule,

    MatSelectModule,
    MatOptionModule,
    MatTooltipModule,
    MatCheckboxModule,

    FuseSharedModule,

    DialogFactoryModule,

    EditorTopMenuBarModule,
    EditorTreeViewModule,

    ClassOptionsOverlayControlModule,

  ],
  declarations: [ClassConfiguratorComponent],
  exports: [ClassConfiguratorComponent],
})

export class ClassConfiguratorModule { }
