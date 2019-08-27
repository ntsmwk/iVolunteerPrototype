import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ClassesConfiguratorComponent } from './classes-configurator.component';
import { RouterModule } from '@angular/router';
import { MatCommonModule, MatProgressSpinnerModule, MatIconModule, MatInputModule, MatMenuModule } from '@angular/material';
import { FuseTruncatePipeModule } from '../../../_pipe/truncate-pipe.module';
import { FuseSharedModule } from '@fuse/shared.module';

// const routes = [
//   {path: '**', component: ClassesConfiguratorComponent}
// ];

@NgModule({
  imports: [
    CommonModule,
    
    MatCommonModule,
    MatProgressSpinnerModule,
    MatIconModule,
    MatInputModule,
    MatMenuModule,




    FuseSharedModule,
    FuseTruncatePipeModule,

  ],
  declarations: [ClassesConfiguratorComponent],
  exports: [ClassesConfiguratorComponent]

})



export class ClassesConfiguratorModule { }
