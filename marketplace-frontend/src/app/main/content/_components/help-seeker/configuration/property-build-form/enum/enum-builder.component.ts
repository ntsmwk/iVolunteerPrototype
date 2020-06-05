import { Component, OnInit, Input, ElementRef, ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material';
import { FormGroup, Validators, FormBuilder } from '@angular/forms';
import { isNullOrUndefined } from 'util';
import { mxgraph } from 'mxgraph';
import { Router } from '@angular/router';
import { ObjectIdService } from 'app/main/content/_service/objectid.service.';
import { DialogFactoryDirective } from 'app/main/content/_shared_components/dialogs/_dialog-factory/dialog-factory.component';
import { Marketplace } from 'app/main/content/_model/marketplace';
import { Helpseeker } from 'app/main/content/_model/helpseeker';
import { MyMxCellType, MyMxCell } from '../../myMxCell';


declare var require: any;

const mx: typeof mxgraph = require('mxgraph')({
    // mxDefaultLanguage: 'de',
    // mxBasePath: './mxgraph_resources',
});

// tslint:disable-next-line: class-name


@Component({
    selector: 'app-enum-builder',
    templateUrl: './enum-builder.component.html',
    styleUrls: ['./enum-builder.component.scss'],
    // providers: [DialogFactoryDirective]
})
export class EnumBuilderComponent implements OnInit {
    constructor(
        private formBuilder: FormBuilder,
        private router: Router,
        private objectIdService: ObjectIdService,
        // private dialogFactory: DialogFactoryDirective,
    ) { }

    @Input() marketplace: Marketplace;
    @Input() helpseeker: Helpseeker;

    form: FormGroup;

    ngOnInit() {

        this.form = this.formBuilder.group({
            name: this.formBuilder.control('', Validators.required),
        });

    }

    ngAfterContentInit() {

    }


    navigateBack() {
        window.history.back();
    }


}
