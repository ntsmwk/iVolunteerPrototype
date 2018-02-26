import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReserveTaskComponent } from './reserve-task.component';

describe('ReserveTaskComponent', () => {
  let component: ReserveTaskComponent;
  let fixture: ComponentFixture<ReserveTaskComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ReserveTaskComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReserveTaskComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
