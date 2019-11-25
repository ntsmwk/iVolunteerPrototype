import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PropertyBuildFormComponent } from './property-build-form.component';
import { RouterModule } from '@angular/router';
import { MatCommonModule, MatOptionModule, DateAdapter, MatNativeDateModule } from '@angular/material/core';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatDividerModule } from '@angular/material/divider';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatRadioModule } from '@angular/material/radio';
import { MatSelectModule } from '@angular/material/select';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { MatTooltipModule } from '@angular/material/tooltip';
import { FuseTruncatePipeModule } from '../_pipe/truncate-pipe.module';
import { FuseSharedModule } from '@fuse/shared.module';
import { GermanDateAdapter } from '../_adapter/german-date-adapter';
import { SinglePropertyModule } from './single-property/single-property.module';
// import { MultiplePropertyModule } from './multiple-property/multiple-property.module';

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
    // MultiplePropertyModule,

    FuseSharedModule,
    FuseTruncatePipeModule,

  ],
  declarations: [PropertyBuildFormComponent],
  providers: [
    {provide: DateAdapter, useClass: GermanDateAdapter},
  ]
})



export class PropertyBuildFormModule { }
