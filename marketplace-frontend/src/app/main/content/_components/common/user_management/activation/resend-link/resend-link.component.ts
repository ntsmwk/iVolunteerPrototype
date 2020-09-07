import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { fuseAnimations } from '@fuse/animations';

@Component({
  selector: "resend-link",
  templateUrl: 'resend-link.component.html',
  styleUrls: ['./resend-link.component.scss'],
  animations: fuseAnimations,
})
export class ResendLinkComponent implements OnInit {

  @Input() emailAddress: string;
  @Input() username: string;
  @Output() submitForm: EventEmitter<String> = new EventEmitter();

  constructor(

  ) { }

  ngOnInit() {

  }

  handleBackClick() {
    window.history.back();
  }

  onSubmit() {
    this.submitForm.emit(this.emailAddress);
  }

}
