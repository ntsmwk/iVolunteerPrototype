import { Component, Input, OnInit, Output, EventEmitter } from '@angular/core';


@Component({
    selector: 'app-options-overlay',
    templateUrl: './options-overlay.component.html',
    styleUrls: ['./options-overlay.component.scss']
})
export class OptionsOverlayComponent implements OnInit {

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