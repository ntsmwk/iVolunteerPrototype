import { Component, Input, OnInit, Output, EventEmitter } from '@angular/core';


@Component({
    selector: 'options-overlay-content',
    templateUrl: './options-overlay-content.component.html',
    styleUrls: ['./options-overlay-content.component.scss']
})
export class OptionsOverlayContentComponent implements OnInit {

    constructor(

    ) { }

    ngOnInit() {

    }


    onSubmit() {

    }


    navigateBack() {
        window.history.back();
    }

}