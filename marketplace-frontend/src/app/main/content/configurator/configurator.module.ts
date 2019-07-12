import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ConfiguratorComponent } from './configurator.component';
import { RouterModule } from '@angular/router';
import { MatCommonModule, MatProgressSpinnerModule, MatIconModule, MatRadioModule, MatTooltipModule, MatInputModule, MatFormFieldModule, MatSelectModule, MatOptionModule, MatDatepickerModule, DateAdapter, MatNativeDateModule, MatSlideToggleModule, MatDividerModule, MatTabsModule } from '@angular/material';
import { FuseTruncatePipeModule } from '../_pipe/truncate-pipe.module';
import { FuseSharedModule } from '@fuse/shared.module';
import { ClassesModule } from './classes/classes.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';


const routes = [
  {path: '**', component: ConfiguratorComponent}
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

    ClassesModule,

    FuseSharedModule,
    FuseTruncatePipeModule,

  ],
  declarations: [ConfiguratorComponent]
})



export class ConfiguratorModule { }
