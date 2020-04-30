import { Component, Input, OnInit, Output, EventEmitter } from '@angular/core';
import { MatchingOperatorRelationship, MatchingOperatorType } from 'app/main/content/_model/matching';
import { CConstants } from '../../../class-configurator/utils-and-constants';


@Component({
    selector: 'matching-options-overlay-content',
    templateUrl: './options-overlay-content.component.html',
    styleUrls: ['./options-overlay-content.component.scss']
})
export class MatchingOptionsOverlayContentComponent implements OnInit {

    @Input() overlayRelationship: MatchingOperatorRelationship;
    @Output() resultRelationship = new EventEmitter<MatchingOperatorRelationship>();

    // Model for Overlay
    matchingOperatorType: MatchingOperatorType;
    necessary: boolean;
    fuzzyness: number;
    weighting: number;


    matchingOperatorPalettes = CConstants.matchingOperatorPalettes;

    fuzzynessValid = true;
    weightingValid = true;

    constructor(

    ) { }

    ngOnInit() {
        this.matchingOperatorType = this.overlayRelationship.matchingOperatorType;
        this.necessary = this.overlayRelationship.necessary;
        this.weighting = this.overlayRelationship.weighting;
        this.fuzzyness = this.overlayRelationship.fuzzyness;
    }

    getImagePathForMatchingOperatorType(type: MatchingOperatorType) {
        return (this.matchingOperatorPalettes.find(p => p.id === type).imgPath);
    }

    getLabelForMatchingOperatorType(type: MatchingOperatorType) {
        return (this.matchingOperatorPalettes.find(p => p.id === type).label);
    }

    matchingOperatorChanged(paletteItem: any) {
        this.matchingOperatorType = paletteItem.id;
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
            this.overlayRelationship.matchingOperatorType = this.matchingOperatorType;
            this.overlayRelationship.necessary = this.necessary;
            this.overlayRelationship.fuzzyness = this.fuzzyness;
            this.overlayRelationship.weighting = this.weighting;

            this.resultRelationship.emit(this.overlayRelationship);
        }
    }

    checkFuzzyness() {
        return this.fuzzyness >= 0 && this.fuzzyness <= 100;
    }

    fixFuzzyness() {
        if (this.fuzzyness < 0) {
            this.fuzzyness = 0;
        }

        if (this.fuzzyness > 100) {
            this.fuzzyness = 100;
        }
    }

    checkWeighting() {
        return this.weighting >= 0 && this.weighting <= 9999;
    }

    fixWeighting() {
        if (this.weighting < 0) {
            this.weighting = 0;
        }

        if (this.weighting > 9999) {
            this.weighting = 9999;
        }
    }

    navigateBack() {
        window.history.back();
    }

    sliderValueChanged(evt) {
        this.fuzzyness = evt.value;
    }

}
