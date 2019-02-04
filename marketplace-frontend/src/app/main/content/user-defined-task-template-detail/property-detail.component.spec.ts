import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FuseUserDefinedTaskTemplateDetailComponent } from './user-defined-task-template-detail.component';

describe('PropertyDetailComponent', () => {
  let component: FuseUserDefinedTaskTemplateDetailComponent;
  let fixture: ComponentFixture<FuseUserDefinedTaskTemplateDetailComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FuseUserDefinedTaskTemplateDetailComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FuseUserDefinedTaskTemplateDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
