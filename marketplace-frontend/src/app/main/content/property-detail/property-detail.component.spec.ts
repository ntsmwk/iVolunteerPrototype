import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FusePropertyDetailComponent } from './property-detail.component';

describe('PropertyDetailComponent', () => {
  let component: FusePropertyDetailComponent;
  let fixture: ComponentFixture<FusePropertyDetailComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FusePropertyDetailComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FusePropertyDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
