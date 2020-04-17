import { Component, Input, OnInit, Output, EventEmitter } from '@angular/core';
import { ClassDefinition } from 'app/main/content/_model/meta/Class';
import { Relationship } from 'app/main/content/_model/meta/Relationship';
import { DialogFactoryDirective } from 'app/main/content/_components/dialogs/_dialog-factory/dialog-factory.component';
import { Marketplace } from 'app/main/content/_model/marketplace';

export class ClassOptionsOverlayContentData {
    marketplace: Marketplace;

    classDefinition: ClassDefinition;
    relationship: Relationship;
}

@Component({
    selector: 'class-options-overlay-content',
    templateUrl: './options-overlay-content.component.html',
    styleUrls: ['./options-overlay-content.component.scss']
})
export class ClassOptionsOverlayContentComponent implements OnInit {

    @Input() inputData: ClassOptionsOverlayContentData;
    @Output() resultData = new EventEmitter<ClassOptionsOverlayContentData>();

    constructor(
        private dialogFactory: DialogFactoryDirective,

    ) { }

    ngOnInit() {
        console.log(this.inputData);
    }

    onSubmit() {

    }

    changeIconClicked(selectionIndex: number) {

        this.dialogFactory.openChangeIconDialog(this.inputData.marketplace, this.inputData.classDefinition.imagePath).then((result: any) => {

            this.inputData.classDefinition.imagePath = result;
        });
    }
}
