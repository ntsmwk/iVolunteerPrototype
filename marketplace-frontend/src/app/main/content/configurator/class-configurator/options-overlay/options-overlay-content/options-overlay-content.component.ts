import { Component, Input, OnInit, Output, EventEmitter } from '@angular/core';
import { MatchingOperatorRelationship } from 'app/main/content/_model/matching';
import { CConstants } from '../../../class-configurator/utils-and-constants';
import { myMxCell } from '../../../myMxCell';
import { ClassDefinition } from 'app/main/content/_model/meta/Class';
import { Relationship } from 'app/main/content/_model/meta/Relationship';

export class ClassOptionsOverlayContentData {
    inputCell: myMxCell;
    inputClassDefintion: ClassDefinition;
    inputRelationship: Relationship;
}

@Component({
    selector: 'class-options-overlay-content',
    templateUrl: './options-overlay-content.component.html',
    styleUrls: ['./options-overlay-content.component.scss']
})
export class ClassOptionsOverlayContentComponent implements OnInit {

    @Input() inputCell: myMxCell;
    @Output() resultRelationship = new EventEmitter<ClassOptionsOverlayContentData>();

    constructor(

    ) { }

    ngOnInit() {
    }

    onSubmit() {

    }
}