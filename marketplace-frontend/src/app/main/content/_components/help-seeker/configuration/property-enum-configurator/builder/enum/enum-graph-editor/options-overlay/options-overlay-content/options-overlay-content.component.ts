import { Marketplace } from 'app/main/content/_model/marketplace';
import { Helpseeker } from 'app/main/content/_model/helpseeker';
import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
import { EnumEntry } from 'app/main/content/_model/meta/enum';
import { timeout } from 'rxjs/operators';

export class EnumOptionsOverlayContentData {
    enumEntry: EnumEntry;
}


@Component({
    selector: 'enum-options-overlay-content',
    templateUrl: './options-overlay-content.component.html',
    styleUrls: ['./options-overlay-content.component.scss']
})
export class EnumOptionsOverlayContentComponent implements OnInit {

    @Input() inputData: EnumOptionsOverlayContentData;
    @Output() result = new EventEmitter<EnumOptionsOverlayContentData>();

    typeSelection: string;

    constructor(
        private _sanitizer: DomSanitizer,

    ) { }

    ngOnInit() {
        this.inputData.enumEntry.selectable ? this.typeSelection = 'selector' : this.typeSelection = 'label';
    }

    onSubmit() {
        this.inputData.enumEntry.selectable = this.typeSelection === 'selector';

        const outer = this;
        window.setTimeout(function () {
            outer.result.emit(this.inputData);
        }, 400);

    }







}
