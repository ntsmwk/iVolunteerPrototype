import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ClassInstanceFormEditorComponent } from './class-instance-form-editor.component';
import { RouterModule } from '@angular/router';
import {
    MatCommonModule, MatProgressSpinnerModule, MatIconModule, MatTableModule, MatExpansionModule, MatFormFieldModule, MatInputModule,
    MatSelectModule, MatOptionModule, MatCardModule, MatSlideToggleModule, MatDatepicker, MatDatepickerModule, MatDividerModule, MatButtonModule,
    MatListModule
} from '@angular/material';
import { FuseSharedModule } from '@fuse/shared.module';
import { FormEntryViewModule } from './form-entry-view/form-entry-view.module';
import { InstanceCreationResultModule } from './result/result.module';
import { InstanceCreationVolunteerListModule } from './volunteer-list/volunteer-list.module';


const routes = [
    { path: ':marketplaceId', component: ClassInstanceFormEditorComponent }
    // {path: ':marketplaceId/:classId/:showMaxGluehtemperatur', component: ClassInstanceFormEditorComponent}
];

@NgModule({
    imports: [
        CommonModule,
        RouterModule.forChild(routes),


        MatCommonModule,
        MatExpansionModule,
        MatButtonModule,

        FuseSharedModule,
        FormEntryViewModule,
        InstanceCreationResultModule,
        InstanceCreationVolunteerListModule,

    ],
    declarations: [ClassInstanceFormEditorComponent],


})



export class ClassInstanceFormEditorModule { }