import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FuseUserDefinedTaskTemplateListComponent } from './user-defined-task-template-list.component';

describe('UserDefinedTaskTemplateListComponent', () => {
  let component: FuseUserDefinedTaskTemplateListComponent;
  let fixture: ComponentFixture<FuseUserDefinedTaskTemplateListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FuseUserDefinedTaskTemplateListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FuseUserDefinedTaskTemplateListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
