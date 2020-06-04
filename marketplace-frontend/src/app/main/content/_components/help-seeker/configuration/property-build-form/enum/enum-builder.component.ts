import { Component, OnInit, Input } from '@angular/core';
import { MatTableDataSource } from '@angular/material';
import { FormGroup, Validators, FormBuilder } from '@angular/forms';
import { isNullOrUndefined } from 'util';

@Component({
    selector: 'app-enum-builder',
    templateUrl: './enum-builder.component.html',
    styleUrls: ['./enum-builder.component.scss']
})
export class EnumBuilderComponent implements OnInit {


    ngOnInit() {


    }

    navigateBack() {
        window.history.back();
    }


}
