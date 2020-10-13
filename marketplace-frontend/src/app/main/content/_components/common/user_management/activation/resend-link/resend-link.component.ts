import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { fuseAnimations } from '@fuse/animations';
import { ActivationService } from 'app/main/content/_service/activation.service';
import { organizationTexts, personTexts } from "../activation-texts";
import { AccountType } from 'app/main/content/_model/user';

@Component({
  selector: "resend-link",
  templateUrl: 'resend-link.component.html',
  styleUrls: ['./resend-link.component.scss'],
  animations: fuseAnimations,
})
export class ResendLinkComponent implements OnInit {

  @Input() emailAddress: string;
  @Input() username: string;
  @Input() accountType: AccountType;

  loading = false;
  showResultMessage = false;
  activationTexts: any;

  constructor(
    private activationService: ActivationService
  ) { }

  ngOnInit() {
    this.activationTexts = this.accountType === AccountType.ORGANIZATION ? organizationTexts : personTexts;
  }

  handleBackClick() {
    window.history.back();
  }

  onSubmit() {
    this.handleResendLinkSubmit(this.emailAddress);
  }

  handleResendLinkSubmit(email: string) {
    this.loading = true;
    this.emailAddress = email;
    this.activationService.createActivationLinkViaEmail(email, this.accountType).toPromise().then((ret) => {
      this.loading = false;
      this.showResultMessage = true;
    });
  }

}
