import { Component, OnInit, Input, EventEmitter, Output } from '@angular/core';
import { FormGroup, Validators, FormBuilder } from '@angular/forms';
import { mxgraph } from 'mxgraph';
import { Router } from '@angular/router';
import { ObjectIdService } from 'app/main/content/_service/objectid.service.';
import { Marketplace } from 'app/main/content/_model/marketplace';
import { Helpseeker } from 'app/main/content/_model/helpseeker';
import { EnumDefinition } from 'app/main/content/_model/meta/enum';


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
    @Output() result: EventEmitter<EnumDefinition> = new EventEmitter();

    form: FormGroup;
    showEditor: boolean;

    ngOnInit() {

        this.form = this.formBuilder.group({
            name: this.formBuilder.control('', Validators.required),
        });

    }

    navigateBack() {
        window.history.back();
    }

    createClicked() {
        if (this.form.invalid) {
            this.form.markAllAsTouched();
        } else {
            this.showEditor = true;
        }

    }

    handleSaveClick() {

    }

    handleCancelClick() {
        this.result.emit(undefined);
    }


}
