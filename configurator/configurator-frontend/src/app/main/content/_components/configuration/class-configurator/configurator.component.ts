import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { isNullOrUndefined } from 'util';

/**
 * DEBUG: 
 * tenantId: 5f92c841eada0c0d9dfa877a
 * classConfigurationId: 5fa52106eada0c23a77cd7b2
 */


@Component({
  selector: "app-configurator",
  templateUrl: './configurator.component.html',
  styleUrls: ['./configurator.component.scss']
})
export class ConfiguratorComponent implements OnInit {
  loaded = false;
  tenantId: string;
  classConfigurationId: string;
  redirectUrl: string;

  constructor(private route: ActivatedRoute,
    private router: Router) { }

  async ngOnInit() {

    this.route.queryParams.subscribe(params => {
      if (isNullOrUndefined(params['tenantId']) || isNullOrUndefined(params['redirect'])) {
        console.error('tenantId not set');
        this.router.navigate(['main/invalid-parameters']);
      } else {
        this.tenantId = params['tenantId'];
        this.classConfigurationId = params['classConfigurationId'];
        this.redirectUrl = params['redirect'];
      }
    });

    this.loaded = true;
  }

  navigateBack() {
    window.history.back();
  }
}
