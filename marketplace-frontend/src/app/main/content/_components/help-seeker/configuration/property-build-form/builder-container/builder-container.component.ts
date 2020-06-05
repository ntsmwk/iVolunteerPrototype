import { Component, OnInit, Input, EventEmitter, Output } from '@angular/core';
import { MatTableDataSource } from '@angular/material';
import { FormGroup, Validators, FormBuilder } from '@angular/forms';
import { isNullOrUndefined } from 'util';
import { Marketplace } from 'app/main/content/_model/marketplace';
import { PropertyDefinition } from 'app/main/content/_model/meta/property';
import { Helpseeker } from 'app/main/content/_model/helpseeker';

@Component({
    selector: 'app-builder-container',
    templateUrl: './builder-container.component.html',
    styleUrls: ['./builder-container.component.scss']
})
export class BuilderContainerComponent implements OnInit {

    @Input() marketplace: Marketplace;
    @Input() helpseeker: Helpseeker;
    @Input() allPropertyDefinitions: PropertyDefinition<any>[];
    @Output() result: EventEmitter<PropertyDefinition<any>> = new EventEmitter<PropertyDefinition<any>>();

    selectionValue = 'property';

    ngOnInit() {


    }

    handleResultEvent(event) {
        this.result.emit(event);
    }

    navigateBack() {
        window.history.back();
    }


}
