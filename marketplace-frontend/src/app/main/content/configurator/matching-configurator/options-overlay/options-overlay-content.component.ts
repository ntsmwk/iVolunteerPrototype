import { Component, Input, OnInit, Output, EventEmitter } from '@angular/core';
import { MatchingOperatorRelationship, MatchingOperatorType } from 'app/main/content/_model/matching';
import { CConstants } from '../../class-configurator/utils-and-constants';


@Component({
    selector: 'options-overlay-content',
    templateUrl: './options-overlay-content.component.html',
    styleUrls: ['./options-overlay-content.component.scss']
})
export class OptionsOverlayContentComponent implements OnInit {

    @Input() overlayRelationship: MatchingOperatorRelationship;

    matchingPalettes = CConstants.matchingPalettes;

    constructor(

    ) { }

    ngOnInit() {
        console.log("content init");
        console.log(this.overlayRelationship);
        this.overlayRelationship.weighting = 20;
        this.overlayRelationship.fuzzyness = 30;
        this.overlayRelationship.necessary = true;

        console.log(this.matchingPalettes);
    }

    getImagePathForMatchingOperatorType(type: MatchingOperatorType) {
        return (this.matchingPalettes.find(p => p.id === type).imgPath)
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