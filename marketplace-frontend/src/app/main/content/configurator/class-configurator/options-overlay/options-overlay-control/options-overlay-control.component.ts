import { ViewChild, ElementRef, Component, ChangeDetectorRef, Input, EventEmitter, Output } from '@angular/core';
import { ClassOptionsOverlayContentData } from '../options-overlay-content/options-overlay-content.component';

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

      if (yPos + 275 > window.innerHeight) {
        yPos = window.innerHeight - 275;
      }

      if (xPos + 300 > window.innerWidth) {
        xPos = window.innerWidth - 300;
      }

      this.overlayDiv.nativeElement.style.top = yPos + 'px';
      this.overlayDiv.nativeElement.style.left = xPos + 'px';
      this.overlayDiv.nativeElement.style.position = 'fixed';
      this.overlayDiv.nativeElement.style.width = '300px';
      this.overlayDiv.nativeElement.style.height = '275px';
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