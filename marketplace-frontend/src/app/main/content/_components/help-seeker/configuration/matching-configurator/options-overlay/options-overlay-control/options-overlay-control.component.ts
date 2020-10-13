import { ViewChild, ElementRef, Component, ChangeDetectorRef, Input, EventEmitter, Output } from '@angular/core';
import { MatchingOperatorRelationship } from 'app/main/content/_model/matching';

@Component({
  selector: 'matching-options-overlay-control',
  templateUrl: './options-overlay-control.component.html',
  styleUrls: ['./options-overlay-control.component.scss']
})
export class MatchingOptionsOverlayControlComponent {


  @ViewChild('overlayDiv', { static: false }) overlayDiv: ElementRef;
  @Input() displayOverlay: boolean;
  @Input() overlayRelationship: MatchingOperatorRelationship;
  @Input() overlayEvent: PointerEvent;
  @Output() overlayClosed = new EventEmitter<MatchingOperatorRelationship>();

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

      let yPos = this.overlayEvent.clientY;
      const xPos = this.overlayEvent.clientX;

      if (yPos + 275 > window.innerHeight) {
        yPos = window.innerHeight - 275;
      }

      this.overlayDiv.nativeElement.style.top = yPos + 'px';
      this.overlayDiv.nativeElement.style.left = xPos + 'px';
      this.overlayDiv.nativeElement.style.position = 'fixed';
      this.overlayDiv.nativeElement.style.width = '300px';
      this.overlayDiv.nativeElement.style.height = '275px';
    }
  }

  handleResultEvent(event: MatchingOperatorRelationship) {
    this.displayOverlay = false;
    this.overlayClosed.emit(event);
  }

  closeOverlay($event) {
    this.displayOverlay = false;
    this.overlayClosed.emit(undefined);
  }
}
