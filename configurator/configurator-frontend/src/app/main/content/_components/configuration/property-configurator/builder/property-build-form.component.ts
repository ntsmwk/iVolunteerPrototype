import { Component, OnInit } from '@angular/core';
import { FlatPropertyDefinition } from 'app/main/content/_model/configurator/property/property';
import { isNullOrUndefined } from 'util';
import { ActivatedRoute, Router } from '@angular/router';
import { environment } from 'environments/environment';

@Component({
  selector: "app-property-build-form",
  templateUrl: './property-build-form.component.html',
  styleUrls: ['./property-build-form.component.scss']
})
export class PropertyBuildFormComponent implements OnInit {
  entryId: string;
  loaded: boolean;
  displayBuilder: boolean;
  builderType: string;
  tenantId: string;
  redirectUrl: string;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
  ) { }

  async ngOnInit() {
    this.displayBuilder = true;

    this.route.queryParams.subscribe(params => {
      if (
        ((environment.MODE === 'iVolunteer' || environment.MODE === 'all') && (isNullOrUndefined(params['tenantId']) || isNullOrUndefined(params['redirect'])))
        ||
        (environment.MODE === 'flexprod' && isNullOrUndefined(params['tenantId']))

      ) {        this.router.navigate(['main/invalid-parameters']);
      } else {
        this.tenantId = params['tenantId'];
        this.redirectUrl = params['redirect'];
      }
    });

    await Promise.all([
      this.route.queryParams.subscribe(params => {
        if (isNullOrUndefined(params['type'] || params['type'] === 'flat')) {
          this.builderType = 'flat';
        } else {
          this.builderType = params['type'];
        }
      }),
      this.route.params.subscribe(params => {
        this.entryId = params['entryId'];
      })
    ]);

    this.loaded = true;
  }

  handleResultEvent(result: FlatPropertyDefinition<any>) {
    this.displayBuilder = false;
    window.history.back();
  }

  handleManagementEvent(event: string, dom: HTMLElement) {
    if (event === 'disableScroll') {
      dom.style.overflow = 'hidden';
    } else if (event === 'enableScroll') {
      dom.style.overflow = 'scroll';
    }
  }
}
