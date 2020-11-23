import { ViewChild, ElementRef, Component, ChangeDetectorRef, Input, EventEmitter, Output, OnInit, OnChanges } from '@angular/core';
import { TreePropertyOptionsOverlayContentData } from '../options-overlay-content/options-overlay-content.component';

const OVERLAY_WIDTH = 160;
const OVERLAY_HEIGHT = 100;

@Component({
  selector: 'tree-property-options-overlay-control',
  templateUrl: './options-overlay-control.component.html',
  styleUrls: ['./options-overlay-control.component.scss']
})
export class TreePropertyOptionsOverlayControlComponent implements OnInit, OnChanges {
  @ViewChild('overlayDiv', { static: false }) overlayDiv: ElementRef;
  @Input() displayOverlay: boolean;
  @Input() overlayContent: TreePropertyOptionsOverlayContentData;
  @Input() overlayEvent: PointerEvent;
  @Output() overlayClosed = new EventEmitter<any>();

  constructor(
    private changeDetector: ChangeDetectorRef
  ) { }

  ngOnInit() {
    this.toggleOverlay();
  }

  ngOnChanges() {
    if (this.displayOverlay) {
      this.ngOnInit();
    }
  }

  navigateBack() {
    window.history.back();
  }

  toggleOverlay() {
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

  handleResultEvent(event: TreePropertyOptionsOverlayContentData) {
    this.displayOverlay = false;
    this.overlayClosed.emit(event);
  }

  // closeOverlay(event: ClassOptionsOverlayContentData) {
  //   this.displayOverlay = false;
  //   this.overlayClosed.emit(undefined);
  // }
}