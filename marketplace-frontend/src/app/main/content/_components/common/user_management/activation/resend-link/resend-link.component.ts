import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { fuseAnimations } from '@fuse/animations';
import { ActivationService } from 'app/main/content/_service/activation.service';

@Component({
  selector: "resend-link",
  templateUrl: 'resend-link.component.html',
  styleUrls: ['./resend-link.component.scss'],
  animations: fuseAnimations,
})
export class ResendLinkComponent implements OnInit {

  @Input() emailAddress: string;
  @Input() username: string;
  loading = false;
  showResultMessage = false;

  constructor(
    private activationService: ActivationService
  ) { }

  ngOnInit() {

  }

  handleBackClick() {
    window.history.back();
  }

  onSubmit() {
    // this.submitForm.emit(this.emailAddress);
    this.handleResendLinkSubmit(this.emailAddress);
  }

  handleResendLinkSubmit(email: string) {
    console.log("submit");
    console.log(email);
    this.loading = true;
    this.emailAddress = email;
    this.activationService.createActivationLinkViaEmail(email).toPromise().then((ret) => {
      console.log(ret);
      this.loading = false;
      this.showResultMessage = true;
    });
  }

}
