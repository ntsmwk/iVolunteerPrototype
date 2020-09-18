import { Component, OnInit } from '@angular/core';
import { FuseConfigService } from '@fuse/services/config.service';
import { Router, ActivatedRoute } from '@angular/router';
import { fuseAnimations } from '@fuse/animations';
import { ActivationService } from 'app/main/content/_service/activation.service';
import { isNullOrUndefined } from 'util';
import { ActivationLinkClickedResponse } from 'app/main/content/_model/activation';
import { organizationTexts, personTexts } from "./activation-texts";
import { AccountType } from 'app/main/content/_model/user';


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

  emailAddress: string;
  username: string;
  accountType: AccountType;

  activationTexts: any;


  constructor(
    private fuseConfig: FuseConfigService,
    private router: Router,
    private route: ActivatedRoute,
    private activationService: ActivationService,
  ) {

  }

  ngOnInit() {
    const layout = {
      navigation: 'none',
      toolbar: 'none',
      footer: 'none',
    };

    this.fuseConfig.setConfig({ layout: layout });


    this.route.params.subscribe((param) => {
      this.activationId = param['activationId'];
    });
    this.route.queryParams.subscribe((param) => {
      this.username = param['username'];
      this.emailAddress = param['email'];
      this.accountType = param['type'];
      console.log(param);
    });

    if (isNullOrUndefined(this.accountType)) {
      this.accountType = AccountType.ORGANIZATION;
    }

    this.activationTexts = this.accountType === AccountType.PERSON ? personTexts : organizationTexts;


    if (!isNullOrUndefined(this.activationId)) {
      this.activationService.activate(this.activationId).toPromise().then((ret: ActivationLinkClickedResponse) => {
        if (!isNullOrUndefined(ret)) {
          this.response = ret;
          this.loaded = true;
        }
      });
    }
  }

  handleBackClick() {
    window.history.back();
  }



}
