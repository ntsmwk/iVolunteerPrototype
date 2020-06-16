import { Component, OnInit, Input, EventEmitter, Output } from '@angular/core';
import { Marketplace } from 'app/main/content/_model/marketplace';
import { PropertyDefinition } from 'app/main/content/_model/meta/property';
import { Helpseeker } from 'app/main/content/_model/helpseeker';
import { ActivatedRoute, Router, Params } from '@angular/router';

@Component({
    selector: 'app-builder-container',
    templateUrl: './builder-container.component.html',
    styleUrls: ['./builder-container.component.scss']
})
export class BuilderContainerComponent implements OnInit {

    @Input() marketplace: Marketplace;
    @Input() helpseeker: Helpseeker;
    @Input() allPropertyDefinitions: PropertyDefinition<any>[];
    @Input() builderType: string;
    @Output() result: EventEmitter<PropertyDefinition<any>> = new EventEmitter<PropertyDefinition<any>>();

    constructor(private router: Router,
        private route: ActivatedRoute
    ) { }

    ngOnInit() {


    }

    onSelectionChange() {
        const queryParams: Params = { type: this.builderType };

        this.router.navigate([], { relativeTo: this.route, queryParams: queryParams, queryParamsHandling: 'merge', });
    }

    handleResultEvent(event) {
        this.result.emit(event);
    }

    navigateBack() {
        window.history.back();
    }


}
