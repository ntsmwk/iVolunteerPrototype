import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ClassesComponent } from './classes.component';
import { RouterModule } from '@angular/router';
import { MatCommonModule, MatProgressSpinnerModule, MatIconModule, MatTableModule, MatButtonModule } from '@angular/material';
import { FuseTruncatePipeModule } from '../../_pipe/truncate-pipe.module';
import { FuseSharedModule } from '@fuse/shared.module';
import { ClassesListModule } from './list/classes-list.module';
import { ClassesConfiguratorModule } from './configurator/classes-configurator.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

@NgModule({
  imports: [
    CommonModule,
    
    MatCommonModule,
    MatProgressSpinnerModule,
    MatIconModule,
    MatTableModule,
    MatButtonModule,


    ClassesListModule,
    ClassesConfiguratorModule,

    FuseSharedModule,
    FuseTruncatePipeModule,

  ],
  declarations: [ClassesComponent],
  exports: [ClassesComponent]

})



export class ClassesModule { }