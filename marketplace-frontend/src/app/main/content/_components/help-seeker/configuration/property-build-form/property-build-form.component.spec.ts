import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PropertyBuildFormComponent } from './property-build-form.component';

describe('PropertyBuildFormComponent', () => {
  let component: PropertyBuildFormComponent;
  let fixture: ComponentFixture<PropertyBuildFormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PropertyBuildFormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PropertyBuildFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
