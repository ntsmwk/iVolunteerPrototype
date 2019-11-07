import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ConfiguratorComponent } from './configurator.component';
import { RouterModule } from '@angular/router';
import { MatCommonModule, MatProgressSpinnerModule, MatIconModule, MatRadioModule, MatTooltipModule, MatInputModule, MatFormFieldModule, MatSelectModule, MatOptionModule, MatDatepickerModule, DateAdapter, MatNativeDateModule, MatSlideToggleModule, MatDividerModule, MatTabsModule } from '@angular/material';
import { FuseTruncatePipeModule } from '../_pipe/truncate-pipe.module';
import { FuseSharedModule } from '@fuse/shared.module';
import { ConfiguratorEditorModule } from './configurator-editor/configurator-editor.module';

const routes = [
  {path: '', component: ConfiguratorComponent}
];

@NgModule({
  imports: [
    CommonModule,

    RouterModule.forChild(routes),
    
    MatCommonModule,
    MatProgressSpinnerModule,
    MatIconModule,
    MatRadioModule,
    MatFormFieldModule,
    MatInputModule,
    MatOptionModule,
    MatSelectModule,
    MatSlideToggleModule,
    MatDividerModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatTooltipModule,
   
   
    MatTabsModule,

    ConfiguratorEditorModule,

    FuseSharedModule,
    FuseTruncatePipeModule,

  ],
  declarations: [ConfiguratorComponent]
})



export class ConfiguratorModule { }
