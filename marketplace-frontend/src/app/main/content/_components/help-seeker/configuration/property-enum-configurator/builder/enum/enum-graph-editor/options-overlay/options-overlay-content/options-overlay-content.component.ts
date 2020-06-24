import { Marketplace } from 'app/main/content/_model/marketplace';
import { Helpseeker } from 'app/main/content/_model/helpseeker';
import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';

export class EnumOptionsOverlayContentData {
    marketplace: Marketplace;
    helpseeker: Helpseeker;
}


@Component({
    selector: 'enum-options-overlay-content',
    templateUrl: './options-overlay-content.component.html',
    styleUrls: ['./options-overlay-content.component.scss']
})
export class EnumOptionsOverlayContentComponent implements OnInit {

    @Input() inputData: EnumOptionsOverlayContentData;
    @Output() resultData = new EventEmitter<EnumOptionsOverlayContentData>();

    constructor(
        private _sanitizer: DomSanitizer,

    ) { }

    ngOnInit() {
    }

    onSubmit() {
    }







}
