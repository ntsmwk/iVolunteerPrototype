import { Component, Input, OnInit, Output, EventEmitter } from '@angular/core';
import { MatchingOperatorRelationship } from 'app/main/content/_model/matching';


@Component({
    selector: 'options-overlay-content',
    templateUrl: './options-overlay-content.component.html',
    styleUrls: ['./options-overlay-content.component.scss']
})
export class OptionsOverlayContentComponent implements OnInit {

    @Input() overlayRelationship: MatchingOperatorRelationship;

    constructor(

    ) { }

    ngOnInit() {
        console.log("content init");
        console.log(this.overlayRelationship);
        this.overlayRelationship.weighting = 20;
        this.overlayRelationship.fuzzyness = 30;
        this.overlayRelationship.necessary = true;
    }


    onSubmit() {

    }


    navigateBack() {
        window.history.back();
    }

    sliderValueChanged(evt) {
        this.overlayRelationship.fuzzyness = evt.value;
    }

}