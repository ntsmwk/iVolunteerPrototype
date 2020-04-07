import { Component, Input, OnInit, Output, EventEmitter } from '@angular/core';
import { ClassDefinition } from 'app/main/content/_model/meta/Class';
import { Relationship } from 'app/main/content/_model/meta/Relationship';

export class ClassOptionsOverlayContentData {
    inputClassDefintion: ClassDefinition;
    inputRelationship: Relationship;
}

@Component({
    selector: 'class-options-overlay-content',
    templateUrl: './options-overlay-content.component.html',
    styleUrls: ['./options-overlay-content.component.scss']
})
export class ClassOptionsOverlayContentComponent implements OnInit {

    @Input() inputData: ClassOptionsOverlayContentData;
    @Output() resultRelationship = new EventEmitter<ClassOptionsOverlayContentData>();

    constructor(

    ) { }

    ngOnInit() {
    }

    onSubmit() {

    }
}