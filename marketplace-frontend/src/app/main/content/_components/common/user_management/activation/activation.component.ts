import { Component, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { FuseConfigService } from '@fuse/services/config.service';
import { Router, ActivatedRoute } from '@angular/router';
import { fuseAnimations } from '@fuse/animations';


@Component({
  selector: "activation",
  templateUrl: 'activation.component.html',
  styleUrls: ['./activation.component.scss'],
  animations: fuseAnimations,
})
export class ActivationComponent implements OnInit {
  activationId: string;

  constructor(
    private fuseConfig: FuseConfigService,
    private router: Router,
    private route: ActivatedRoute,
  ) {
    const layout = {
      navigation: 'none',
      toolbar: 'none',
      footer: 'none',
    };
    this.fuseConfig.setConfig({ layout: layout });

  }



  ngOnInit() {
    this.route.params.subscribe((param) => {
      this.activationId = param['activationId'];
    });
  }



  handleBackClick() {
    window.history.back();
  }

}
