import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';

import { FuseSharedModule } from '@fuse/shared.module';
import { DynamicFormItemComponent } from './dynamic-form-item.component';
import { GermanDateAdapter } from '../../../../_adapter/german-date-adapter';
/*prettier-ignore*/
import {
  MatButtonModule, MatCheckboxModule, MatChipsModule, MatDividerModule, MatFormFieldModule, MatIconModule,
  MatInputModule, MatSidenavModule, MatTableModule, MatSelectModule, MatOptionModule, MatRadioModule,
  MatSlideToggleModule, MatCardModule, MatDatepickerModule, MatNativeDateModule, DateAdapter, MatListModule,
} from '@angular/material';
import { MultipleSelectTreeItemModule } from './multiple-select-tree-item/multiple-select-tree-item.module';
import { SingleSelectTreeItemModule } from './single-select-tree-item/single-select-tree-item.module';
import { LocationItemModule } from './location-item/location-item.module';
import { ComputedItemModule } from './computed-item/computed-item.module';

@NgModule({
  imports: [
    CommonModule, ReactiveFormsModule,

    MatFormFieldModule, MatButtonModule, MatCheckboxModule, MatChipsModule,
    MatDividerModule, MatIconModule, MatInputModule, MatSidenavModule, MatTableModule, MatSelectModule,
    MatOptionModule, MatRadioModule, MatSlideToggleModule, MatCardModule, MatDatepickerModule, MatNativeDateModule,
    MatListModule,

    MultipleSelectTreeItemModule,
    SingleSelectTreeItemModule,
    LocationItemModule,
    ComputedItemModule,

    FuseSharedModule,
  ],
  declarations: [DynamicFormItemComponent],
  exports: [DynamicFormItemComponent],
  providers: [
    { provide: DateAdapter, useClass: GermanDateAdapter },
  ]
})
export class DynamicFormItemModule { }
