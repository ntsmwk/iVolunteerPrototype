import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PropertyBuildFormComponent } from './property-build-form.component';
import { RouterModule } from '@angular/router';
import { MatCommonModule, MatProgressSpinnerModule, MatIconModule, MatRadioModule, MatTooltipModule, MatInputModule, MatFormFieldModule, MatSelectModule, MatOptionModule, MatDatepickerModule, DateAdapter, MatNativeDateModule, MatSlideToggleModule, MatDividerModule } from '@angular/material';
import { FuseTruncatePipeModule } from '../_pipe/truncate-pipe.module';
import { FuseSharedModule } from '@fuse/shared.module';
import { GermanDateAdapter } from '../_adapter/german-date-adapter';
import { SinglePropertyModule } from './single-property/single-property.module';
import { MultiplePropertyModule } from './multiple-property/multiple-property.module';

const routes = [
  {path: ':marketplaceId/:propertyId', component: PropertyBuildFormComponent},
  {path: ':marketplaceId', component: PropertyBuildFormComponent}
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

    SinglePropertyModule,
    MultiplePropertyModule,

    FuseSharedModule,
    FuseTruncatePipeModule,

  ],
  declarations: [PropertyBuildFormComponent],
  providers: [
    {provide: DateAdapter, useClass: GermanDateAdapter},
  ]
})



export class PropertyBuildFormModule { }
