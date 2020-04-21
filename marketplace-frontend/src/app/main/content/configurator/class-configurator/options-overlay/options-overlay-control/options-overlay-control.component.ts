import { ViewChild, ElementRef, Component, ChangeDetectorRef, Input, EventEmitter, Output } from '@angular/core';
import { ClassOptionsOverlayContentData } from '../options-overlay-content/options-overlay-content.component';

const OVERLAY_WIDTH = 400;
const OVERLAY_HEIGHT = 390;


@Component({
  selector: 'class-options-overlay-control',
  templateUrl: './options-overlay-control.component.html',
  styleUrls: ['./options-overlay-control.component.scss']
})
export class ClassOptionsOverlayControlComponent {


  @ViewChild('overlayDiv', { static: false }) overlayDiv: ElementRef;
  @Input() displayOverlay: boolean;
  @Input() overlayContent: ClassOptionsOverlayContentData;
  @Input() overlayEvent: PointerEvent;
  @Output() overlayClosed = new EventEmitter<ClassOptionsOverlayContentData>();

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
      let xPos = this.overlayEvent.clientX;

      if (yPos + OVERLAY_HEIGHT > window.innerHeight) {
        yPos = window.innerHeight - OVERLAY_HEIGHT;
      }

      if (xPos + OVERLAY_WIDTH > window.innerWidth) {
        xPos = window.innerWidth - OVERLAY_WIDTH;
      }

      this.overlayDiv.nativeElement.style.top = yPos + 'px';
      this.overlayDiv.nativeElement.style.left = xPos + 'px';
      this.overlayDiv.nativeElement.style.position = 'fixed';
      this.overlayDiv.nativeElement.style.width = OVERLAY_WIDTH + 'px';
      this.overlayDiv.nativeElement.style.height = OVERLAY_HEIGHT + 'px';
    }
  }

  handleResultEvent(event: ClassOptionsOverlayContentData) {
    this.displayOverlay = false;
    this.overlayClosed.emit(event);
  }

  closeOverlay($event) {
    this.displayOverlay = false;
    this.overlayClosed.emit(undefined);
  }
}