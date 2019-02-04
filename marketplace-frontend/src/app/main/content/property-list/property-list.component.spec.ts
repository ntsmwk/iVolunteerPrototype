import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FusePropertyListComponent } from './property-list.component';

describe('PropertyListComponent', () => {
  let component: FusePropertyListComponent;
  let fixture: ComponentFixture<FusePropertyListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FusePropertyListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FusePropertyListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
