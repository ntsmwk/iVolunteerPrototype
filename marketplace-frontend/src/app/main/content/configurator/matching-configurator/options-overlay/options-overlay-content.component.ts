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
    @Output() resultRelationship = new EventEmitter<MatchingOperatorRelationship>();

    matchingPalettes = CConstants.matchingPalettes;

    fuzzynessValid = true;
    weightingValid = true;

    constructor(

    ) { }

    ngOnInit() {
        console.log("content init");
        console.log(this.overlayRelationship);


        console.log(this.matchingPalettes);
    }

    getImagePathForMatchingOperatorType(type: MatchingOperatorType) {
        return (this.matchingPalettes.find(p => p.id === type).imgPath);
    }

    getLabelForMatchingOperatorType(type: MatchingOperatorType) {
        return (this.matchingPalettes.find(p => p.id === type).label);
    }

    matchingOperatorChanged(paletteItem: any) {
        this.overlayRelationship.matchingOperatorType = paletteItem.id;
    }


    onSubmit() {
        this.fuzzynessValid = this.checkFuzzyness();
        this.weightingValid = this.checkWeighting();

        if (!this.fuzzynessValid) {
            this.fixFuzzyness();
        }

        if (!this.weightingValid) {
            this.fixWeighting();
        }

        if (this.fuzzynessValid && this.weightingValid) {
            // RETURN
            this.resultRelationship.emit(this.overlayRelationship);
        }
    }

    checkFuzzyness() {
        return this.overlayRelationship.fuzzyness >= 0 && this.overlayRelationship.fuzzyness <= 100;
    }

    fixFuzzyness() {
        if (this.overlayRelationship.fuzzyness < 0) {
            this.overlayRelationship.fuzzyness = 0;
        }

        if (this.overlayRelationship.fuzzyness > 100) {
            this.overlayRelationship.fuzzyness = 100;
        }
    }

    checkWeighting() {
        return this.overlayRelationship.weighting >= 0 && this.overlayRelationship.weighting <= 9999;
    }

    fixWeighting() {
        if (this.overlayRelationship.weighting < 0) {
            this.overlayRelationship.weighting = 0;
        }

        if (this.overlayRelationship.weighting > 9999) {
            this.overlayRelationship.weighting = 9999;
        }
    }

    navigateBack() {
        window.history.back();
    }

    sliderValueChanged(evt) {
        this.overlayRelationship.fuzzyness = evt.value;
    }

}