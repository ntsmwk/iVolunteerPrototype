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

      this.overlayDiv.nativeElement.style.top = (150) + 'px';
      this.overlayDiv.nativeElement.style.left = (500) + 'px';
      this.overlayDiv.nativeElement.style.position = 'fixed';
      this.overlayDiv.nativeElement.style.width = '300px';
      this.overlayDiv.nativeElement.style.height = '240px';


    }



  }

  closeOverlay($event) {
    this.displayOverlay = false;
  }
}