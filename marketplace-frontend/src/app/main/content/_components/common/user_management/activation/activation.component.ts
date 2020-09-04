import { Component, OnInit } from '@angular/core';
import { FuseConfigService } from '@fuse/services/config.service';
import { Router, ActivatedRoute } from '@angular/router';
import { fuseAnimations } from '@fuse/animations';
import { ActivationService } from 'app/main/content/_service/activation.service';
import { isNullOrUndefined } from 'util';
import { ActivationLinkClickedResponse } from 'app/main/content/_model/activation';
import { User } from 'app/main/content/_model/user';


@Component({
  selector: "activation",
  templateUrl: 'activation.component.html',
  styleUrls: ['./activation.component.scss'],
  animations: fuseAnimations,
})
export class ActivationComponent implements OnInit {
  activationId: string;
  response: ActivationLinkClickedResponse;
  loaded: boolean;
  resendActivationFlow: boolean;
  lastUser: User;



  constructor(
    private fuseConfig: FuseConfigService,
    private router: Router,
    private route: ActivatedRoute,
    private activationService: ActivationService,
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

    if (!isNullOrUndefined(this.activationId)) {
      this.activationService.activate(this.activationId).toPromise().then((ret: ActivationLinkClickedResponse) => {
        if (!isNullOrUndefined(ret)) {
          this.response = ret;
          if (!isNullOrUndefined(ret.user)) {
            this.lastUser = { ...ret.user };
          }
          this.loaded = true;
          console.log(ret);
          console.log(this.lastUser);
        }
      });
    }
  }



  handleBackClick() {
    window.history.back();
  }

}
