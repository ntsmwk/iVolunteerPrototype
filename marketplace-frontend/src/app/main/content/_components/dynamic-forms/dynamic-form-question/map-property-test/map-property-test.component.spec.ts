import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MapPropertyTestComponent } from './map-property-test.component';

describe('MapPropertyTestComponent', () => {
  let component: MapPropertyTestComponent;
  let fixture: ComponentFixture<MapPropertyTestComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MapPropertyTestComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MapPropertyTestComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
