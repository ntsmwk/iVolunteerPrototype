import { ViewChild, ElementRef, Component, ChangeDetectorRef } from '@angular/core';

@Component({
  selector: 'app-options-overlay-control',
  templateUrl: './options-overlay-control.component.html',
  styleUrls: ['./options-overlay-control.component.scss']
})
export class OptionsOverlayControlComponent {


  @ViewChild('overlayDiv', { static: false }) overlayDiv: ElementRef;
  displayInboxOverlay = false;

  constructor(
    private changeDetector: ChangeDetectorRef
  ) { }

  ngOnInit() {

  }


  navigateBack() {
    window.history.back();
  }

  toggleInboxOverlay(event: any, inboxIcon: any) {
    this.displayInboxOverlay = !this.displayInboxOverlay;
    this.changeDetector.detectChanges();

    if (this.displayInboxOverlay) {
      const { x, y } = inboxIcon._elementRef.nativeElement.getBoundingClientRect();

      this.overlayDiv.nativeElement.style.top = (y + 35) + 'px';
      this.overlayDiv.nativeElement.style.left = (x - 150) + 'px';
      this.overlayDiv.nativeElement.style.position = 'fixed';
      this.overlayDiv.nativeElement.style.width = '300px';
      this.overlayDiv.nativeElement.style.height = '240px';


    }



  }

  closeOverlay($event) {
    this.displayInboxOverlay = false;
  }
}