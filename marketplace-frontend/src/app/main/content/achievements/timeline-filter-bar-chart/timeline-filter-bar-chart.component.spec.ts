import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TimelineFilterBarChartComponent } from './timeline-filter-bar-chart.component';

describe('TimelineFilterBarChartComponent', () => {
  let component: TimelineFilterBarChartComponent;
  let fixture: ComponentFixture<TimelineFilterBarChartComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TimelineFilterBarChartComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TimelineFilterBarChartComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
