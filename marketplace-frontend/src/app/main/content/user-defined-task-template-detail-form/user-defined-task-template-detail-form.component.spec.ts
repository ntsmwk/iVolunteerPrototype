import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UserDefinedTaskTemplateDetailFormComponent } from './user-defined-task-template-detail-form.component';

describe('UserDefinedTaskTemplateDetailFormComponent', () => {
  let component: UserDefinedTaskTemplateDetailFormComponent;
  let fixture: ComponentFixture<UserDefinedTaskTemplateDetailFormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UserDefinedTaskTemplateDetailFormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UserDefinedTaskTemplateDetailFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
