import { ViewChild, ElementRef, Component, ChangeDetectorRef, Input } from '@angular/core';
import { MatchingOperatorRelationship } from 'app/main/content/_model/matching';

@Component({
  selector: 'options-overlay-control',
  templateUrl: './options-overlay-control.component.html',
  styleUrls: ['./options-overlay-control.component.scss']
})
export class OptionsOverlayControlComponent {


  @ViewChild('overlayDiv', { static: false }) overlayDiv: ElementRef;
  @Input() displayOverlay: boolean;
  @Input() overlayRelationship: MatchingOperatorRelationship;
  @Input() overlayEvent: PointerEvent;

  constructor(
    private changeDetector: ChangeDetectorRef
  ) { }

  ngOnInit() {
    this.toggleInboxOverlay();
  }

  ngOnChanges() {
    this.toggleInboxOverlay();
  }

  navigateBack() {
    window.history.back();
  }

  toggleInboxOverlay() {
    this.changeDetector.detectChanges();


    if (this.displayOverlay) {
      this.overlayDiv.nativeElement.style.top = (this.overlayEvent.clientY) + 'px';
      this.overlayDiv.nativeElement.style.left = this.overlayEvent.clientX + 'px';
      this.overlayDiv.nativeElement.style.position = 'fixed';
      this.overlayDiv.nativeElement.style.width = '300px';
      this.overlayDiv.nativeElement.style.height = '240px';

      console.log(this.overlayDiv.nativeElement);
    }



  }

  closeOverlay($event) {
    this.displayOverlay = false;
  }
}