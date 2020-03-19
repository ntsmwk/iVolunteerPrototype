import { ViewChild, ElementRef, Component, ChangeDetectorRef } from '@angular/core';

@Component({
  selector: 'options-overlay-control',
  templateUrl: './options-overlay-control.component.html',
  styleUrls: ['./options-overlay-control.component.scss']
})
export class OptionsOverlayControlComponent {


  @ViewChild('overlayDiv', { static: false }) overlayDiv: ElementRef;
  displayOptionsOverlay = true;

  constructor(
    private changeDetector: ChangeDetectorRef
  ) { }

  ngOnInit() {
    this.toggleInboxOverlay();
  }


  navigateBack() {
    window.history.back();
  }

  toggleInboxOverlay() {
    // this.displayOptionsOverlay = !this.displayOptionsOverlay;
    this.changeDetector.detectChanges();

    if (this.displayOptionsOverlay) {

      this.overlayDiv.nativeElement.style.top = (150) + 'px';
      this.overlayDiv.nativeElement.style.left = (500) + 'px';
      this.overlayDiv.nativeElement.style.position = 'fixed';
      this.overlayDiv.nativeElement.style.width = '300px';
      this.overlayDiv.nativeElement.style.height = '240px';


    }



  }

  closeOverlay($event) {
    this.displayOptionsOverlay = false;
  }
}