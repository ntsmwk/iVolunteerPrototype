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
      console.log(this.overlayEvent);

      let yPos = this.overlayEvent.clientY;
      // yPos = 500;
      let xPos = this.overlayEvent.clientX;
      // xPos = 500;

      // if (yPos + 275 > window.innerHeight) {
      //   yPos = window.innerHeight - 275;
      // }

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